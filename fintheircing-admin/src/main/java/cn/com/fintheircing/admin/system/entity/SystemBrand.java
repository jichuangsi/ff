package cn.com.fintheircing.admin.system.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SystemBrand extends AbstractEntity{

    public static final String APPLYON_PC = "0";
    public static final String APPLYON_APP = "1";

/*    public static final String STATUS_ACTIVE = "0";
    public static final String STATUS_DISABLED = "1";*/

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String brandName;
    private byte[] content;
    private String applyOn;
/*    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }


    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getApplyOn() {
        return applyOn;
    }

    public void setApplyOn(String applyOn) {
        this.applyOn = applyOn;
    }
}
