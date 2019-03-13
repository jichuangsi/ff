package cn.com.fintheircing.admin.common.feign.model;

import java.util.Date;

public class FlowModel {

    private String id;
    private Date createdTime;
    private Double afterMoney;
    private Double happenMoney;
    private Double businessMoney;
    private Integer control;
    private String contolType;
    private String stockCode;
    private String stockName;
    private Double beforeMoney;

    public Double getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(Double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public Integer getControl() {
        return control;
    }

    public void setControl(Integer control) {
        this.control = control;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Double getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(Double afterMoney) {
        this.afterMoney = afterMoney;
    }

    public Double getHappenMoney() {
        return happenMoney;
    }

    public void setHappenMoney(Double happenMoney) {
        this.happenMoney = happenMoney;
    }

    public Double getBusinessMoney() {
        return businessMoney;
    }

    public void setBusinessMoney(Double businessMoney) {
        this.businessMoney = businessMoney;
    }

    public String getContolType() {
        return contolType;
    }

    public void setContolType(String contolType) {
        this.contolType = contolType;
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
}
