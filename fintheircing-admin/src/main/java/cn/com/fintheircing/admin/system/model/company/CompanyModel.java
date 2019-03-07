package cn.com.fintheircing.admin.system.model.company;

import javax.validation.constraints.Pattern;
import java.util.Date;

public class CompanyModel {

    private String id;
    private String name;    //站点名
    private String apply;   //位置
    private String companyName; //公司姓名
    private String hotLine; //热像
    @Pattern(regexp = "^$|^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$",message = "邮箱格式不正确")
    private String email;   //邮箱
    @Pattern(regexp = "^[1-9]\\d{4,11}$",message = "qq格式不正确")
    private String qqAccount;   //qq
    @Pattern(regexp = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$",message = "微信格式不正确")
    private String wechatAccount;   //微信
    private String serverTime;  //服务时间
    private String copyright;   //公司版权
    private String icpNo;   //备案号
    private Date modifyTime;    //修改时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHotLine() {
        return hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getIcpNo() {
        return icpNo;
    }

    public void setIcpNo(String icpNo) {
        this.icpNo = icpNo;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
