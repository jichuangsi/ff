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
    private Integer amount;     //持仓
    private Integer canSell;    //可用
    private Double costPrice;    //成本单价
    private Double currentPrice;    //   当前单价
    private Double currentWorth;    //当前市值
    private String motherAccount;   //母账号
    private Double floatMoney;      //盈亏金额
    private Double floatRate;       //盈亏比例
    private Integer coldAmount;
    @Version
    private Integer version;

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

    public Integer getColdAmount() {
        return coldAmount;
    }

    public void setColdAmount(Integer coldAmount) {
        this.coldAmount = coldAmount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCanSell() {
        return canSell;
    }

    public void setCanSell(Integer canSell) {
        this.canSell = canSell;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getCurrentWorth() {
        return currentWorth;
    }

    public void setCurrentWorth(Double currentWorth) {
        this.currentWorth = currentWorth;
    }

    public String getMotherAccount() {
        return motherAccount;
    }

    public void setMotherAccount(String motherAccount) {
        this.motherAccount = motherAccount;
    }

    public Double getFloatMoney() {
        return floatMoney;
    }

    public void setFloatMoney(Double floatMoney) {
        this.floatMoney = floatMoney;
    }

    public Double getFloatRate() {
        return floatRate;
    }

    public void setFloatRate(Double floatRate) {
        this.floatRate = floatRate;
    }
}
