package cn.com.fintheircing.admin.usermanag.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class OnlineUserInfo {
    private String RecodeInfoId;
    String userId;
    String userName;
    String ipAdress;
    Date loginTime;
    Date lastTime;
    @Value("${custom.token.longTime}")
    String ExpiredTime;//过期时间
    String status;
    String operating;

    public String getRecodeInfoId() {
        return RecodeInfoId;
    }

    public void setRecodeInfoId(String recodeInfoId) {
        RecodeInfoId = recodeInfoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperating() {
        return operating;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastTime() {

        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
