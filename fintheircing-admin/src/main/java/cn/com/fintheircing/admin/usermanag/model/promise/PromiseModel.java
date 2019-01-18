package cn.com.fintheircing.admin.usermanag.model.promise;

import java.util.Date;

/**
 * 保证金
 *
 * @author yaoxiong
 * @date 2019/1/15
 */
public class PromiseModel {
    private String recodeInfoPayId;
    private String userId;
    private String businessContractId;
    private double cash;
    private Date createTime;
    private String remark;
    private String way;
    private String contractNum;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecodeInfoPayId() {
        return recodeInfoPayId;
    }

    public void setRecodeInfoPayId(String recodeInfoPayId) {
        this.recodeInfoPayId = recodeInfoPayId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessContractId() {
        return businessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        this.businessContractId = businessContractId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }
}
