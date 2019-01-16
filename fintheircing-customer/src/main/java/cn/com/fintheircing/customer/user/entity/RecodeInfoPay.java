package cn.com.fintheircing.customer.user.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 生成待确认充值记录 class
 *
 * @author yaoxiong
 * @date 2019/1/14
 */
@Entity
public class RecodeInfoPay {
    @Id
    private String uuid;
    private String userId;
    private String addCount;
    private String costCount;
    private String remark;
    private Integer status=0;
    private String way;
    private Date createTime =new Date();
    /**
     * 用户自己看是充值完成时间,管理员看是操作时间
     */
    private Date updateTime;
    private String operator;
    private String operatorId;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
