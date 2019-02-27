package cn.com.fintheircing.admin.business.utils;

import cn.com.fintheircing.admin.business.entity.record.StockEquityRecord;
import cn.com.fintheircing.admin.business.model.record.StockEquityModel;

public final class MappingModel2EntityConverter {

    private MappingModel2EntityConverter(){}

    public static StockEquityRecord CONVERTERFROMSTOCKEQUITYMODEL(StockEquityModel model){
        StockEquityRecord equityRecord = new StockEquityRecord();
        equityRecord.setAmount(model.getAmount());
        equityRecord.setApplyType(model.getApplyType());
        equityRecord.setContactId(model.getContractId());
        if (null == model.getDealPrice()){
            model.setDealPrice(0.0);
        }
        equityRecord.setDealPrice(model.getDealPrice());
        equityRecord.setRemark(model.getRemarks());
        equityRecord.setStockCode(model.getStockCode());
        equityRecord.setStockName(model.getStockName());
        return equityRecord;
    }
}
