package cn.com.fintheircing.customer.user.entity;

import cn.com.fintheircing.customer.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BlackList extends AbstractEntity{

    public final static String STATUS_EXIST = "0";
    public final static String STATUS_NOTEXIST = "1";

    public final static String SERIOUS_SLIGHT = "0";
    public final static String SERIOUS_NORMAL = "1";
    public final static String SERIOUS_BADLY = "2";

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String ipAddress;
    private String cause;
    private String status;
    private String serious;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerious() {
        return serious;
    }

    public void setSerious(String serious) {
        this.serious = serious;
    }
}
