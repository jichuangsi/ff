package cn.com.fintheircing.admin.common.entity.Contact;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "contactDetails")
public class contactDetails {
    @Id
    @GeneratedValue
    private String id;
    private String borrowRate;//借款利率
    private String borrowDays;//借款天数
    private String remainderDays;//剩余天数
    private String firstRate;//初次利息
    private String dangerLevel;//风控级别
    private String transactionCommission;//交易佣金
    private String delegationOrigin;//委托来源
    private String ContactAssets;//合约净资产
    private String taskStatus;//审核状态
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "contactDetails")
    private contactInfo contactInfo;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(String borrowRate) {
        this.borrowRate = borrowRate;
    }

    public String getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(String borrowDays) {
        this.borrowDays = borrowDays;
    }

    public String getRemainderDays() {
        return remainderDays;
    }

    public void setRemainderDays(String remainderDays) {
        this.remainderDays = remainderDays;
    }

    public String getFirstRate() {
        return firstRate;
    }

    public void setFirstRate(String firstRate) {
        this.firstRate = firstRate;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public String getTransactionCommission() {
        return transactionCommission;
    }

    public void setTransactionCommission(String transactionCommission) {
        this.transactionCommission = transactionCommission;
    }

    public String getDelegationOrigin() {
        return delegationOrigin;
    }

    public void setDelegationOrigin(String delegationOrigin) {
        this.delegationOrigin = delegationOrigin;
    }

    public String getContactAssets() {
        return ContactAssets;
    }

    public void setContactAssets(String contactAssets) {
        ContactAssets = contactAssets;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
