package cn.com.fintheircing.admin.scheduling.utils;

import cn.com.fintheircing.admin.common.feign.model.TodayAcceptOrder;
import com.alibaba.fastjson.JSONObject;

public final class DealUtils {

    public static Boolean BALANCEDEALORDER(TodayAcceptOrder newOrder,TodayAcceptOrder oldOrder){
        String newO = JSONObject.toJSONString(newOrder).trim();
        String oldO = JSONObject.toJSONString(oldOrder).trim();
        if (newO.equals(oldO)){
            return true;
        }
        return false;
    }
}
