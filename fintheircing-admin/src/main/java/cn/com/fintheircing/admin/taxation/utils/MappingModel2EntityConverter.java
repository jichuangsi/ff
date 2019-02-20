package cn.com.fintheircing.admin.taxation.utils;

import cn.com.fintheircing.admin.taxation.entity.BusinessTaxation;
import cn.com.fintheircing.admin.taxation.model.TaxationModel;

public final class MappingModel2EntityConverter {
    private MappingModel2EntityConverter(){}

    public static BusinessTaxation CONVERTERFORTAXATIONMODEL(TaxationModel model){
        BusinessTaxation businessTaxation = new BusinessTaxation();
        businessTaxation.setFixed(model.getFixed());
        businessTaxation.setBsuinessTo(model.getBusinessTo());
        businessTaxation.setRemarks(model.getRemarks());
        businessTaxation.setTaxName(model.getTaxationName());
        businessTaxation.setTaxRate(model.getTaxationRate());
        return businessTaxation;
    }
}
