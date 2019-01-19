package cn.com.fintheircing.admin.business.utils;

import cn.com.fintheircing.admin.business.entity.BusinessContractRisk;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.StockModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;

import java.math.BigDecimal;
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
    public static final Boolean holdOverFiveAvg() throws BusinessException{

        return  true;
    }

    //获取流通市值，暂无接口
    public static final Boolean holdOverCurrency() throws BusinessException{

        return true;
    }

    //跌停能否购买
    public static final Boolean shockShutDown(StockModel stockModel,ContractModel contractModel) throws BusinessException{
        Boolean shutDown = false;
        Double todayMin = stockModel.getTodayMin();
        Double yesterdayClose = stockModel.getYesterdayClose();
        BigDecimal b = new BigDecimal(exceptMethod(yesterdayClose,todayMin));
        double rate = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        if (rate<0.90){
            shutDown = true;
        }
        if (shutDown){
            if (BusinessContractRisk.SHUTDOWN_NOT_BUY==contractModel.getShockShutDown()){
                throw new BusinessException(ResultCode.STOCK_ENTRUST_DANGER);
            }
        }
        return true;
    }

    public static final Boolean throughRisk(StockHoldingModel stockHoldingModel, StockEntrustModel stockEntrustModel,
                                            ContractModel contractModel,StockModel stockModel) throws BusinessException{
        if (stockEntrustModel==null){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_NULL);
        }
        if (stockHoldingModel==null){
            stockHoldingModel = new StockHoldingModel();
        }
        if (stockModel==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        if (contractModel==null){
            throw new BusinessException(ResultCode.CONTRACT_NULL_FIND);
        }
        Double yesterdayClose = stockModel.getYesterdayClose();
        if (stockEntrustModel.getPrice()/yesterdayClose>1.1
                ||stockEntrustModel.getPrice()/yesterdayClose<0.9){
            throw new BusinessException(ResultCode.STOCK_BAD_ERR);
        }
        String reg = "^3[0-9]*$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(stockEntrustModel.getStockNum());
        if (matcher.find()){
            Boolean venturEditionMax = venturEditionMax(stockHoldingModel,stockEntrustModel,contractModel);
        }else {
            Boolean maxBuyOneStock = maxBuyOneStock(stockHoldingModel,stockEntrustModel,contractModel);
        }
        Boolean holdOverFiveAvg  = holdOverFiveAvg();
        Boolean holdOverCurrency = holdOverCurrency();
        Boolean shockShutDown = shockShutDown(stockModel,contractModel);
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

}
