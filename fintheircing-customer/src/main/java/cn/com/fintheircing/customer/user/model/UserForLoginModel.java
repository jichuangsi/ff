package cn.com.fintheircing.customer.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserForLoginModel {

	@NotBlank(message = "登录名不能为空")
	private String loginName;
	@NotBlank(message = "登录密码不能为空")
	@Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8-16位数字与字母混合")
	private String pwd;

	private String displayname;
	private String phone;
	private String status;

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
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

}
