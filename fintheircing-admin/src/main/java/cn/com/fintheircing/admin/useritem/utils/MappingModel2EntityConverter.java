package cn.com.fintheircing.admin.useritem.utils;

import cn.com.fintheircing.admin.useritem.common.TransactionSummaryStatus;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;

public final class MappingModel2EntityConverter {
    private MappingModel2EntityConverter(){}


    public static TransactionSummary coverWithAbsoluteWhiteList(TransactionModel model) {
        TransactionSummary t =new TransactionSummary();
        t.setStatus(TransactionSummaryStatus.getIndex(model.getStatus()));
        t.setAlphabetCapitalization(model.getAlphabetCapitalization());
        t.setJoinTime(model.getJoinTime());
        t.setMartTemplate(model.getMartTemplate());
        t.setRemake(model.getRemake());
        t.setId(model.getId());
        t.setStockNum(model.getStockNum());
        t.setStockName(model.getStockName());
        return t;
    }

    public static TransactionSummary coverWithStaticBlackList(TransactionModel model) {
        TransactionSummary t =new TransactionSummary();
        t.setStatus(TransactionSummaryStatus.getIndex(model.getStatus()));
        t.setAlphabetCapitalization(model.getAlphabetCapitalization());
        t.setJoinTime(model.getJoinTime());
        t.setMartTemplate(model.getMartTemplate());
        t.setRemake(model.getRemake());
        t.setId(model.getId());
        t.setStockNum(model.getStockNum());
        t.setStockName(model.getStockName());
        return t;
    }
}
