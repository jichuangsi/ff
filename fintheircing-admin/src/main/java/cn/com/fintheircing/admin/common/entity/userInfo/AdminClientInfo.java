package cn.com.fintheircing.admin.common.entity.userInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AdminClientInfo")
public class AdminClientInfo  {
    public final static String STATUS_EXIST = "0";
    public final static String STATUS_NOTEXIST = "1";
    @Id
    String uuid;
    String userId;
    @Column(name = "position")
    private String position;//职位 现在不显示在页面不用管
    @Column(name = "status")
    private String status;//状态
    @Column(name = "proxyNum")//代理商编号
    private String proxyNum;
    @Column(name = "userName")
    private String proxyName;//代理商名称
    @Column(name = "remarks")
    private String remarks;  //备注
    @Column(name = "bossId")
    private String bossId;  //

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUuid() {
            return uuid;
        }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getProxyNum() {
        return proxyNum;
    }

    public void setProxyNum(String proxyNum) {
        this.proxyNum = proxyNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }
}
