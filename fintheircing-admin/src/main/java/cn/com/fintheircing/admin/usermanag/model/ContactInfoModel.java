package cn.com.fintheircing.admin.usermanag.model;

import cn.com.fintheircing.admin.common.model.AbstractEntityModel;

import java.util.Date;

public class ContactInfoModel extends AbstractEntityModel {
    private String contactId;//合约编号
    private Date createTime;//发生时间
    private String goodsType;//产品信息
    private String borrowMoney;//借款金额
    private Date borrowTime;//借款时间
    private Date remainderTime;//剩余时间
    private String assureMoney;//保证金金额
    private String deficitAmount;//亏损警戒线
    private String deficitLine;//亏损平仓线
    private String contactAllMoney;//合约总资产
    private String availableCash;//可用余额
    private String stockValue;//股票市值
    private String floatFilled;//浮盈
    private String status;//状态
    private String taskStatus;//审核状态
    private String borrowRate;//借款利率
    private String borrowDays;//借款天数
    private String remainderDays;//剩余天数
    private String firstRate;//初次利息
    private String dangerLevel;//风控级别
    private String transactionCommission;//交易佣金
    private String delegationOrigin;//委托来源
    private String ContactAssets;//合约净资产
    private String stockId;//股票代码ID
    private String stockName;//股票名称
    private String stockAccount;//数量
    private String buyPrice;//买入价
    private String MarketPrice;//市价
    private String MarketValue;//市值
    private String buyTime;//购入时间

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockAccount() {
        return stockAccount;
    }

    public void setStockAccount(String stockAccount) {
        this.stockAccount = stockAccount;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        MarketPrice = marketPrice;
    }

    public String getMarketValue() {
        return MarketValue;
    }

    public void setMarketValue(String marketValue) {
        MarketValue = marketValue;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(String borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getRemainderTime() {
        return remainderTime;
    }

    public void setRemainderTime(Date remainderTime) {
        this.remainderTime = remainderTime;
    }

    public String getAssureMoney() {
        return assureMoney;
    }

    public void setAssureMoney(String assureMoney) {
        this.assureMoney = assureMoney;
    }

    public String getDeficitAmount() {
        return deficitAmount;
    }

    public void setDeficitAmount(String deficitAmount) {
        this.deficitAmount = deficitAmount;
    }

    public String getDeficitLine() {
        return deficitLine;
    }

    public void setDeficitLine(String deficitLine) {
        this.deficitLine = deficitLine;
    }

    public String getContactAllMoney() {
        return contactAllMoney;
    }

    public void setContactAllMoney(String contactAllMoney) {
        this.contactAllMoney = contactAllMoney;
    }

    public String getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(String availableCash) {
        this.availableCash = availableCash;
    }

    public String getStockValue() {
        return stockValue;
    }

    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }

    public String getFloatFilled() {
        return floatFilled;
    }

    public void setFloatFilled(String floatFilled) {
        this.floatFilled = floatFilled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(String borrowRate) {
        this.borrowRate = borrowRate;
    }

    public String getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(String borrowDays) {
        this.borrowDays = borrowDays;
    }

    public String getRemainderDays() {
        return remainderDays;
    }

    public void setRemainderDays(String remainderDays) {
        this.remainderDays = remainderDays;
    }

    public String getFirstRate() {
        return firstRate;
    }

    public void setFirstRate(String firstRate) {
        this.firstRate = firstRate;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public String getTransactionCommission() {
        return transactionCommission;
    }

    public void setTransactionCommission(String transactionCommission) {
        this.transactionCommission = transactionCommission;
    }

    public String getDelegationOrigin() {
        return delegationOrigin;
    }

    public void setDelegationOrigin(String delegationOrigin) {
        this.delegationOrigin = delegationOrigin;
    }

    public String getContactAssets() {
        return ContactAssets;
    }

    public void setContactAssets(String contactAssets) {
        ContactAssets = contactAssets;
    }
}
