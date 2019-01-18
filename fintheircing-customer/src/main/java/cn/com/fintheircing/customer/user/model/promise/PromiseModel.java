package cn.com.fintheircing.customer.user.model.promise;

import java.util.Date;

/**
 * 保证金
 *
 * @author yaoxiong
 * @date 2019/1/15
 */
public class PromiseModel {
    private String userId;
    private String BusinessContractId;
    private Date createTime;
    private double cash;
    private int payStatus=0;

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
        return BusinessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        BusinessContractId = businessContractId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }
}
