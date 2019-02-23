package cn.com.fintheircing.admin.dividend.model;

import java.util.Date;

public class DividendModel {

    private String id;
    private String stockId;
    private String stockCode;
    private String stockName;
    private String dividendId;
    private Double money;
    private Integer amount;
    private String activeTimeMoney;
    private String activeTimeAmount;
    private Double cost;
    private Integer dividendAmount;

    private String userName;
    private String contractId;
    private String contractNo;
    private Date createdTime;
    private String crossNum;

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
