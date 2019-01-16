package cn.com.fintheircing.admin.business.utils;

import cn.com.fintheircing.admin.business.entity.BusinessContractRisk;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.StockModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BusinessUtils {


    //单股最大买入
    public static final Boolean maxBuyOneStock(StockHoldingModel stockHoldingModel, StockEntrustModel stockEntrustModel,
                                               ContractModel contractModel) throws BusinessException{
        Double C0 = stockHoldingModel.getCurrentWorth();
        Double C1 = stockEntrustModel.getPrice()*stockEntrustModel.getAmount();
        Double A = contractModel.getColdCash()+contractModel.getWorth()+contractModel.getCanUseMoney();
        if (!((C0+C1)/A<contractModel.getCustomerMaxAccount())){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_MAX);
        }
        return true;
    }

    //创业股最大买入
    public static final Boolean venturEditionMax(StockHoldingModel stockHoldingModel, StockEntrustModel stockEntrustModel,
                                                 ContractModel contractModel) throws BusinessException{
        Double C0 = stockHoldingModel.getCurrentWorth();
        Double C1 = stockEntrustModel.getPrice()*stockEntrustModel.getAmount();
        Double A = contractModel.getColdCash()+contractModel.getWorth()+contractModel.getCanUseMoney();
        if (!((C0+C1)/A<contractModel.getVenturEditionMaxAccount())){
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
        BigDecimal b = new BigDecimal(todayMin/yesterdayClose);
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
}
