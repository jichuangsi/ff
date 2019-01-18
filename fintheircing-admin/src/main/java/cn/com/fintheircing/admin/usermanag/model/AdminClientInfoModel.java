package cn.com.fintheircing.admin.usermanag.model;

import cn.com.fintheircing.admin.common.model.AbstractEntityModel;

import java.util.Date;


public class AdminClientInfoModel  {
    Date createTime;//注册时间
    Date endtime;//结束时间
    String userId;//用户编号
    String phoneNo;//手机号码
    String userName;//用户名字
    String cer;//是否实名
    String source;//来源
    String proxyId;//代理Id
    String proxyName;//代理名称
    String balanceMoney;//资金
    String belongs;//所属 有或者无
    String emplooyeeId;//所属员工编号


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
    int pageNum;
    int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
