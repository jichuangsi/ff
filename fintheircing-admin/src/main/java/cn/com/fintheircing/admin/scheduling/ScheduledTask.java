package cn.com.fintheircing.admin.scheduling;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.business.service.MotherAccountQueryService;
import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.model.TodayAcceptOrder;
import cn.com.fintheircing.admin.common.feign.model.TodayOrder;
import cn.com.fintheircing.admin.common.model.MotherAccount;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.scheduling.model.DealJsonModel;
import cn.com.fintheircing.admin.scheduling.model.EntrustJsonModel;
import cn.com.fintheircing.admin.scheduling.utils.DealUtils;
import cn.com.fintheircing.admin.scheduling.utils.EntrustUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Api("定时任务，查看每日委托，每日成交")
public class ScheduledTask {

    Logger logger = LoggerFactory.getLogger(getClass());

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
    /*@Value("${custom.entrust.entrustSuccess}")
    private String entrustSuccess;*/
    @Value("${custom.deal.prefix}")
    private String dealPrefix;
    @Value("${custom.deal.dealBuy}")
    private String dealBuy;
    @Value("${custom.deal.dealSell}")
    private String dealSell;



   /* @Scheduled(cron = "${custom.scheduled.entrust}")*/
    public void entrustStock() throws BusinessException{
        List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
        for (MotherAccount account:motherAccounts){
            ResponseModel<List<TodayOrder>> responseModel
                    = exchangeFeignService.getTodayOrderList(account.getAccountNo());
            List<TodayOrder> todayOrders = responseModel.getData();
            String oldOrdersJson = redisTemplate.opsForValue().get(entrustPrefix+account.getAccountNo());
            Map<String,TodayOrder> oldOrders = new HashMap<>();
            if (!StringUtils.isEmpty(oldOrdersJson)){
                oldOrders = JSONObject.parseObject(oldOrdersJson, EntrustJsonModel.class).getTodayOrders();
            }
            Map<String,TodayOrder> map = new HashMap<String,TodayOrder>();
            for(TodayOrder todayOrder :todayOrders) {
                if(!oldOrders.containsKey(todayOrder.getOrderNumber())
                        || !EntrustUtils.BALANCETODAYORDER(todayOrder,oldOrders.get(todayOrder.getOrderNumber()))){
                    String orderTypeName = todayOrder.getOrderTypeName().trim();
                    String orderStatus = todayOrder.getStatus().trim();
                    String orderNo = todayOrder.getOrderNumber();
                    if (entrustBusiness.trim().equals(orderTypeName)){
                        if (entrustCancel.trim().equals(orderStatus)){
                            businessService.updateColdMoneyAndSaveEntrust(todayOrder,account.getAccountNo());
                        }
                    }
                }
                map.put(todayOrder.getOrderNumber(),todayOrder);
            }
            EntrustJsonModel jsonModel = new EntrustJsonModel();
            jsonModel.setTodayOrders(map);
            redisTemplate.opsForValue().set(entrustPrefix+account.getAccountNo(),JSONObject.toJSONString(jsonModel));
        }
    }

   /* @Scheduled(cron = "${custom.scheduled.deal}")*/
    public void dealStock(){
        List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
        Map<String,TodayAcceptOrder> jsonMap = new HashMap<String,TodayAcceptOrder>();
        for (MotherAccount account:motherAccounts){
            ResponseModel<List<TodayAcceptOrder>> responseModel = exchangeFeignService.getTodayAcceptOrderList(account.getAccountNo());
            List<TodayAcceptOrder> todayAcceptOrders = responseModel.getData();
            String oldDeal = redisTemplate.opsForValue().get(dealPrefix+account.getAccountNo());
            Map<String,TodayAcceptOrder> map = new HashMap<>();
            if (!StringUtils.isEmpty(oldDeal)){
                map = JSONObject.parseObject(oldDeal, DealJsonModel.class).getMap();
            }
            for (TodayAcceptOrder todayAcceptOrder:todayAcceptOrders){
                if(!map.containsKey(todayAcceptOrder.getOrderNumber())
                        || !DealUtils.BALANCEDEALORDER(todayAcceptOrder,map.get(todayAcceptOrder.getOrderNumber()))){
                    if (dealBuy.equals(todayAcceptOrder.getOperName())){
                        try {
                            businessService.dealBuyMethod(todayAcceptOrder,account.getAccountNo());
                        } catch (BusinessException e) {
                            logger.error(e.getMessage());
                        }
                    }else if (dealSell.equals(todayAcceptOrder.getOperName())){
                        try {
                            businessService.dealSellMethod(todayAcceptOrder,account.getAccountNo());
                        } catch (BusinessException e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
                jsonMap.put(todayAcceptOrder.getOrderNumber(),todayAcceptOrder);
            }
            DealJsonModel jsonModel = new DealJsonModel();
            jsonModel.setMap(jsonMap);
            redisTemplate.opsForValue().set(dealPrefix+account.getAccountNo(),JSONObject.toJSONString(jsonMap));
        }


    }

}
