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
import org.springframework.scheduling.annotation.Scheduled;
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
    @Value("${custom.entrust.entrustCancelBuy}")
    private String entrustCancelBuy;
    @Value("${custom.entrust.entrustCancelSell}")
    private String entrustCancelSell;



    @Scheduled(cron = "${custom.scheduled.entrust}")
    public void entrustStock(){
        try {
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
                        String operName = todayOrder.getOperName();
                        if (entrustBusiness.trim().equals(orderTypeName)){
                            if (entrustCancel.trim().equals(orderStatus)){
                                if (entrustCancelBuy.trim().equals(operName)){
                                    try {
                                        businessService.updateColdMoneyAndSaveEntrust(todayOrder,account.getAccountNo());
                                    } catch (BusinessException e) {
                                        logger.error(e.getMessage());
                                        continue;
                                    }
                                }else if(entrustCancelSell.trim().equals(operName)){
                                    try {
                                        businessService.updateHoldingAndSaveEntrust(todayOrder,account.getAccountNo());
                                    } catch (BusinessException e) {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                    map.put(todayOrder.getOrderNumber(),todayOrder);
                }
                EntrustJsonModel jsonModel = new EntrustJsonModel();
                jsonModel.setTodayOrders(map);
                redisTemplate.opsForValue().set(entrustPrefix+account.getAccountNo(),JSONObject.toJSONString(jsonModel));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Scheduled(cron = "${custom.scheduled.deal}")
    public void dealStock(){
        try {
            List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
            Map<String, TodayAcceptOrder> jsonMap = new HashMap<String, TodayAcceptOrder>();
            for (MotherAccount account : motherAccounts) {
                ResponseModel<List<TodayAcceptOrder>> responseModel = exchangeFeignService.getTodayAcceptOrderList(account.getAccountNo());
                List<TodayAcceptOrder> todayAcceptOrders = responseModel.getData();
                String oldDeal = redisTemplate.opsForValue().get(dealPrefix + account.getAccountNo());
                Map<String, TodayAcceptOrder> map = new HashMap<>();
                if (!StringUtils.isEmpty(oldDeal)) {
                    DealJsonModel jsonModel = JSONObject.parseObject(oldDeal, DealJsonModel.class);
                    map = jsonModel.getTodayAcceptOrders();
                }
                for (TodayAcceptOrder todayAcceptOrder : todayAcceptOrders) {
                    if (!map.containsKey(todayAcceptOrder.getOrderNumber())
                            || !DealUtils.BALANCEDEALORDER(todayAcceptOrder, map.get(todayAcceptOrder.getOrderNumber()))) {
                        if (dealBuy.equals(todayAcceptOrder.getOperName())) {
                            try {
                                businessService.dealBuyMethod(todayAcceptOrder, account.getAccountNo());
                            } catch (BusinessException e) {
                                logger.error(e.getMessage());
                                continue;
                            }
                        } else if (dealSell.equals(todayAcceptOrder.getOperName())) {
                            try {
                                businessService.dealSellMethod(todayAcceptOrder, account.getAccountNo());
                            } catch (BusinessException e) {
                                logger.error(e.getMessage());
                                continue;
                            }
                        }
                    }
                    jsonMap.put(todayAcceptOrder.getOrderNumber(), todayAcceptOrder);
                }
                DealJsonModel jsonModel = new DealJsonModel();
                jsonModel.setTodayAcceptOrders(jsonMap);
                redisTemplate.opsForValue().set(dealPrefix + account.getAccountNo(), JSONObject.toJSONString(jsonModel));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

   /* @Scheduled(cron = "1/10 0/1 * * * ?")
    public void testBusiness(){

        TestRunnable runnable1 = new TestRunnable();
        TestRunnable runnable2 = new TestRunnable();
        TestRunnable runnable3 = new TestRunnable();
        TestRunnable runnable4 = new TestRunnable();
        TestRunnable runnable5 = new TestRunnable();
        TestRunnable runnable6 = new TestRunnable();
        TestRunnable runnable7 = new TestRunnable();
        TestRunnable runnable8 = new TestRunnable();
        TestRunnable runnable9 = new TestRunnable();
        TestRunnable runnable10 = new TestRunnable();
        TestRunnable runnable11 = new TestRunnable();
        TestRunnable runnable12 = new TestRunnable();
        TestRunnable runnable13 = new TestRunnable();
        TestRunnable runnabl14 = new TestRunnable();
        TestRunnable runnable15 = new TestRunnable();
        TestRunnable runnable16 = new TestRunnable();
        TestRunnable runnable17 = new TestRunnable();
        TestRunnable runnable18 = new TestRunnable();
        TestRunnable runnable19 = new TestRunnable();
        TestRunnable runnable20 = new TestRunnable();
        TestRunnable runnable21 = new TestRunnable();
        TestRunnable runnable22 = new TestRunnable();
        TestRunnable runnable23 = new TestRunnable();
        TestRunnable runnable24 = new TestRunnable();
        TestRunnable runnable25 = new TestRunnable();
        TestRunnable runnable26 = new TestRunnable();
        TestRunnable runnable27 = new TestRunnable();
        TestRunnable runnable28 = new TestRunnable();
        TestRunnable runnable29 = new TestRunnable();
        TestRunnable runnable30 = new TestRunnable();
        TestRunnable runnable31 = new TestRunnable();
        TestRunnable runnable32 = new TestRunnable();
        TestRunnable runnabl33 = new TestRunnable();
        TestRunnable runnable34 = new TestRunnable();
        TestRunnable runnable35 = new TestRunnable();
        TestRunnable runnable36 = new TestRunnable();
        TestRunnable runnable37 = new TestRunnable();
        TestRunnable runnable38 = new TestRunnable();
        TestRunnable runnable39 = new TestRunnable();
        TestRunnable runnable40 = new TestRunnable();
        TestRunnable runnable41 = new TestRunnable();
        TestRunnable runnable42 = new TestRunnable();
        TestRunnable runnable43 = new TestRunnable();
        TestRunnable runnable44 = new TestRunnable();
        TestRunnable runnable45 = new TestRunnable();
        TestRunnable runnable46 = new TestRunnable();
        TestRunnable runnable47 = new TestRunnable();
        TestRunnable runnable48 = new TestRunnable();
        TestRunnable runnable49 = new TestRunnable();
        TestRunnable runnable50 = new TestRunnable();
        Thread thread1 = new Thread(runnable1,"线程1");
        Thread thread2 = new Thread(runnable2,"线程2");
        Thread thread3 = new Thread(runnable1,"线程1");
        Thread thread4 = new Thread(runnable2,"线程2");
        Thread thread5 = new Thread(runnable1,"线程1");
        Thread thread6 = new Thread(runnable2,"线程2");
        Thread thread7 = new Thread(runnable1,"线程1");
        Thread thread8 = new Thread(runnable2,"线程2");
        Thread thread9 = new Thread(runnable1,"线程1");
        Thread thread10 = new Thread(runnable2,"线程2");
        Thread thread11 = new Thread(runnable1,"线程1");
        Thread thread12 = new Thread(runnable2,"线程2");
        Thread thread13 = new Thread(runnable1,"线程1");
        Thread threa114 = new Thread(runnable2,"线程2");

        Thread thread15 = new Thread(runnable1,"线程1");
        Thread thread16 = new Thread(runnable2,"线程2");

        Thread thread17 = new Thread(runnable1,"线程1");
        Thread thread18 = new Thread(runnable2,"线程2");

        Thread thread19 = new Thread(runnable1,"线程1");
        Thread thread20 = new Thread(runnable2,"线程2");

        Thread thread21 = new Thread(runnable1,"线程1");
        Thread thread22 = new Thread(runnable2,"线程2");

        Thread thread23 = new Thread(runnable1,"线程1");
        Thread thread24 = new Thread(runnable2,"线程2");

        Thread thread25 = new Thread(runnable1,"线程1");
        Thread thread26 = new Thread(runnable2,"线程2");

        Thread thread27 = new Thread(runnable1,"线程1");
        Thread thread28 = new Thread(runnable2,"线程2");

        Thread thread29 = new Thread(runnable1,"线程1");
        Thread thread30 = new Thread(runnable2,"线程2");

        Thread thread31 = new Thread(runnable1,"线程1");
        Thread thread32 = new Thread(runnable2,"线程2");

        Thread thread33 = new Thread(runnable1,"线程1");
        Thread thread34 = new Thread(runnable2,"线程2");

        Thread thread35 = new Thread(runnable1,"线程1");
        Thread thread36 = new Thread(runnable2,"线程2");

        Thread thread37 = new Thread(runnable1,"线程1");
        Thread thread38 = new Thread(runnable2,"线程2");

        Thread thread39 = new Thread(runnable1,"线程1");
        Thread thread40 = new Thread(runnable2,"线程2");

        Thread thread41 = new Thread(runnable1,"线程1");
        Thread thread42 = new Thread(runnable2,"线程2");

        Thread thread44 = new Thread(runnable1,"线程1");
        Thread thread43 = new Thread(runnable2,"线程2");

        Thread thread45 = new Thread(runnable1,"线程1");
        Thread thread46 = new Thread(runnable2,"线程2");

        Thread thread47 = new Thread(runnable1,"线程1");
        Thread thread48 = new Thread(runnable2,"线程2");

        Thread thread49 = new Thread(runnable1,"线程1");
        Thread thread50 = new Thread(runnable2,"线程2");



        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread11.start();thread10.start();
        thread12.start();thread13.start();
        thread15.start();thread16.start();
        thread17.start();
        thread18.start();
        thread19.start();
        thread20.start();
        thread22.start();
        thread21.start();
        thread23.start();
        thread24.start();
        thread25.start();
        thread26.start();
        thread27.start();thread28.start();
        thread29.start();
        thread30.start();
        thread31.start();
        thread32.start();
        thread33.start();
        thread34.start();
        thread35.start();
        thread36.start();
        thread37.start();
        thread38.start();
        thread39.start();
        thread40.start();
        thread41.start();
        thread42.start();
        thread43.start();
        thread44.start();
        thread45.start();thread46.start();
        thread47.start();
        thread48.start();
        thread49.start();
        thread50.start();





    }


    public class TestRunnable implements Runnable {
        @Override
        public void run() {

                System.out.println(Thread.currentThread().getName());
                entrustStock();
                dealStock();
        }
    }*/



   @Scheduled(cron = "${custom.scheduled.fiveAvg}")
    public void getFiveDayAvgAmount(){
       try {
           businessService.getFiveDayMaxAmount();
       } catch (Exception e) {
           logger.error(e.getMessage());
       }
   }

    @Scheduled(cron = "${custom.scheduled.everyDay}")
   public void everyDayClear(){
        try {
            businessService.everyDayClear();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
