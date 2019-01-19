package cn.com.fintheircing.admin.business.model.tranfer;

import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import com.github.pagehelper.PageInfo;

public class TranferStockEntrustModel {
    private PageInfo<StockEntrustModel> pageInfo = new PageInfo<StockEntrustModel>();
    private String autoBuy;

    public PageInfo<StockEntrustModel> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<StockEntrustModel> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getAutoBuy() {
        return autoBuy;
    }

    public void setAutoBuy(String autoBuy) {
        this.autoBuy = autoBuy;
    }
}
