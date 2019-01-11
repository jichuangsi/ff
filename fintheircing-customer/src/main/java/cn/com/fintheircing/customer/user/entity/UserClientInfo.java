package cn.com.fintheircing.customer.user.entity;

import cn.com.fintheircing.customer.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserClientInfo extends AbstractEntity {

	public static final String STATUS_INIT = "0";
	public static final String STATUS_ACTIVE = "1";
	public static final String STATUS_STOP = "2";

	public static final String CER_NOT = "0";
	public static final String CER_PASS = "1";


	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String uuid;
	/**
	 * 用户名，使用手机号作为用户名
	 */
	private String userName;
	private String phone;
	private String status;
	private String cer;
	/**
	 * 未实名时使用手机号，实名后使用实名姓名
	 */
	private String displayname;
	private Integer roleGrade;  //固定字段，用户

	private String inviterId;

	public String getInviterId() {
		return inviterId;
	}

	public void setInviterId(String inviterId) {
		this.inviterId = inviterId;
	}

	public Integer getRoleGrade() {
		return roleGrade;
	}

	public void setRoleGrade(Integer roleGrade) {
		this.roleGrade = roleGrade;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

}