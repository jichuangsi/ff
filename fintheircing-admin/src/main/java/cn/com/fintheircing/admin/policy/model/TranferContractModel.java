package cn.com.fintheircing.admin.policy.model;

import cn.com.fintheircing.admin.business.model.ContractModel;

public class TranferContractModel extends ContractModel {

    private Double contractSum;     //合约总金额
    private Double available;     //可用资金
    private Double sharePrice;     //股票价值
    private Double floatPrice;     //赢浮
    private Double leftWorth;     //净值
    private Integer borrowTime;    //借款天数
    private Integer leftTime;     //剩余天数
    private String status;     //状态

    public Double getContractSum() {
        return contractSum;
    }

    public void setContractSum(Double contractSum) {
        this.contractSum = contractSum;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public Double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Double sharePrice) {
        this.sharePrice = sharePrice;
    }

    public Double getFloatPrice() {
        return floatPrice;
    }

    public void setFloatPrice(Double floatPrice) {
        this.floatPrice = floatPrice;
    }

    public Double getLeftWorth() {
        return leftWorth;
    }

    public void setLeftWorth(Double leftWorth) {
        this.leftWorth = leftWorth;
    }

    public Integer getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Integer borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Integer getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Integer leftTime) {
        this.leftTime = leftTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
