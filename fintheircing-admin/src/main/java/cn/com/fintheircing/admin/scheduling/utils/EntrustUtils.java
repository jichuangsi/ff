package cn.com.fintheircing.admin.scheduling.utils;

import cn.com.fintheircing.admin.common.feign.model.TodayOrder;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntrustUtils {

    public static Map<String,TodayOrder> CONVERTERTODAYORDER(List<TodayOrder> orders){
        Map<String,TodayOrder> map = new HashMap<String,TodayOrder>();
        orders.forEach(order->{
            map.put(order.getOrderNumber(),order);
        });
        return map;
    }

    public static Boolean BALANCETODAYORDER(TodayOrder newOrder,TodayOrder oldOrder){
        String newO = JSON.toJSONString(newOrder).trim();
        String oldO = JSON.toJSONString(oldOrder).trim();
        if (newO.equals(oldO)){
            return true;
        }
        return false;
    }
}
