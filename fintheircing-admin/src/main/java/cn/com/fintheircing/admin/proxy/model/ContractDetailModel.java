package cn.com.fintheircing.admin.proxy.model;

import java.util.Date;

public class ContractDetailModel {

    private String contractId;
    private String userId;
    private Date createdTime;
    private Date endTime;
    private String shareId;
    private String shareName;
    private String businessTo;
    private Double finishCount;
    private Double finishAccount;
    private Double commission;
    private Double cost;
    private Double gain;
    private String proxyNum;
    private String proxyName;
    private Double stampDuty;
    private Double transferFee;
    private Double five;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getBusinessTo() {
        return businessTo;
    }

    public void setBusinessTo(String businessTo) {
        this.businessTo = businessTo;
    }

    public Double getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Double finishCount) {
        this.finishCount = finishCount;
    }

    public Double getFinishAccount() {
        return finishAccount;
    }

    public void setFinishAccount(Double finishAccount) {
        this.finishAccount = finishAccount;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public String getProxyNum() {
        return proxyNum;
    }

    public void setProxyNum(String proxyNum) {
        this.proxyNum = proxyNum;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public Double getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(Double stampDuty) {
        this.stampDuty = stampDuty;
    }

    public Double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }

    public Double getFive() {
        return five;
    }

    public void setFive(Double five) {
        this.five = five;
    }
}
