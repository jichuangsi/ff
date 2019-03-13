package cn.com.fintheircing.admin.risk.utils;

import cn.com.fintheircing.admin.risk.entity.SystemRisk;
import cn.com.fintheircing.admin.risk.model.RiskModel;

public class MappingModel2EntityConverter {

    private MappingModel2EntityConverter(){}

    public static SystemRisk CONVERTERFROMRISKMODEL(RiskModel model){
        SystemRisk risk = new SystemRisk();
        risk.setVenturEditionMaxAccount(model.getVenturEditionMaxAccount());
        risk.setStockShutDown(model.getStockShutDown());
        risk.setHoldOverFiveAvg(model.getHoldOverFiveAvg());
        risk.setHoldOverCurrency(model.getHoldOverCurrency());
        risk.setUuid(model.getId());
        risk.setCustomerMaxAccount(model.getCustomerMaxAccount());
        return risk;
    }
}
