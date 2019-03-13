package cn.com.fintheircing.admin.risk.model;

public class RiskModel {


    private String id;
    private double customerMaxAccount;  //最大购买
    private double venturEditionMaxAccount;     //创业股最大购买
    private double holdOverFiveAvg;     //持仓不超过五日平均
    private double holdOverCurrency;       //持仓不超过流通市值
    private int stockShutDown;      //跌停能否购买

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCustomerMaxAccount() {
        return customerMaxAccount;
    }

    public void setCustomerMaxAccount(double customerMaxAccount) {
        this.customerMaxAccount = customerMaxAccount;
    }

    public double getVenturEditionMaxAccount() {
        return venturEditionMaxAccount;
    }

    public void setVenturEditionMaxAccount(double venturEditionMaxAccount) {
        this.venturEditionMaxAccount = venturEditionMaxAccount;
    }

    public double getHoldOverFiveAvg() {
        return holdOverFiveAvg;
    }

    public void setHoldOverFiveAvg(double holdOverFiveAvg) {
        this.holdOverFiveAvg = holdOverFiveAvg;
    }

    public double getHoldOverCurrency() {
        return holdOverCurrency;
    }

    public void setHoldOverCurrency(double holdOverCurrency) {
        this.holdOverCurrency = holdOverCurrency;
    }

    public int getStockShutDown() {
        return stockShutDown;
    }

    public void setStockShutDown(int stockShutDown) {
        this.stockShutDown = stockShutDown;
    }
}
