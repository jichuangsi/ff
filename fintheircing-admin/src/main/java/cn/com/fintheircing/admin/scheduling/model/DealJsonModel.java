package cn.com.fintheircing.admin.scheduling.model;

import cn.com.fintheircing.admin.common.feign.model.TodayAcceptOrder;

import java.util.HashMap;
import java.util.Map;

public class DealJsonModel {

    Map<String,TodayAcceptOrder> map = new HashMap<String,TodayAcceptOrder>();

    public Map<String, TodayAcceptOrder> getMap() {
        return map;
    }

    public void setMap(Map<String, TodayAcceptOrder> map) {
        this.map = map;
    }
}
