package cn.com.fintheircing.admin.topLevel.model;

/**
 * HodingStockModel class
 *
 * @author YAOXIONG
 * @date 2019/2/27
 */
public class HodingStockModel {
    private String id;
    private String stockNum;
    private String stockName;
    private int  hodingMargin;//持仓差额
    private double closingPrice; //收盘价
    private double marginMarketValue;//差额市值
    private String parentId;//母账户ID
    private String parentAccountId;//母账户
    private String checkTime;//核查时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getHodingMargin() {
        return hodingMargin;
    }

    public void setHodingMargin(int hodingMargin) {
        this.hodingMargin = hodingMargin;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public double getMarginMarketValue() {
        return marginMarketValue;
    }

    public void setMarginMarketValue(double marginMarketValue) {
        this.marginMarketValue = marginMarketValue;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentAccountId() {
        return parentAccountId;
    }

    public void setParentAccountId(String parentAccountId) {
        this.parentAccountId = parentAccountId;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
}
