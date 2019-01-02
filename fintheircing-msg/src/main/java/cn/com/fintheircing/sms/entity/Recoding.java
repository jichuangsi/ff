package cn.com.fintheircing.sms.entity;


import cn.com.fintheircing.customer.common.entity.AbstractEntity;
import cn.com.fintheircing.sms.Commons.Status;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
@Entity
public class Recoding extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String phone;
    private Status isSucess;//是否成功
    private String taskType;//任务类型
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


    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Recoding() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
