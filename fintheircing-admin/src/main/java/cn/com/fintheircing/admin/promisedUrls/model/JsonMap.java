package cn.com.fintheircing.admin.promisedUrls.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMap {
    private Map<String,List<UrlsModel>> map = new HashMap<String,List<UrlsModel>>();

    public Map<String, List<UrlsModel>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<UrlsModel>> map) {
        this.map = map;
    }
}
