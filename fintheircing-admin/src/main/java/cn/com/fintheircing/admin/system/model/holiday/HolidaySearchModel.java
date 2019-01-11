package cn.com.fintheircing.admin.system.model.holiday;

public class HolidaySearchModel {

    private String searchTime;

    private long longtime;

    private Integer page;
    private Integer limit;

    public long getLongtime() {
        return longtime;
    }

    public void setLongtime(long longtime) {
        this.longtime = longtime;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
