package cn.com.fintheircing.admin.scheduling.model;

import cn.com.fintheircing.admin.common.feign.model.TodayOrder;

import java.util.HashMap;
import java.util.Map;

public class EntrustJsonModel {
    Map<String,TodayOrder> todayOrders = new HashMap<String,TodayOrder>();

    public Map<String, TodayOrder> getTodayOrders() {
        return todayOrders;
    }

    public void setTodayOrders(Map<String, TodayOrder> todayOrders) {
        this.todayOrders = todayOrders;
    }
}
