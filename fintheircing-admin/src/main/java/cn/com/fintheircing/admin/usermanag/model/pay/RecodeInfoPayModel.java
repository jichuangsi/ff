package cn.com.fintheircing.admin.usermanag.model.pay;

import java.util.Date;

/**
 * RecodeInfoPay 的model
 *
 * @author yaoxiong
 * @date 2019/1/14
 */
public class RecodeInfoPayModel {
    private String RecodeInfoPayId;//个人记录Id;
    private String userId;
    /**
     * 金额
     */
    private double addCount;
    private double costCount;
    private String remark;
    private String way;
    private Date creatTime;
    private Date updateTime;
    private String status;
    private String phone;
    private String userName;
    private String operator;
    private String operatorId;
    private String taskType;
    private String taskId;
    private String businessContractId;
    private double accountAmount;//账户余额
    private double iAccountAmount;//更改后账户余额

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public double getiAccountAmount() {
        return iAccountAmount;
    }

    public void setiAccountAmount(double iAccountAmount) {
        this.iAccountAmount = iAccountAmount;
    }

    public String getBusinessContractId() {
        return businessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        this.businessContractId = businessContractId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRecodeInfoPayId() {
        return RecodeInfoPayId;
    }

    public void setRecodeInfoPayId(String recodeInfoPayId) {
        RecodeInfoPayId = recodeInfoPayId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAddCount() {
        return addCount;
    }

    public void setAddCount(double addCount) {
        this.addCount = addCount;
    }

    public double getCostCount() {
        return costCount;
    }

    public void setCostCount(double costCount) {
        this.costCount = costCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
