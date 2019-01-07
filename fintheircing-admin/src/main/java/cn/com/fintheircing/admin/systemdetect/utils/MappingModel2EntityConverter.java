package cn.com.fintheircing.admin.systemdetect.utils;

import cn.com.fintheircing.admin.systemdetect.entity.Product;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;

public final class MappingModel2EntityConverter {
    private MappingModel2EntityConverter(){}

    public static Product coverProduct(ProductModel model) {
        Product p =new Product();
        p.setAllot(model.getAllot());
        p.setEntryAmount(model.getEntryAmount());
        p.setFinancingTime(model.getFinancingTime());
        p.setId(model.getId());
        p.setLeverRate(model.getLeverRate());
        p.setLiquidation(model.getLiquidation());
        p.setMoneyInContact(model.getMoneyInContact());
        p.setOutAmount(model.getOutAmount());
        p.setWornLine(model.getWornLine());
        p.setMoneyInDeal(model.getMoneyInDeal());
        return p;
    }
}
