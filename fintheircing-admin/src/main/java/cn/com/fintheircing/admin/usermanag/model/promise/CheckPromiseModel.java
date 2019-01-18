package cn.com.fintheircing.admin.usermanag.model.promise;

import java.util.Date;

/**
 * 给操作员看的保证金申请model
 *
 * @author yaoxiong
 * @date 2019/1/16
 */
public class CheckPromiseModel {
    private String userId;
    private String userName;
    private String phone;
    /**
     * 合约编号
     */
    private String contractNum;

    /**
     * 追加保证金金额
     */
    private double promiseCash;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     *备注
     */
    private String remark;
    /**
     * 申请类型
     */
    private String taskType;

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

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public double getPromiseCash() {
        return promiseCash;
    }

    public void setPromiseCash(double promiseCash) {
        this.promiseCash = promiseCash;
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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
