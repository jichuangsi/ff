package cn.com.fintheircing.customer.business.model;



import java.util.Date;

public class ContractModel {

    private String id;    //合约id
    private String contractNum;   //合约编号
    private String userPhone;    //用户电话
    private String userName;    //用户姓名
    private Date  createdTime;     //合约开始时间
    private String productName;     //产品名称
    private Double borrowMoney;     //借款
    private Double promisedMoney;      //保证金
    private Double warningLine;     //预警线
    private Double abortLine;     //合约中止线
    private String userId;     //用户人
    private String saleManId;     //上级操控人
    private Double canUseMoney;      //可用余额
    private Double worth;       //市值
    private Double floatMoney;      //盈亏

    private Integer choseWay;    //选择方式
    private Double coldCash;    //冻结资金
    private Double dangerCash;    //危险保证金
    private String productId;

    private String choseStr;

    private Double first;   //初次利息

    private ProductModel productModel ;


    private Integer pageIndex;
    private Integer pageSize;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getChoseStr() {
        return choseStr;
    }

    public void setChoseStr(String choseStr) {
        this.choseStr = choseStr;
    }

    public Double getFirst() {
        return first;
    }

    public void setFirst(Double first) {
        this.first = first;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    public Double getDangerCash() {
        return dangerCash;
    }

    public void setDangerCash(Double dangerCash) {
        this.dangerCash = dangerCash;
    }

    public Double getColdCash() {
        return coldCash;
    }

    public void setColdCash(Double coldCash) {
        this.coldCash = coldCash;
    }

    public Integer getChoseWay() {
        return choseWay;
    }

    public void setChoseWay(Integer choseWay) {
        this.choseWay = choseWay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(Double borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public Double getPromisedMoney() {
        return promisedMoney;
    }

    public void setPromisedMoney(Double promisedMoney) {
        this.promisedMoney = promisedMoney;
    }

    public Double getWarningLine() {
        return warningLine;
    }

    public void setWarningLine(Double warningLine) {
        this.warningLine = warningLine;
    }

    public Double getAbortLine() {
        return abortLine;
    }

    public void setAbortLine(Double abortLine) {
        this.abortLine = abortLine;
    }

    public Double getCanUseMoney() {
        return canUseMoney;
    }

    public void setCanUseMoney(Double canUseMoney) {
        this.canUseMoney = canUseMoney;
    }

    public Double getWorth() {
        return worth;
    }

    public void setWorth(Double worth) {
        this.worth = worth;
    }

    public Double getFloatMoney() {
        return floatMoney;
    }

    public void setFloatMoney(Double floatMoney) {
        this.floatMoney = floatMoney;
    }
}
