package cn.com.fintheircing.customer.user.model.payresultmodel;

import cn.com.fintheircing.customer.common.constant.PayStatus;

import java.util.Date;

/**
 * RecodeInfoPay 的model
 *
 * @author yaoxiong
 * @date 2019/1/14
 */
public class RecodeInfoPayModel {
    private String uuid;
    private String userId;
    /**
     * 金额
     */
    private double addCount;
    private double costCount;
    private String remark;
    private String way;
    private Date createTime;
    private Date updateTime;
    private String status;
    private String phone;
    private String userName;
    private String businessContractId;

    public String getBusinessContractId() {
        return businessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        this.businessContractId = businessContractId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
