package cn.com.fintheircing.customer.user.model.contract;

import java.util.Date;

public class AccountInfoModel {
    private Date createdTime;//发生时间
    private String payway;//支付宝或者微信
    private double amount;//支付或者收入金额
    private double lessMoney;//可用余额
    private double coldMoney;//冻结资金
    private Integer rate;//标识符
    private String rateStr;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getRateStr() {
        return rateStr;
    }

    public void setRateStr(String rateStr) {
        this.rateStr = rateStr;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(double lessMoney) {
        this.lessMoney = lessMoney;
    }

    public double getColdMoney() {
        return coldMoney;
    }

    public void setColdMoney(double coldMoney) {
        this.coldMoney = coldMoney;
    }
}
