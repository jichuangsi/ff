package cn.com.fintheircing.admin.system.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SystemPhoto extends AbstractEntity{

    public static String APPLY_ON_BANK = "0";   //付款二维码
    public static String APPLE_ON_DOWN = "1";   //下载二维码
    public static String APPLY_ON_LOGO = "2";   //logo

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String apply;
    private String name;
    private String stayOn;
    @Column(columnDefinition = "longblob")
    private byte[] content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStayOn() {
        return stayOn;
    }

    public void setStayOn(String stayOn) {
        this.stayOn = stayOn;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
