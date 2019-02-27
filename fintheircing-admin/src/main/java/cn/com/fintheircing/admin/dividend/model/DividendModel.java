package cn.com.fintheircing.admin.dividend.model;

import java.util.Date;

public class DividendModel {

    private String id;
    private String stockId;
    private String stockCode;   //股份代码
    private String stockName;   //股票名
    private String dividendId;  //除权除息id
    private Double money;   //金钱
    private Integer amount;     //股份数量
    private String activeTimeMoney;     //添加金钱时间
    private String activeTimeAmount;    //添加股份数量时间
    private Double cost;    //股份扣款
    private Integer dividendAmount; //除权除息 总股份 基数

    private String userName;    //用户名
    private String contractId;  //合约id
    private String contractNo;  //合约编号
    private Date createdTime;   //创建时间
    private String crossNum;    //经手人编号

    public Integer getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(Integer dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    public String getDividendId() {
        return dividendId;
    }

    public void setDividendId(String dividendId) {
        this.dividendId = dividendId;
    }

    private long happenTime;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(long happenTime) {
        this.happenTime = happenTime;
    }

    public String getCrossNum() {
        return crossNum;
    }

    public void setCrossNum(String crossNum) {
        this.crossNum = crossNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getActiveTimeMoney() {
        return activeTimeMoney;
    }

    public void setActiveTimeMoney(String activeTimeMoney) {
        this.activeTimeMoney = activeTimeMoney;
    }

    public String getActiveTimeAmount() {
        return activeTimeAmount;
    }

    public void setActiveTimeAmount(String activeTimeAmount) {
        this.activeTimeAmount = activeTimeAmount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
