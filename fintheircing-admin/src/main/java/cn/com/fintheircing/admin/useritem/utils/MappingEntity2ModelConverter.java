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
    public static final TransactionModel coverWithAbsoluteWhiteList(TransactionSummary Summary){
        TransactionModel model=new TransactionModel();
        model.setAlphabetCapitalization(Summary.getAlphabetCapitalization());
        model.setJoinTime(Summary.getJoinTime());
        model.setMartTemplate(Summary.getMartTemplate());
        model.setRemake(Summary.getRemake());
        model.setStockNum(Summary.getStockNum());
        model.setId(Summary.getId());
        model.setStockName(Summary.getStockName());
        model.setStatus(Summary.getStatus());
        return model;
    }
    public static final TransactionModel coverWithStaticBlackList(TransactionSummary Summary){
        TransactionModel model=new TransactionModel();
        model.setAlphabetCapitalization(Summary.getAlphabetCapitalization());
        model.setJoinTime(Summary.getJoinTime());
        model.setMartTemplate(Summary.getMartTemplate());
        model.setRemake(Summary.getRemake());
        model.setId(Summary.getId());
        model.setStockNum(Summary.getStockNum());
        model.setStockName(Summary.getStockName());
        model.setStatus(Summary.getStatus());
        return model;
    }
}
