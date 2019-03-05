package cn.com.fintheircing.customer.user.model.contact;

import java.util.Date;

public class contactModel {
    private Date createTime;
    private int rate;
    private String allot;//日配.月配,特殊配
    private double allValue;//总资产
    private double marketValue;//市值
    private double profit;//盈利
    private double capital;//本金
    private double availableMoney;//可用余额

    public double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(double availableMoney) {
        this.availableMoney = availableMoney;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getAllot() {
        return allot;
    }

    public void setAllot(String allot) {
        this.allot = allot;
    }

    public double getAllValue() {
        return allValue;
    }

    public void setAllValue(double allValue) {
        this.allValue = allValue;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
