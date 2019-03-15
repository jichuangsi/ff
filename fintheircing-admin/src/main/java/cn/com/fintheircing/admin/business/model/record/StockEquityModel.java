package cn.com.fintheircing.admin.business.model.record;

import java.util.Date;

public class StockEquityModel {
    private String srId;//股票操作记录Id(StockEquityRecord)
    private String id;
    private String contractId;  //合约id
    private String applyType;   //增还是减
    private String remarks; //备注
    private String stockName;   //股票名
    private String stockCode;   //股票代码
    private Integer amount; //数量
    private Double dealPrice;   //成交价格
    private Double balance; //差额
    private String submitterName;   //提交人姓名
    private String submitterId;   //提交人ID
    private String contractNo;  //合约编号
    private String userCode;    //用户编号
    private String userName;    //用户名
    private String userPhone;   //用户电话
    private double userfulMoney;//账户可用余额
    private double codeMoney;//冻结资金
    private Date createdTime;   //创建时间
    private Integer falg;//增减标识符
    private String buyTime;

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public Integer getFalg() {
        return falg;
    }

    public void setFalg(Integer falg) {
        this.falg = falg;
    }

    public double getUserfulMoney() {
        return userfulMoney;
    }

    public void setUserfulMoney(double userfulMoney) {
        this.userfulMoney = userfulMoney;
    }

    public double getCodeMoney() {
        return codeMoney;
    }

    public void setCodeMoney(double codeMoney) {
        this.codeMoney = codeMoney;
    }


    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
    }
}
