package cn.com.fintheircing.admin.business.model;

import java.util.Date;

public class ContractControlModel {

    private String id;
    private String contractNo;  //合约编号
    private String userId;
    private String phone;   //电话
    private String name;    //姓名
    private String controlStr;  //操作
    private Integer controlNum;     //操作num
    private Date createdTime;   //操作时间
    private String productStr;  //产品
    private Integer productNum; //产品num
    private Double borrowMoney; //借款
    private Double promisedMoney;   //保证金
    private Double gainMoney;   //收益
    private Double endMoney;    //结算
    private Double warnningLine;    //预警线
    private Double exWarnningLine;//修改后的预警线
    private Double abortLine;   //平仓线
    private Double exAbortLine;//修改后的平仓线
    private Double borrowRate;  //借款利率
    private Date borrowTime;    //借款时间
    private Long expiredTime; //还款时间
    private Long lessTime;//剩余天数
    private Double firstMoney;
    private Double lessMoney;   //剩余金额 /可用金额
    private Integer verifyStatus;   //审核num
    private String verifyStr;   //审核
    private String productId;
    private double currentWorth;//当前市值
    private double totalAssets;//总资产
    private double netAssets;//净资产
    private String proxyNum;//代理
    private String proxyName;//代理名称
    private Integer pageSize;
    private Integer pageIndex;
    private double coldMoney;//冻结资金
    private String businessControlContractId;
    private String remark;//备注
    private String BusinessContractId;//合约Id
    private int stockAmount;//股票持仓
    private double firstInterest;//初次利息

    public double getFirstInterest() {
        return firstInterest;
    }

    public void setFirstInterest(double firstInterest) {
        this.firstInterest = firstInterest;
    }

    public Double getExWarnningLine() {
        return exWarnningLine;
    }

    public void setExWarnningLine(Double exWarnningLine) {
        this.exWarnningLine = exWarnningLine;
    }

    public Double getExAbortLine() {
        return exAbortLine;
    }

    public void setExAbortLine(Double exAbortLine) {
        this.exAbortLine = exAbortLine;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    public String getBusinessContractId() {
        return BusinessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        BusinessContractId = businessContractId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBusinessControlContractId() {
        return businessControlContractId;
    }

    public void setBusinessControlContractId(String businessControlContractId) {
        this.businessControlContractId = businessControlContractId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getColdMoney() {
        return coldMoney;
    }

    public void setColdMoney(double coldMoney) {
        this.coldMoney = coldMoney;
    }

    public String getProxyNum() {
        return proxyNum;
    }

    public void setProxyNum(String proxyNum) {
        this.proxyNum = proxyNum;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public double getNetAssets() {
        return netAssets;
    }

    public void setNetAssets(double netAssets) {
        this.netAssets = netAssets;
    }

    public Long getLessTime() {
        return lessTime;
    }

    public void setLessTime(Long lessTime) {
        this.lessTime = lessTime;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public double getCurrentWorth() {
        return currentWorth;
    }

    public void setCurrentWorth(double currentWorth) {
        this.currentWorth = currentWorth;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getFirstMoney() {
        return firstMoney;
    }

    public void setFirstMoney(Double firstMoney) {
        this.firstMoney = firstMoney;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControlStr() {
        return controlStr;
    }

    public void setControlStr(String controlStr) {
        this.controlStr = controlStr;
    }

    public Integer getControlNum() {
        return controlNum;
    }

    public void setControlNum(Integer controlNum) {
        this.controlNum = controlNum;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getProductStr() {
        return productStr;
    }

    public void setProductStr(String productStr) {
        this.productStr = productStr;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
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

    public Double getGainMoney() {
        return gainMoney;
    }

    public void setGainMoney(Double gainMoney) {
        this.gainMoney = gainMoney;
    }

    public Double getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(Double endMoney) {
        this.endMoney = endMoney;
    }

    public Double getWarnningLine() {
        return warnningLine;
    }

    public void setWarnningLine(Double warnningLine) {
        this.warnningLine = warnningLine;
    }

    public Double getAbortLine() {
        return abortLine;
    }

    public void setAbortLine(Double abortLine) {
        this.abortLine = abortLine;
    }

    public Double getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(Double borrowRate) {
        this.borrowRate = borrowRate;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Double getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(Double lessMoney) {
        this.lessMoney = lessMoney;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyStr() {
        return verifyStr;
    }

    public void setVerifyStr(String verifyStr) {
        this.verifyStr = verifyStr;
    }
}
