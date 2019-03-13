package cn.com.fintheircing.admin.risk.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SystemRisk {

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private double customerMaxAccount;  //最大购买
    private double venturEditionMaxAccount;     //创业股最大购买
    private double holdOverFiveAvg;     //持仓不超过五日平均
    private double holdOverCurrency;       //持仓不超过流通市值
    private int stockShutDown;      //跌停能否购买

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
