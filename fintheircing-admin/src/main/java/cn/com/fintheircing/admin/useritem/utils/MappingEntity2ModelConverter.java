package cn.com.fintheircing.admin.useritem.utils;

import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class MappingEntity2ModelConverter {
    public static final List<TransactionModel> coverListSummaryWithAbsoluteWhiteList(List<TransactionSummary> Summary) {
        List<TransactionModel> models = new ArrayList<>();
        TransactionModel model = new TransactionModel();
        Summary.forEach(s -> {
            model.setAlphabetCapitalization(s.getAlphabetCapitalization());
            model.setJoinTime(s.getJoinTime());
            model.setMartTemplate(s.getMartTemplate());
            model.setRemake(s.getRemake());
            model.setId(s.getId());
            model.setStockNum(s.getStockNum());
            model.setStockName(s.getStockName());
            models.add(model);
        });
        return models;
    }
  
}
