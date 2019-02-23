package cn.com.fintheircing.admin.system.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SystemBlackList extends AbstractEntity {

/*    public final static String STATUS_EXIST = "0";
    public final static String STATUS_NOTEXIST = "1";

    public final static String SERIOUS_SLIGHT = "0";
    public final static String SERIOUS_NORMAL = "1";
    public final static String SERIOUS_BADLY = "2";*/

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String ipAddress;
    private String cause;
/*    private String status;
    private String serious;*/

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


}
