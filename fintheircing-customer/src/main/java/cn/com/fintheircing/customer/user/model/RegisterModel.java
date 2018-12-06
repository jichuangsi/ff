package cn.com.fintheircing.customer.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterModel {
	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp="^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",message = "手机号不正确")
	private String phoneNo;
	@NotBlank(message = "密码不能为空")
    @Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$",message = "密码必须为8-16位数字与字母混合")
	private String pwd;
    @NotBlank(message = "验证码不能为空")
	private String valCode;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getValCode() {
		return valCode;
	}

	public void setValCode(String valCode) {
		this.valCode = valCode;
	}

}
