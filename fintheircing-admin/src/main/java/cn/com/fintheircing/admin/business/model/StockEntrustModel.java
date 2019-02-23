package cn.com.fintheircing.admin.business.model;

import java.util.Date;

public class StockEntrustModel {
    public static String ENTRUST_RUDE = "1";

    private String id;
    private String userName;    //用户名
    private String stockNum;    //股票代码
    private String stockName;   //股票名
    private Integer business;   //买卖方向num
    private Integer status;     //状态num
    private String businessStr;     //买卖方向
    private String statusStr;   //状态
    private Double price;   //委托价格
    private Integer amount;     //委托数量
    private Integer dealNum;    //成交数量
    private long dealTime;  //成交时间
    private String contractId;  //合约id
    private String userId;  //用户id
    private String motherAccount;   //母账号
    private String dealNo;  //委托单号
    private Double dealPrice;   //成交单价
    private String cancelOrder;     //是否撤单
    private Date createdTime;   //委托时间
    private String contractNum;     //合约编号
    private String rudeStatus;

    public String getRudeStatus() {
        return rudeStatus;
    }

    public void setRudeStatus(String rudeStatus) {
        this.rudeStatus = rudeStatus;
    }

    private Integer pageIndex;
    private Integer pageSize;

    public Double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(String cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }

    public String getMotherAccount() {
        return motherAccount;
    }

    public void setMotherAccount(String motherAccount) {
        this.motherAccount = motherAccount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBusinessStr() {
        return businessStr;
    }

    public void setBusinessStr(String businessStr) {
        this.businessStr = businessStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDealNum() {
        return dealNum;
    }

    public void setDealNum(Integer dealNum) {
        this.dealNum = dealNum;
    }

    public long getDealTime() {
        return dealTime;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
}
