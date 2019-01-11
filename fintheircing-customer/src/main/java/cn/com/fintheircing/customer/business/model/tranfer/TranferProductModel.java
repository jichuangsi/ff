package cn.com.fintheircing.customer.business.model.tranfer;

import cn.com.fintheircing.customer.business.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class TranferProductModel{
    String canBuy;

    List<ProductModel> productModels = new ArrayList<ProductModel>();

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public String getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(String canBuy) {
        this.canBuy = canBuy;
    }
}
