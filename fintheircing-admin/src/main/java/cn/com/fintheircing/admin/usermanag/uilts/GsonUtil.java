package cn.com.fintheircing.admin.usermanag.uilts;
/**
 * 把String类型的json转换成对象
 *
 * @author 姚雄
 * @date 2019/1/31
 */
import cn.com.fintheircing.admin.usermanag.eum.EumDemo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sun.javafx.collections.SortHelper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;

import java.util.*;

public class GsonUtil {
    public static <T> T jsonToObject(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);

        return result;
    }

    public static void main(String[] args) {

    }
}