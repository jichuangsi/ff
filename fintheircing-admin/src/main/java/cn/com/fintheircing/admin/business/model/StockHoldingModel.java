package cn.com.fintheircing.admin.business.model;

import javax.validation.constraints.Pattern;

public class StockHoldingModel {

    private String id;
    private String stockId;
    @Pattern(regexp = "^\\d{6}$",message = "股票代码不正确")
    private String stockNo;
    private String stockName;
    private Integer amount;     //持仓
    private Integer canSell;    //可用
    private Double costPrice;    //成本单价
    private Double currentPrice;    //   当前单价
    private Double currentWorth;    //当前市值
    private String motherAccount;   //母账号
    private Double floatMoney;      //盈亏金额
    private Double floatRate;
    private String contractId;
    private String userId;
    private String dealFrom;

    private Double oneDay;
    private Double twoDay;
    private Double threeDay;
    private Double fourDay;
    private Double fiveDay;

    public String getDealFrom() {
        return dealFrom;
    }

    public void setDealFrom(String dealFrom) {
        this.dealFrom = dealFrom;
    }

    public Double getOneDay() {
        return oneDay;
    }

    public void setOneDay(Double oneDay) {
        this.oneDay = oneDay;
    }

    public Double getTwoDay() {
        return twoDay;
    }

    public void setTwoDay(Double twoDay) {
        this.twoDay = twoDay;
    }

    public Double getThreeDay() {
        return threeDay;
    }

    public void setThreeDay(Double threeDay) {
        this.threeDay = threeDay;
    }

    public Double getFourDay() {
        return fourDay;
    }

    public void setFourDay(Double fourDay) {
        this.fourDay = fourDay;
    }

    public Double getFiveDay() {
        return fiveDay;
    }

    public void setFiveDay(Double fiveDay) {
        this.fiveDay = fiveDay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCanSell() {
        return canSell;
    }

    public void setCanSell(Integer canSell) {
        this.canSell = canSell;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getCurrentWorth() {
        return currentWorth;
    }

    public void setCurrentWorth(Double currentWorth) {
        this.currentWorth = currentWorth;
    }

    public String getMotherAccount() {
        return motherAccount;
    }

    public void setMotherAccount(String motherAccount) {
        this.motherAccount = motherAccount;
    }

    public Double getFloatMoney() {
        return floatMoney;
    }

    public void setFloatMoney(Double floatMoney) {
        this.floatMoney = floatMoney;
    }

    public Double getFloatRate() {
        return floatRate;
    }

    public void setFloatRate(Double floatRate) {
        this.floatRate = floatRate;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
