package cn.com.fintheircing.admin.business.utils;

import cn.com.fintheircing.admin.business.entity.BusinessContractRisk;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BusinessUtils {


    //单股最大买入
    public static final Boolean maxBuyOneStock(StockHoldingModel stockHoldingModel, StockEntrustModel stockEntrustModel,
                                               ContractModel contractModel) throws BusinessException{
        Double C0 = stockHoldingModel.getCurrentWorth();
        Double C1 = multiplicationMethod(stockEntrustModel.getPrice(),stockEntrustModel.getAmount().doubleValue());
        Double A = addMethod(contractModel.getColdCash(),contractModel.getWorth(),contractModel.getCanUseMoney());
        if (!(addMethod(C0,C1)/A<contractModel.getCustomerMaxAccount())){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_MAX);
        }
        return true;
    }

    //创业股最大买入
    public static final Boolean venturEditionMax(StockHoldingModel stockHoldingModel, StockEntrustModel stockEntrustModel,
                                                 ContractModel contractModel) throws BusinessException{
        Double C0 = stockHoldingModel.getCurrentWorth();
        Double C1 = multiplicationMethod(stockEntrustModel.getPrice(),stockEntrustModel.getAmount().doubleValue());
        Double A = BusinessUtils.addMethod(contractModel.getColdCash(),contractModel.getWorth(),contractModel.getCanUseMoney());
        if (!(addMethod(C0,C1)/A<contractModel.getVenturEditionMaxAccount())){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_MAX);
        }
        return true;
    }

    //五日平均，暂无接口
    public static final Boolean holdOverFiveAvg(StockHoldingModel stockHoldingModel,StockEntrustModel stockEntrustModel) throws BusinessException{
        Double avgFive = avgMethod(stockHoldingModel.getOneDay(),stockHoldingModel.getTwoDay(),stockHoldingModel.getThreeDay(),stockHoldingModel.getFourDay(),stockHoldingModel.getFiveDay());
        Double C0 = stockHoldingModel.getCurrentWorth();
        Double C1 = multiplicationMethod(stockEntrustModel.getPrice(),stockEntrustModel.getAmount().doubleValue());
        if (avgFive!=0) {
            if (!(addMethod(C0, C1) < avgFive)) {
                throw new BusinessException(ResultCode.STOCK_ENTRUST_MAX);
            }
        }
        return  true;
    }

    //获取流通市值，暂无接口
    public static final Boolean holdOverCurrency(StockHoldingModel stockHoldingModel, StockEntrustModel stockEntrustModel,
                                                 ContractModel contractModel) throws BusinessException{
        Double C0 = stockHoldingModel.getCurrentWorth();
        Double C1 = multiplicationMethod(stockEntrustModel.getPrice(),stockEntrustModel.getAmount().doubleValue());
        if (!(addMethod(C0,C1)<contractModel.getHoldOverCurrency())){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_MAX);
        }
        return true;
    }

    //跌停能否购买
    public static final Boolean stockShutDown(QuotesTenModel quotesTenModel,ContractModel contractModel) throws BusinessException{
        Boolean shutDown = false;
        Double todayMin = (double)quotesTenModel.getBottomPrice();
        Double yesterdayClose = (double)quotesTenModel.getClosePrice();
        BigDecimal b = new BigDecimal(exceptMethod(yesterdayClose,todayMin));
        double rate = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        if (rate<0.90){
            shutDown = true;
        }
        if (shutDown){
            if (BusinessContractRisk.SHUTDOWN_NOT_BUY==contractModel.getStockShutDown()){
                throw new BusinessException(ResultCode.STOCK_ENTRUST_DANGER);
            }
        }
        return true;
    }

    public static final Boolean throughRisk(StockHoldingModel stockHoldingModel, StockEntrustModel stockEntrustModel,
                                            ContractModel contractModel, QuotesTenModel quotesTenModel, String chuangRegex) throws BusinessException{
        if (stockEntrustModel==null){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_NULL);
        }
        if (stockHoldingModel==null){
            stockHoldingModel = new StockHoldingModel();
        }
        if (quotesTenModel==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        if (contractModel==null){
            throw new BusinessException(ResultCode.CONTRACT_NULL_FIND);
        }
        Double yesterdayClose = (double)quotesTenModel.getClosePrice();
        if (stockEntrustModel.getPrice()/yesterdayClose>1.1
                ||stockEntrustModel.getPrice()/yesterdayClose<0.9){
            throw new BusinessException(ResultCode.STOCK_BAD_ERR);
        }
        Pattern pattern = Pattern.compile(chuangRegex);
        Matcher matcher = pattern.matcher(stockEntrustModel.getStockNum());
        if (matcher.find()){
            Boolean venturEditionMax = venturEditionMax(stockHoldingModel,stockEntrustModel,contractModel);
        }else {
            Boolean maxBuyOneStock = maxBuyOneStock(stockHoldingModel,stockEntrustModel,contractModel);
        }
        Boolean holdOverFiveAvg  = holdOverFiveAvg(stockHoldingModel,stockEntrustModel);
        Boolean holdOverCurrency = holdOverCurrency(stockHoldingModel,stockEntrustModel,contractModel);
        Boolean shockShutDown = stockShutDown(quotesTenModel,contractModel);
        return true;
    }


    public static UUID fromStringWhitoutHyphens(String str) {
        if (str.length() != 32) {
            throw new IllegalArgumentException("Invalid UUID string: " + str);
        }
        String s1 = "0x" + str.substring(0, 8);
        String s2 = "0x" + str.substring(9, 12);
        String s3 = "0x" + str.substring(13, 16);
        String s4 = "0x" + str.substring(17, 20);
        String s5 = "0x" + str.substring(21, 32);

        long mostSigBits = Long.decode(s1).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(s2).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(s3).longValue();

        long leastSigBits = Long.decode(s4).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(s5).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }


    public static  Double addMethod(Double... doubles){
        Double sum = 0.0;
        for (int i = 0; i<doubles.length; i++){
            if (doubles[i]!=null){
                sum+=doubles[i];
            }
        }
        return sum;
    }

    public static Double exceptMethod(Double mother,Double son) throws BusinessException{
        if (son==null){
            return 0.0;
        }
        if (mother==null){
            throw new BusinessException("无法计算");
        }
        if (mother==0){
            throw new BusinessException("by /zero");
        }
        return son/mother;
    }

    public static  Double minusMethod(Double mother,Double... doubles) throws BusinessException{
        if (mother==null){
            throw new BusinessException("无法计算");
        }
        for (int i = 0; i<doubles.length; i++){
            if (doubles[i]!=null){
                mother-=doubles[i];
            }
        }
        return mother;
    }

    public static Double multiplicationMethod(Double... doubles){
        Double sum = 0.0;
        Boolean flag = true;
        Double method = 0.0;
        for (int i = 0; i<doubles.length; i++){
            if (flag){
                if (doubles[i]!=null){
                    sum = doubles[i];
                    flag = false;
                    continue;
                }
            }
            if (doubles[i]==null){
                method = 0.0;
            }else {
                method = doubles[i];
            }
            sum = sum * method;
        }
        return sum;
    }


    public static Double avgMethod(Double... doubles){
        Double sum = 0.0;
        Integer num = 0;
        for (int i = 0; i<doubles.length; i++){
            if (null != doubles[i]){
                sum += doubles[i];
                num ++;
            }
        }
        if (0 == num){
            return 0.0;
        }else {
            return sum/num;
        }
    }
    public static int addIntMethod(Integer... nums){
        Integer sum = 0;
        for (int i = 0; i< nums.length; i++){
            if (null != nums[i]){
                sum+=nums[i];
            }
        }
        return sum;
    }



    public static  Integer minusIntMethod(Integer mother,Integer... doubles) {
        if (mother==null){
            mother = 0;
        }
        for (int i = 0; i<doubles.length; i++){
            if (doubles[i]!=null){
                mother-=doubles[i];
            }
        }
        return mother;
    }


    public static List<String[]> getListStringArray(String[] dd, int b) {
        List<String[]> aa = new ArrayList<String[]>();
        // tyy 取整代表可以拆分的数组个数
        int f = dd.length / b;
        for (int i = 0; i < f; i++) {
            String[] bbb = new String[b];
            for (int j = 0; j < b; j++) {
                bbb[j] = dd[j + i * b];
            }
            aa.add(bbb);
        }
        int z = dd.length % b;
        if (z != 0){
            String[] ccc = new String[z];
            for (int p = 0; p < z; p++){
                ccc[p] = dd[p+f*b];
            }
            aa.add(ccc);
        }
        return aa;
    }

    public static Double avgStockPrice(Double costPrice,Integer costAmount,Double dealPrice,Integer dealAmount){
        Double cost = multiplicationMethod(costPrice , costAmount.doubleValue());
        Double deal = multiplicationMethod(dealPrice , dealAmount.doubleValue());
        return (cost + deal)/(costAmount +dealAmount);
    }

}
