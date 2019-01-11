package cn.com.fintheircing.sms.model;

import cn.com.fintheircing.sms.Commons.Status;
import cn.com.fintheircing.sms.entity.AbstractEntity;

public class mesModel extends AbstractEntity {
    private String uuid;
    private Long sendCount;
    private Status isSucess;
    private String taskType;
    private String content;//短信内容

    public Status getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(Status isSucess) {
        this.isSucess = isSucess;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
