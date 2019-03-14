package cn.com.fintheircing.admin.business.model.record;

import java.util.Date;

public class ContractEquityModel {
    private String SubmitterName;
    private String SubmitterId;
    private String ContractId;
    private String UserCode;
    private String UserName;
    private String UserPhone;
    private String ApplyType;
    private String Remarks;
    private String productStr;
    private double warnLine;//修改后的
    private double exWarnLine;//原来的
    private double abortLine;//修改后的平仓线
    private double exAbortLine;//原来的平仓线
    private double promisedMoney;//保证金金额
    private Date createdTime;//提交时间
    private String contactRecodeId;//合约申请记录Id

    public String getContactRecodeId() {
        return contactRecodeId;
    }

    public void setContactRecodeId(String contactRecodeId) {
        this.contactRecodeId = contactRecodeId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getSubmitterName() {
        return SubmitterName;
    }

    public void setSubmitterName(String submitterName) {
        SubmitterName = submitterName;
    }

    public String getSubmitterId() {
        return SubmitterId;
    }

    public void setSubmitterId(String submitterId) {
        SubmitterId = submitterId;
    }

    public String getContractId() {
        return ContractId;
    }

    public void setContractId(String contractId) {
        ContractId = contractId;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getApplyType() {
        return ApplyType;
    }

    public void setApplyType(String applyType) {
        ApplyType = applyType;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getProductStr() {
        return productStr;
    }

    public void setProductStr(String productStr) {
        this.productStr = productStr;
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

    public double getPromisedMoney() {
        return promisedMoney;
    }

    public void setPromisedMoney(double promisedMoney) {
        this.promisedMoney = promisedMoney;
    }
}
