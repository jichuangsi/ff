package cn.com.fintheircing.admin.usermanag.model;

import cn.com.fintheircing.admin.common.model.AbstractEntityModel;

import java.util.Date;


public class AdminClientInfoModel extends AbstractEntityModel {
    Date createTime;//注册时间
    Date endtime;
    String userId;
    String phoneNo;
    String userName;
    String cer;
    String source;
    String proxyId;
    String proxyName;
    String balanceMoney;
    String belongs;
    String emplooyeeId;

    public String getEmplooyeeId() {
        return emplooyeeId;
    }

    public void setEmplooyeeId(String emplooyeeId) {
        this.emplooyeeId = emplooyeeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBelongs() {
        return belongs;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }


    public String getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(String balanceMoney) {
        this.balanceMoney = balanceMoney;
    }
}
