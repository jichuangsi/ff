package cn.com.fintheircing.admin.dividend.utils;

import cn.com.fintheircing.admin.dividend.entity.DividendRelation;
import cn.com.fintheircing.admin.dividend.model.DividendModel;

public final class MappingEntity2ModelConverter {
    private MappingEntity2ModelConverter(){}

    public static DividendModel CONVERTERFROMDIVIDENDRELATION(DividendRelation relation){
        DividendModel model = new DividendModel();
        model.setId(relation.getUuid());
        model.setContractId(relation.getContractId());
        model.setMoney(relation.getTenStockMoney());
        model.setCost(relation.getTenStockCost());
        model.setAmount(relation.getTenStockAmount());
        if (DividendRelation.CHOSE_MONEY.equals(relation.getChoseWay())){
            model.setActiveTimeMoney(String.valueOf(relation.getHappenTime()));
        }else if (DividendRelation.CHOSE_STOCK.equals(relation.getChoseWay())){
            model.setActiveTimeAmount(String.valueOf(relation.getHappenTime()));
        }
        return model;
    }
}
