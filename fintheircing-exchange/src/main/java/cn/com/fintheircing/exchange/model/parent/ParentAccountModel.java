package cn.com.fintheircing.exchange.model.parent;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * ParentAccountModel
 *
 * @author YAOXIONG
 * @date 2019/1/18
 */
public class ParentAccountModel {
    //交易账号
    private String tradeAccount;
    //交易持有人
    private String tradeName;
    //初期金额
    private String beginAmount;
    //证券
    private String Securities;
    private Date createTime;
    //账户资金
    private double amount;
    //持仓市值
    private double marketValue;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
