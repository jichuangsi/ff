package cn.com.fintheircing.admin.business.model;

public class ContractControlModel {

    private String id;
    private String contractNo;  //合约编号
    private String userId;
    private String phone;   //电话
    private String name;    //姓名
    private String controlStr;  //操作
    private Integer controlNum;     //操作num
    private String createdTime;   //创建时间
    private String updateTime;//修改时间
    private String productStr;  //产品
    private Integer productNum; //产品num
    private double borrowMoney; //借款
    private double promisedMoney;   //保证金
    private double gainMoney;   //收益
    private double endMoney;    //结算
    private double warnningLine;    //预警线
    private double exWarnningLine;//修改后的预警线
    private double abortLine;   //平仓线
    private double exAbortLine;//修改后的平仓线
    private double borrowRate;  //借款利率
    private long borrowTime;    //借款时间
    private long expiredTime; //还款时间
    private long lessTime;//剩余天数
    private double firstMoney;//初次利息
    private double lessMoney;   //剩余金额 /可用金额
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public double getFirstInterest() {
        return firstInterest;
    }

    public void setFirstInterest(double firstInterest) {
        this.firstInterest = firstInterest;
    }

    public double getExWarnningLine() {
        return exWarnningLine;
    }

    public void setExWarnningLine(double exWarnningLine) {
        this.exWarnningLine = exWarnningLine;
    }

    public double getExAbortLine() {
        return exAbortLine;
    }

    public void setExAbortLine(double exAbortLine) {
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

    public long getLessTime() {
        return lessTime;
    }

    public void setLessTime(long lessTime) {
        this.lessTime = lessTime;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
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

    public double getFirstMoney() {
        return firstMoney;
    }

    public void setFirstMoney(double firstMoney) {
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
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

    public double getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(double borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public double getPromisedMoney() {
        return promisedMoney;
    }

    public void setPromisedMoney(double promisedMoney) {
        this.promisedMoney = promisedMoney;
    }

    public double getGainMoney() {
        return gainMoney;
    }

    public void setGainMoney(double gainMoney) {
        this.gainMoney = gainMoney;
    }

    public double getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(double endMoney) {
        this.endMoney = endMoney;
    }

    public double getWarnningLine() {
        return warnningLine;
    }

    public void setWarnningLine(double warnningLine) {
        this.warnningLine = warnningLine;
    }

    public double getAbortLine() {
        return abortLine;
    }

    public void setAbortLine(double abortLine) {
        this.abortLine = abortLine;
    }

    public double getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(double borrowRate) {
        this.borrowRate = borrowRate;
    }


    public long getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(long borrowTime) {

        this.borrowTime = borrowTime;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public double getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(double lessMoney) {
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
