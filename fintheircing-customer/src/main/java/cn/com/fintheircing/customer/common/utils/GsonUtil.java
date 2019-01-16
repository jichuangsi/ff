package cn.com.fintheircing.customer.common.utils;
/**
 * 把String类型的json转换成对象
 *
 * @author keriezhang
 * @date 2016/10/31
 */
import com.google.gson.Gson;

public class GsonUtil {
        public static <T> T jsonToObject(String jsonData, Class<T> type) {
            Gson gson = new Gson();
            T result = gson.fromJson(jsonData, type);
            return result;
        }

}
