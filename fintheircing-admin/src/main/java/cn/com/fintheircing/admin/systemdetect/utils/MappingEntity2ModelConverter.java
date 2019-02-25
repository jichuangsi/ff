package cn.com.fintheircing.admin.systemdetect.utils;

import cn.com.fintheircing.admin.systemdetect.entity.Product;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class MappingEntity2ModelConverter {
    public static final ProductModel coverProduct(Product p) {
        ProductModel model =new ProductModel() ;

            model.setAllot(p.getAllot());
            model.setEntryAmount(p.getEntryAmount());
            model.setFinancingTime(p.getFinancingTime());
            model.setId(p.getId());
            model.setLeverRate(Integer.toString(p.getLeverRate()));
            model.setLiquidation(p.getLiquidation());
            model.setMoneyInContact(p.getMoneyInContact());
            model.setOutAmount(p.getOutAmount());
            model.setWornLine(p.getWornLine());
            model.setMoneyInDeal(p.getMoneyInDeal());
            return model;

    }
}
