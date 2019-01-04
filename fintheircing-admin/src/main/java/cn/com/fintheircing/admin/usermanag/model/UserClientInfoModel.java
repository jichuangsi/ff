package cn.com.fintheircing.admin.usermanag.model;

import cn.com.fintheircing.admin.common.model.AbstractEntityModel;

public class UserClientInfoModel extends AbstractEntityModel {
    private String status;
    private String cer;//是否实名认证
    private String displayname;
    private String Source;//来源

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }
}
