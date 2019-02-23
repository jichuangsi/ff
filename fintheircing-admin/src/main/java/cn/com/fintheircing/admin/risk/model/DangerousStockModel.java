package cn.com.fintheircing.admin.risk.model;

import java.util.Date;

public class DangerousStockModel {

    private String holdingId;   //持仓id
    private String stockId;     //股票id
    private Integer stockStatus;    //股票状态
    private String stockCode;   //股票代码
    private String stockName;   //股票名称
    private Date createdTime;   //创建时间
    private Integer holdingAmount;  //持仓数量
    private Double holdingWorth;    //持仓价值
    private Integer globalAmount;   //全局数量
    private Double globalWorth;     //全局价值
    private Double dangerousRate;   //高危占比
    private Double globalRate;      //全局占比
    private String toBlack = "yes";     //是否能进黑名单

    public String getToBlack() {
        return toBlack;
    }

    public void setToBlack(String toBlack) {
        this.toBlack = toBlack;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Double getDangerousRate() {
        return dangerousRate;
    }

    public void setDangerousRate(Double dangerousRate) {
        this.dangerousRate = dangerousRate;
    }

    public Double getGlobalRate() {
        return globalRate;
    }

    public void setGlobalRate(Double globalRate) {
        this.globalRate = globalRate;
    }

    public String getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getHoldingAmount() {
        return holdingAmount;
    }

    public void setHoldingAmount(Integer holdingAmount) {
        this.holdingAmount = holdingAmount;
    }

    public Double getHoldingWorth() {
        return holdingWorth;
    }

    public void setHoldingWorth(Double holdingWorth) {
        this.holdingWorth = holdingWorth;
    }

    public Integer getGlobalAmount() {
        return globalAmount;
    }

    public void setGlobalAmount(Integer globalAmount) {
        this.globalAmount = globalAmount;
    }

    public Double getGlobalWorth() {
        return globalWorth;
    }

    public void setGlobalWorth(Double globalWorth) {
        this.globalWorth = globalWorth;
    }
}
