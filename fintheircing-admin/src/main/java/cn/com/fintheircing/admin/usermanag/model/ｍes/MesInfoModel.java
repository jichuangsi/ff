package cn.com.fintheircing.admin.usermanag.model.ｍes;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;

import java.util.Date;

public class MesInfoModel {
    private String mesId;
    private String title;
    private String content;
    private Date sendTime;
    private String status;


    public String getMesId() {
        return mesId;
    }

    public void setMesId(String mesId) {
        this.mesId = mesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
