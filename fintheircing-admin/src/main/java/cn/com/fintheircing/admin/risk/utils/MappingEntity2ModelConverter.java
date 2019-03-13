package cn.com.fintheircing.admin.risk.utils;

import cn.com.fintheircing.admin.risk.entity.SystemRisk;
import cn.com.fintheircing.admin.risk.model.RiskModel;

public class MappingEntity2ModelConverter {

    private MappingEntity2ModelConverter(){}

    public static RiskModel CONVERTERFROMSYSTEMRISK(SystemRisk risk){
        RiskModel model = new RiskModel();
        model.setCustomerMaxAccount(risk.getCustomerMaxAccount());
        model.setHoldOverCurrency(risk.getHoldOverCurrency());
        model.setHoldOverFiveAvg(risk.getHoldOverFiveAvg());
        model.setId(risk.getUuid());
        model.setStockShutDown(risk.getStockShutDown());
        model.setVenturEditionMaxAccount(risk.getVenturEditionMaxAccount());
        return model;
    }
}
