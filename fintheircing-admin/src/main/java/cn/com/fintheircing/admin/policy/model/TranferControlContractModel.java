package cn.com.fintheircing.admin.policy.model;

import cn.com.fintheircing.admin.business.model.ContractModel;

public class TranferControlContractModel  extends ContractModel{

    private String controlId;
    private String productId;
    private Double cashMoney;
    private Double balanceMoney;

    private Double borrowPercent;
    private Integer borrowDays;
    private Double firstInterest;
    private Double lessMoney;
    private String status;

    public String getControlId() {
        return controlId;
    }

    public void setControlId(String controlId) {
        this.controlId = controlId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(Double cashMoney) {
        this.cashMoney = cashMoney;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public Double getBorrowPercent() {
        return borrowPercent;
    }

    public void setBorrowPercent(Double borrowPercent) {
        this.borrowPercent = borrowPercent;
    }

    public Integer getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(Integer borrowDays) {
        this.borrowDays = borrowDays;
    }

    public Double getFirstInterest() {
        return firstInterest;
    }

    public void setFirstInterest(Double firstInterest) {
        this.firstInterest = firstInterest;
    }

    public Double getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(Double lessMoney) {
        this.lessMoney = lessMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
