package cn.com.fintheircing.admin.business.model.record;

import java.util.Date;

public class ContactApplyModel {
    private String contactRecodeId;//合约操作记录Id
    private String submitterId;//提交的人
    private String submitterName;//提交的人名字
    private String contactId;//
    private String userId;
    private String userName;
    private String phone;
    private String applyType;//申请类型
    private Date createTime=new Date();
    private String remark;
    private Integer checkStatus=0;//0为未审核,1为通过,2为未通过
    private double warnLine;//修改后的
    private double exWarnLine;//原来的
    private double abortLine;//修改后的平仓线
    private double exAbortLine;//原来的平仓线
    private double userFunds;//账户资金余额
    private double promiseMoney;//保证金
    private String goodsType;//产品类型
    private String businessControlContractId;

    public String getContactRecodeId() {
        return contactRecodeId;
    }

    public void setContactRecodeId(String contactRecodeId) {
        this.contactRecodeId = contactRecodeId;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public double getWarnLine() {
        return warnLine;
    }

    public void setWarnLine(double warnLine) {
        this.warnLine = warnLine;
    }

    public double getExWarnLine() {
        return exWarnLine;
    }

    public void setExWarnLine(double exWarnLine) {
        this.exWarnLine = exWarnLine;
    }

    public double getAbortLine() {
        return abortLine;
    }

    public void setAbortLine(double abortLine) {
        this.abortLine = abortLine;
    }

    public double getExAbortLine() {
        return exAbortLine;
    }

    public void setExAbortLine(double exAbortLine) {
        this.exAbortLine = exAbortLine;
    }

    public double getUserFunds() {
        return userFunds;
    }

    public void setUserFunds(double userFunds) {
        this.userFunds = userFunds;
    }

    public double getPromiseMoney() {
        return promiseMoney;
    }

    public void setPromiseMoney(double promiseMoney) {
        this.promiseMoney = promiseMoney;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getBusinessControlContractId() {
        return businessControlContractId;
    }

    public void setBusinessControlContractId(String businessControlContractId) {
        this.businessControlContractId = businessControlContractId;
    }
}
