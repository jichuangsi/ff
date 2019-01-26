package cn.com.fintheircing.admin.usermanag.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Demo 消息发送记录
 *
 * @author yaoxiong
 * @date 2019/1/24
 */
@Entity
@Table(name = "admin_MesInfo")
public class MesInfo {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String title;
    private String content;
    private Date sendTime;
    private Integer status=0;
    private Integer delete_Flag=0;

    public Integer getDelete_Flag() {
        return delete_Flag;
    }

    public void setDelete_Flag(Integer delete_Flag) {
        this.delete_Flag = delete_Flag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
