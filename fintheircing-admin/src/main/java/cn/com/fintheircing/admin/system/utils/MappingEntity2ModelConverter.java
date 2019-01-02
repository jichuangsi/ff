package cn.com.fintheircing.admin.system.utils;

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
            model.setStockId(s.getStockId());
            model.setStockName(s.getStockName());
            models.add(model);
        });
        return models;
    }
    public static final TransactionModel coverWithAbsoluteWhiteList(TransactionSummary Summary){
        TransactionModel model=new TransactionModel();
        model.setAlphabetCapitalization(Summary.getAlphabetCapitalization());
        model.setJoinTime(Summary.getJoinTime());
        model.setMartTemplate(Summary.getMartTemplate());
        model.setRemake(Summary.getRemake());
        model.setStockId(Summary.getStockId());
        model.setStockName(Summary.getStockName());
        model.setStatus(Summary.getStatus());
        return model;
    }
    public static final TransactionModel coverWithStaticBlackList(TransactionSummary summary){
        TransactionModel model=new TransactionModel();
        model.setAlphabetCapitalization(summary.getAlphabetCapitalization());
        model.setJoinTime(summary.getJoinTime());
        model.setMartTemplate(summary.getMartTemplate());
        model.setRemake(summary.getRemake());
        model.setStockId(summary.getStockId());
        model.setStockName(summary.getStockName());
        model.setStatus(summary.getStatus());
        return model;
    }
}
