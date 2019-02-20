package cn.com.fintheircing.admin.taxation.utils;

import cn.com.fintheircing.admin.taxation.entity.BusinessTaxation;
import cn.com.fintheircing.admin.taxation.model.TaxationModel;

public final class MappingEntity2ModelConverter {

    private MappingEntity2ModelConverter(){}

    public static TaxationModel CONVERTERFORMBUSINESSTAXATION(BusinessTaxation taxation){
        TaxationModel model = new TaxationModel();
        if (BusinessTaxation.BUSINESS_BUY.equals(taxation.getBsuinessTo())){
            model.setBusinessTo("买入");
        }
        if (BusinessTaxation.BUSINESS_SELL.equals(taxation.getBsuinessTo())){
            model.setBusinessTo("卖出");
        }
        model.setFixed(taxation.getFixed());
        model.setId(taxation.getUuid());
        model.setLabelName(taxation.getLabelName());
        model.setRemarks(taxation.getRemarks());
        model.setTaxationName(taxation.getTaxName());
        model.setTaxationRate(taxation.getTaxRate());
        if (null != taxation.getUpdatedTime()) {
            model.setUpdateTime(taxation.getUpdatedTime().getTime());
        }
        return model;
    }
}
