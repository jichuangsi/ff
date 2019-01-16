package cn.com.fintheircing.admin.usermanag.model.pay;

import java.util.Date;

/**
 * PayInfo的Model
 *
 * @author yaoxiong
 * @date 2016/1/14
 */
public class PayInfoModel {
    private String userId;
    private String phone;
    private String userName;
    private String addCount;
    private String costCount;
    private String remark;
    private String status;
    private String way;
    private Date createTime;
    private Date updateTime=new Date();
    private String checkStatus="0";

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddCount() {
        return addCount;
    }

    public void setAddCount(String addCount) {
        this.addCount = addCount;
    }

    public String getCostCount() {
        return costCount;
    }

    public void setCostCount(String costCount) {
        this.costCount = costCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
}
