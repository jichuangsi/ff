package cn.com.fintheircing.customer.user.utlis;
/**
 * 把String类型的json转换成对象
 *
 * @author 姚雄
 * @date 2019/1/31
 */

import com.google.gson.Gson;

public class GsonUtil {
    public static <T> T jsonToObject(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);

        return result;
    }


}