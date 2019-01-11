package cn.com.fintheircing.customer.common.feign.model;

import javax.validation.constraints.Pattern;

public class StockEntrustModel {

    private String id;
    private String userName;
    @Pattern(regexp = "^(?i)s[hz]\\d{6}$",message = "股票代码不符合规范")
    private String stockNum;
    private String stockName;
    private Integer business;
    private Integer status;
    private String businessStr;
    private String statusStr;
    private Double price;
    @Pattern(regexp = "^[1-9][0-9]*0{2}$",message = "购买数量必须是100的倍数")
    private Integer amount;
    private String dealNum;
    private String dealTime;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBusinessStr() {
        return businessStr;
    }

    public void setBusinessStr(String businessStr) {
        this.businessStr = businessStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDealNum() {
        return dealNum;
    }

    public void setDealNum(String dealNum) {
        this.dealNum = dealNum;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}
