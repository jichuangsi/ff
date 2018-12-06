package cn.com.fintheircing.customer.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import cn.com.fintheircing.customer.common.entity.AbstractEntity;

@Entity
public class UserClientLoginInfo extends AbstractEntity{

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String uuid;
	private String loginName;
	private String pwd;
	private String clientInfoId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getClientInfoId() {
		return clientInfoId;
	}

	public void setClientInfoId(String clientInfoId) {
		this.clientInfoId = clientInfoId;
	}

}