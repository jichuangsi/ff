package cn.com.fintheircing.admin.scheduling.model;

import cn.com.fintheircing.admin.common.feign.model.TodayAcceptOrder;

import java.util.HashMap;
import java.util.Map;

public class DealJsonModel {

    Map<String,TodayAcceptOrder> todayAcceptOrders = new HashMap<String,TodayAcceptOrder>();

    public Map<String, TodayAcceptOrder> getTodayAcceptOrders() {
        return todayAcceptOrders;
    }

    public void setTodayAcceptOrders(Map<String, TodayAcceptOrder> todayAcceptOrders) {
        this.todayAcceptOrders = todayAcceptOrders;
    }
}
