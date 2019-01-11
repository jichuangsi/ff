package cn.com.fintheircing.admin.proxy.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProxySpread extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;
    private String salemanId;  //销售人员
    private String inviteCode;  //邀请码
    private String spreadLink;  //邀请链接
    private byte[] spreadCodePic;  //二维码邀请

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalemanId() {
        return salemanId;
    }

    public void setSalemanId(String salemanId) {
        this.salemanId = salemanId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getSpreadLink() {
        return spreadLink;
    }

    public void setSpreadLink(String spreadLink) {
        this.spreadLink = spreadLink;
    }

    public byte[] getSpreadCodePic() {
        return spreadCodePic;
    }

    public void setSpreadCodePic(byte[] spreadCodePic) {
        this.spreadCodePic = spreadCodePic;
    }
}
