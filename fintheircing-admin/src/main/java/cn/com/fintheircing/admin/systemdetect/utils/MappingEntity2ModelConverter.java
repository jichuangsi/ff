package cn.com.fintheircing.admin.systemdetect.utils;

import cn.com.fintheircing.admin.common.constant.Status;
import cn.com.fintheircing.admin.systemdetect.entity.Product;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class MappingEntity2ModelConverter {
    public static final List<ProductModel> coverProductList(List<Product> productList) {
        List<ProductModel> models = new ArrayList<>();
        ProductModel model = new ProductModel();
        productList.forEach(p -> {
            model.setAllot(Status.getStatus(p.getAllot()).getIndex());
            model.setEntryAmount(p.getEntryAmount());
            model.setFinancingTime(p.getFinancingTime());
            model.setId(p.getId());
            model.setLeverRate(p.getLeverRate());
            model.setLiquidation(p.getLiquidation());
            model.setMoneyInContact(p.getMoneyInContact());
            model.setOutAmount(p.getOutAmount());
            model.setWornLine(p.getWornLine());
            model.setMoneyInDeal(p.getMoneyInDeal());
            models.add(model);
        });
        return models;
    }
    public static final ProductModel coverProduct(Product p) {
        ProductModel model =new ProductModel() ;

            model.setAllot(Status.getStatus(p.getAllot()).getIndex());
            model.setEntryAmount(p.getEntryAmount());
            model.setFinancingTime(p.getFinancingTime());
            model.setId(p.getId());
            model.setLeverRate(p.getLeverRate());
            model.setLiquidation(p.getLiquidation());
            model.setMoneyInContact(p.getMoneyInContact());
            model.setOutAmount(p.getOutAmount());
            model.setWornLine(p.getWornLine());
            model.setMoneyInDeal(p.getMoneyInDeal());
            return model;

    }
}
