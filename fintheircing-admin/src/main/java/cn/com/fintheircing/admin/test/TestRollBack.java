package cn.com.fintheircing.admin.test;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessStockHoldingMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRiskRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockHoldingRepository;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.StockModel;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.business.service.MotherAccountQueryService;
import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.model.TodayAcceptOrder;
import cn.com.fintheircing.admin.common.model.MotherAccount;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.scheduling.model.DealJsonModel;
import cn.com.fintheircing.admin.scheduling.utils.DealUtils;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRollBack
{
    @Resource
    private BusinessService service;
    @Resource
    private IBusinessContractRiskRepository businessContractRiskRepository;
    @Resource
    private IBusinessStockHoldingRepository businessStockHoldingRepository;
    @Resource
    private IBusinessContractMapper businessContractMapper;
    @Resource
    private TransactionSummaryRepository transactionSummaryRepository;
    @Resource
    private IBusinessStockHoldingMapper businessStockHoldingMapper;



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

    @Test
    public void main(){
        try {
            service.testRollBack();
        } catch (BusinessException e) {
            System.out.println("111");
        } finally {
        }
    }

    @Test
    public void testRisk() throws BusinessException{
        StockModel stockModel = new StockModel();//根据stockNum获取当前股票实时数据
        //BusinessContractRisk risk = businessContractRiskRepository.findBusinessContractRiskByContractId("40289f1a686084460168608694110000");
        StockHoldingModel stockHoldingModel = businessStockHoldingMapper.selectStockNum("40289f1a686084460168608694110000","600600");
        ContractModel contract = businessContractMapper.selectContract("40289f1a686084460168608694110000");//获取相关合约
        stockModel.setYesterdayClose(54.0);
        stockModel.setTodayMax(55.0);
        stockModel.setTodayMin(48.0);
        stockModel.setTodayOpen(54.32);
        StockEntrustModel model = new StockEntrustModel();
        model.setAmount(300);
        model.setPrice(53.0);
        model.setStockNum("600600");
        /*BusinessUtils.throughRisk(stockHoldingModel,model,contract,stockModel);*/

    }

    @Test
    public void testSaveStock(){
        TransactionSummary summary = new TransactionSummary();
        summary.setStockNum("600678");
        summary.setJoinTime(new Date());
        summary.setRemake("测试");
        summary.setStockName("测试");
        transactionSummaryRepository.save(summary);
    }

    @Test
    public void testNum(){
        Integer i = null;
        i = 1+i;
        System.out.println(i);
    }


    @Test
    public void testdeal(){
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
            redisTemplate.opsForValue().set(dealPrefix+account.getAccountNo(), JSONObject.toJSONString(jsonMap));
        }


    }
}
