package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * 持仓
 */
@Entity
public class BusinessStockHolding extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String contractId;  //相关合约编号
    private String stockId;     //关联股票
    private int amount;     //持仓
    private int canSell;    //可用
    private double costPrice;    //成本单价
    private double currentPrice;    //   当前单价
    private double currentWorth;    //当前市值
    private String motherAccount;   //母账号
    private double floatMoney;      //盈亏金额
    private double floatRate;       //盈亏比例
    private int coldAmount;
    private int rudeEnd;
    @Version
    private int version;

    public int getRudeEnd() {
        return rudeEnd;
    }

    public void setRudeEnd(int rudeEnd) {
        this.rudeEnd = rudeEnd;
    }

    public BusinessStockHolding() {
    }

    public BusinessStockHolding(String contractId, String stockId, Integer amount, Integer canSell, Double costPrice, Double currentPrice, Double currentWorth, String motherAccount, Double floatMoney, Double floatRate, Integer coldAmount, Integer version) {
        this.contractId = contractId;
        this.stockId = stockId;
        this.amount = amount;
        this.canSell = canSell;
        this.costPrice = costPrice;
        this.currentPrice = currentPrice;
        this.currentWorth = currentWorth;
        this.motherAccount = motherAccount;
        this.floatMoney = floatMoney;
        this.floatRate = floatRate;
        this.coldAmount = coldAmount;
        this.version = version;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCanSell() {
        return canSell;
    }

    public void setCanSell(int canSell) {
        this.canSell = canSell;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getCurrentWorth() {
        return currentWorth;
    }

    public void setCurrentWorth(double currentWorth) {
        this.currentWorth = currentWorth;
    }

    public String getMotherAccount() {
        return motherAccount;
    }

    public void setMotherAccount(String motherAccount) {
        this.motherAccount = motherAccount;
    }

    public double getFloatMoney() {
        return floatMoney;
    }

    public void setFloatMoney(double floatMoney) {
        this.floatMoney = floatMoney;
    }

    public double getFloatRate() {
        return floatRate;
    }

    public void setFloatRate(double floatRate) {
        this.floatRate = floatRate;
    }

    public int getColdAmount() {
        return coldAmount;
    }

    public void setColdAmount(int coldAmount) {
        this.coldAmount = coldAmount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
