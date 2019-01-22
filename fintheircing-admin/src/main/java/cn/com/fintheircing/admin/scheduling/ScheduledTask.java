package cn.com.fintheircing.admin.scheduling;

import cn.com.fintheircing.admin.business.constant.EntrustStatus;
import cn.com.fintheircing.admin.business.entity.BusinessStockEntrust;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.business.service.MotherAccountQueryService;
import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.model.TodayOrder;
import cn.com.fintheircing.admin.common.model.MotherAccount;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.scheduling.model.EntrustJsonModel;
import cn.com.fintheircing.admin.scheduling.utils.EntrustUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Api("定时任务，查看每日委托，每日成交")
public class ScheduledTask {
    @Resource
    private MotherAccountQueryService motherAccountQueryService;
    @Resource
    private IExchangeFeignService exchangeFeignService;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private BusinessService businessService;


    @Value("${custom.entrust.prefix}")
    private String entrustPrefix;
    @Value("${custom.entrust.entrustBusiness}")
    private String entrustBusiness;
    @Value("${custom.entrust.entrustCancel}")
    private String entrustCancel;
    @Value("${custom.entrust.entrustSuccess}")
    private String entrustSuccess;



    @Scheduled(cron = "0 0/1 9,10,11,12,13,14,15 * * 1/5")
    public void entrustStock(){
        List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
        BusinessStockEntrust stockEntrust = new BusinessStockEntrust();
        for (MotherAccount account:motherAccounts){
            ResponseModel<List<TodayOrder>> responseModel
                    = exchangeFeignService.getTodayOrderList(account.getAccountNo());
            List<TodayOrder> todayOrders = responseModel.getData();
            String oldOrdersJson = redisTemplate.opsForValue().get(entrustPrefix+account.getAccountNo());
            Map<String,TodayOrder> oldOrders = JSONObject.parseObject(oldOrdersJson, EntrustJsonModel.class).getTodayOrders();
            Map<String,TodayOrder> map = new HashMap<String,TodayOrder>();
            for(TodayOrder todayOrder :todayOrders) {
                if(!oldOrders.containsKey(todayOrder.getOrderNumber())
                        || !EntrustUtils.BALANCETODAYORDER(todayOrder,oldOrders.get(todayOrder.getOrderNumber()))){
                    String orderTypeName = todayOrder.getOrderTypeName().trim();
                    String orderStatus = todayOrder.getStatus().trim();
                    String orderNo = todayOrder.getOrderNumber();
                    if (entrustBusiness.trim().equals(orderTypeName)){
                        if (entrustCancel.trim().equals(orderStatus)){
                            stockEntrust = businessService.selectEntrust(account.getAccountNo(),orderNo);
                            stockEntrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
                            stockEntrust.setUpdatedTime(new Date());
                            businessService.saveEntrus(stockEntrust);
                        }else if (entrustSuccess.trim().equals(orderStatus)){
                            stockEntrust = businessService.selectEntrust(account.getAccountNo(),orderNo);
                            stockEntrust.setEntrustStatus(EntrustStatus.ENTRUST_FINSISH.getIndex());
                            stockEntrust.setUpdatedTime(new Date());
                            businessService.saveEntrus(stockEntrust);
                        }
                    }/*else if (entrustCancel.trim().equals(oldOrdersJson)){

                    }*/
                }
                map.put(todayOrder.getOrderNumber(),todayOrder);
            }
            EntrustJsonModel jsonModel = new EntrustJsonModel();
            jsonModel.setTodayOrders(map);
            redisTemplate.opsForValue().set(entrustPrefix+account.getAccountNo(),JSONObject.toJSONString(jsonModel));
        }
    }

    @Scheduled(cron = "0 0/1 9,10,11,12,13,14,15 * * 1/5")
    public void dealStock(){



    }

}
