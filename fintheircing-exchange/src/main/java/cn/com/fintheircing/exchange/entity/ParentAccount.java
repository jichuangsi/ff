package cn.com.fintheircing.exchange.entity;

import cn.com.fintheircing.exchange.constant.AccountStatus;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
/**
 * 母账户
 *
 * @author yaoxiong
 * @date 2019/1/18
 */
@Entity
@Table(name = "exchange_ParentAccount")
public class ParentAccount {
    private String uuid;
    private String tradeAccount;//交易账号
    private String tradeName;//交易持有人
    private String beginAmount;//初期金额
    private String Securities;//证券
    private Date createTime=new Date();
    private double amount;//账户资金
    private double marketValue;//持仓市值
    private int status=0;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(String beginAmount) {
        this.beginAmount = beginAmount;
    }

    public String getSecurities() {
        return Securities;
    }

    public void setSecurities(String securities) {
        Securities = securities;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
