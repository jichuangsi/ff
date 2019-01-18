package cn.com.fintheircing.admin.usermanag.model;

import cn.com.fintheircing.admin.common.constant.MesStatus;


import java.util.Date;

public class MesModel {
    private String userId;
    private String phone;
    private String userName;
    private Long sendCount;
    private String isSucess;
    private String taskType;
    private String content;
    private Date createTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsSucess() {
        if ("0".equals(isSucess)){
        return MesStatus.getName(0);
        }else {
            return MesStatus.getName(1);
        }
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public Long getSendCount() {
        return sendCount;
    }

    public void setSendCount(Long sendCount) {
        this.sendCount = sendCount;
    }



    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
