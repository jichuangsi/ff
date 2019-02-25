package cn.com.fintheircing.admin.usermanag.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;


public class AdminClientInfoModel  {
    @Pattern(regexp="yyyy-MM-dd HH:MM:SS")
    private String createTime;//注册时间
    @Pattern(regexp="yyyy-MM-dd HH:MM:SS")
    private String endtime;//结束时间
    private String userId;//用户编号
    private String phoneNo;//手机号码
    private String userName;//用户名字
    private String cer;//是否实名
    private String source;//来源
    private String proxyId;//代理Id
    private String proxyName;//代理名称
    private String balanceMoney;//余额
    private String belongs;//所属 有或者无
    private String emplooyeeId;//所属员工编号
    private String accountStatus;//账号状态
    private String idCard;//身份证号
    private String frezzeAmount;//冻结余额
    private String remark;
    private String bossId;

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFrezzeAmount() {
        return frezzeAmount;
    }

    public void setFrezzeAmount(String frezzeAmount) {
        this.frezzeAmount = frezzeAmount;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
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
