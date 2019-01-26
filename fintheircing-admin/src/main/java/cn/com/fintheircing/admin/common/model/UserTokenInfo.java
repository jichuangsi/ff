package cn.com.fintheircing.admin.common.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserTokenInfo {
    @NotBlank(message = "登录名不能为空")
    private String loginName;
    @NotBlank(message = "登录密码不能为空")
    @Pattern(regexp = "^((?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16})|111111$", message = "密码必须为8-16位数字与字母混合")
    private String pwd;

    private String uuid;
    private String phone;
    private String status;
    private String userName;
    private String displayname;
    private Integer roleGrade; //区别管理和用户
    private String roleName;
    private String applyOn;     //区别pc和app


    public String getApplyOn() {
        return applyOn;
    }

    public void setApplyOn(String applyOn) {
        this.applyOn = applyOn;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Integer getRoleGrade() {
        return roleGrade;
    }

    public void setRoleGrade(Integer roleGrade) {
        this.roleGrade = roleGrade;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
