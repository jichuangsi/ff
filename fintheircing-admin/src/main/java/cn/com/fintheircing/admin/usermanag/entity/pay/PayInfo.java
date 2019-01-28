package cn.com.fintheircing.admin.usermanag.entity.pay;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class PayInfo {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String userId;
    private double addCount;
    private double costCount;
    private String remark;
    private String way;//是把钱用去哪里了
    private Date createTime=new Date();
    private Date updateTime;
    private String checkStatus;
    private String businessContractId;
    private String operaId;
    private String operaName;
    private Integer taskType;

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getOperaId() {
        return operaId;
    }

    public void setOperaId(String operaId) {
        this.operaId = operaId;
    }

    public String getOperaName() {
        return operaName;
    }

    public void setOperaName(String operaName) {
        this.operaName = operaName;
    }

    public String getBusinessContractId() {
        return businessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        this.businessContractId = businessContractId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
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

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
}
