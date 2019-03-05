package cn.com.fintheircing.admin.usermanag.uilts;
/**
 * 把String类型的json转换成对象
 *
 * @author keriezhang
 * @date 2016/10/31
 */
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class GsonUtil {
        public static <T> T jsonToObject(String jsonData, Class<T> type) {
            Gson gson = new Gson();
            T result = gson.fromJson(jsonData, type);

            return result;
        }

    public static void main(String[] args) {
            String Jsonstr="{\"a\":[{\"b\":\"2\"},{\"c\":\"3\"},{\"d\":\"4\"},{\"e\":\"5\"}]}";
        JSONObject j =new JSONObject();
        JSONObject jsonObject = j.getJSONObject(Jsonstr);
        String a = jsonObject.getString("a");
//        String a = jsonObject.get("a").toString();
        String arr[]=a.split(",");
        System.out.println(arr);
        Map<String,String> map=new HashMap<>();
        for (int i=0;i<arr.length;i++){
            map.put(arr[i].split(";")[0],arr[i].split(";")[1]);
        }
        System.out.println(map);
    }
}
