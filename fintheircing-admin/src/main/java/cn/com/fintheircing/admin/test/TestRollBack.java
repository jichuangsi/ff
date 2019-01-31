package cn.com.fintheircing.admin.test;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessStockHoldingMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRiskRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockEntrustRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockHoldingRepository;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.business.service.MotherAccountQueryService;
import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.IStockPriceFeignService;
import cn.com.fintheircing.admin.common.feign.model.GetQuotesTenListRequestModel;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.feign.model.TodayAcceptOrder;
import cn.com.fintheircing.admin.common.feign.model.TodayOrder;
import cn.com.fintheircing.admin.common.model.MotherAccount;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.scheduling.model.DealJsonModel;
import cn.com.fintheircing.admin.scheduling.model.EntrustJsonModel;
import cn.com.fintheircing.admin.scheduling.utils.DealUtils;
import cn.com.fintheircing.admin.scheduling.utils.EntrustUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRollBack {
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
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private BusinessService businessService;
    @Resource
    private TestComponent testComponent;
    @Resource
    private IBusinessStockEntrustRepository businessStockEntrustRepository;
    @Resource
    private IStockPriceFeignService stockPriceFeignService;

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
    public void main() {
        try {
            testComponent.testRollBack();
        } catch (BusinessException e) {
            System.out.println("111");
        } finally {
        }
    }

   /* @Test
    public void testRisk() throws BusinessException{
        StockModel stockModel = new StockModel();//根据stockNum获取当前股票实时数据
        //BusinessContractRisk risk = businessContractRiskRepository.findBusinessContractRiskByContractId("40289f1a686084460168608694110000");
        StockHoldingModel stockHoldingModel = businessStockHoldingMapper.selectStockNum("40289f1a686084460168608694110000","600600").get(0);
        ContractModel contract = businessContractMapper.selectContract("40289f1a686084460168608694110000");//获取相关合约
        stockModel.setYesterdayClose(54.0);
        stockModel.setTodayMax(55.0);
        stockModel.setTodayMin(48.0);
        stockModel.setTodayOpen(54.32);
        StockEntrustModel model = new StockEntrustModel();
        model.setAmount(300);
        model.setPrice(53.0);
        model.setStockNum("600600");
        *//*BusinessUtils.throughRisk(stockHoldingModel,model,contract,stockModel);*//*

    }*/

    @Test
    public void testSaveStock() {
        TransactionSummary summary = new TransactionSummary();
        summary.setStockNum("600678");
        summary.setJoinTime(new Date());
        summary.setRemake("测试");
        summary.setStockName("测试");
        transactionSummaryRepository.save(summary);
    }

    @Test
    public void testNum() {
        Integer i = null;
        i = 1 + i;
        System.out.println(i);
    }

    @Test
    public void testdeal() {
        List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
        Map<String, TodayAcceptOrder> jsonMap = new HashMap<String, TodayAcceptOrder>();
        for (MotherAccount account : motherAccounts) {
            ResponseModel<List<TodayAcceptOrder>> responseModel = exchangeFeignService.getTodayAcceptOrderList(account.getAccountNo());
            List<TodayAcceptOrder> todayAcceptOrders = responseModel.getData();
            String oldDeal = redisTemplate.opsForValue().get(dealPrefix + account.getAccountNo());
            Map<String, TodayAcceptOrder> map = new HashMap<>();
            if (!StringUtils.isEmpty(oldDeal)) {
                map = JSONObject.parseObject(oldDeal, DealJsonModel.class).getTodayAcceptOrders();
            }
            for (TodayAcceptOrder todayAcceptOrder : todayAcceptOrders) {
                if (!map.containsKey(todayAcceptOrder.getOrderNumber())
                        || !DealUtils.BALANCEDEALORDER(todayAcceptOrder, map.get(todayAcceptOrder.getOrderNumber()))) {
                    if (dealBuy.equals(todayAcceptOrder.getOperName())) {
                        try {
                            businessService.dealBuyMethod(todayAcceptOrder, account.getAccountNo());
                        } catch (BusinessException e) {
                            logger.error(e.getMessage());
                        }
                    } else if (dealSell.equals(todayAcceptOrder.getOperName())) {
                        try {
                            businessService.dealSellMethod(todayAcceptOrder, account.getAccountNo());
                        } catch (BusinessException e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
                jsonMap.put(todayAcceptOrder.getOrderNumber(), todayAcceptOrder);
            }
            DealJsonModel jsonModel = new DealJsonModel();
            jsonModel.setTodayAcceptOrders(jsonMap);
            redisTemplate.opsForValue().set(dealPrefix + account.getAccountNo(), JSONObject.toJSONString(jsonMap));
        }
    }

    @Test
    public void testEntrust() throws BusinessException {
        List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
        for (MotherAccount account : motherAccounts) {
            ResponseModel<List<TodayOrder>> responseModel
                    = exchangeFeignService.getTodayOrderList(account.getAccountNo());
            List<TodayOrder> todayOrders = responseModel.getData();
            String oldOrdersJson = redisTemplate.opsForValue().get(entrustPrefix + account.getAccountNo());
            Map<String, TodayOrder> oldOrders = new HashMap<>();
            if (!StringUtils.isEmpty(oldOrdersJson)) {
                oldOrders = JSONObject.parseObject(oldOrdersJson, EntrustJsonModel.class).getTodayOrders();
            }
            Map<String, TodayOrder> map = new HashMap<String, TodayOrder>();
            for (TodayOrder todayOrder : todayOrders) {
                if (!oldOrders.containsKey(todayOrder.getOrderNumber())
                        || !EntrustUtils.BALANCETODAYORDER(todayOrder, oldOrders.get(todayOrder.getOrderNumber()))) {
                    String orderTypeName = todayOrder.getOrderTypeName().trim();
                    String orderStatus = todayOrder.getStatus().trim();
                    String orderNo = todayOrder.getOrderNumber();
                    if (entrustBusiness.trim().equals(orderTypeName)) {
                        if (entrustCancel.trim().equals(orderStatus)) {
                            businessService.updateColdMoneyAndSaveEntrust(todayOrder, account.getAccountNo());
                        }
                    }
                }
                map.put(todayOrder.getOrderNumber(), todayOrder);
            }
            EntrustJsonModel jsonModel = new EntrustJsonModel();
            jsonModel.setTodayOrders(map);
            redisTemplate.opsForValue().set(entrustPrefix + account.getAccountNo(), JSONObject.toJSONString(jsonModel));
        }
    }

    @Test
    public void testQuotesTen() {
        //businessService.getQuotesTenModel("600600","100100","200200","300300");
        /*String[] strings = new String[]{"w","e","w","e","w","y","u","w"};
        List<String[]> list = BusinessUtils.getListStringArray(strings,3);
        System.out.println(list);*/
       /* List<TestModel> list = new ArrayList<>();
        list.add(new TestModel("1",2));
        list.add(new TestModel("2",3));
        list.add(new TestModel("3",4));
        for (TestModel s:list){
            if ("1".equals(s.getS())){
                s.setI(5);
            }
        }
        System.out.println(list);*/
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String dd = sdf.format(new Date());
        System.out.println(dd);
    }

    @Test
    public void testGet() {
        List<String> contractIds = new ArrayList<String>();
        contractIds.add("1");
        contractIds.add("1");
        contractIds.add("2");
        String[] c = contractIds.toArray(new String[contractIds.size()]);
        System.out.println(contractIds);
    }

    @Test
    public void testGetOne() {
       List<String> stockCode = new ArrayList<>();
       List<String> marts = new ArrayList<>();
       stockCode.add("000420");
       marts.add("2");
        GetQuotesTenListRequestModel requestModel = new GetQuotesTenListRequestModel();
        requestModel.setStockCodes(stockCode);
        requestModel.setMarkets(marts);
       ResponseModel<List<QuotesTenModel>> responseModel = stockPriceFeignService.getQuotesTenList(requestModel);
        System.out.println(requestModel);
    }

    @Test
    public void testHasCode(){
        try {
            businessService.getFiveDayMaxAmount();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }



}
