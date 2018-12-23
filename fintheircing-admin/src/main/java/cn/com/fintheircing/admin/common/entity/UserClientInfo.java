package cn.com.fintheircing.admin.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserClientInfo")
public class UserClientInfo extends AbstractEntity {

	public static final String STATUS_INIT = "0";
	public static final String STATUS_ACTIVE = "1";
	public static final String STATUS_STOP = "2";

	public static final String CER_NOT = "0";
	public static final String CER_PASS = "1";

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	String id;
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	String userId;
	private String status;
	private String cer;//是否实名认证
	/**
	 * 未实名时使用手机号，实名后使用实名姓名
	 */
	private String displayname;
	private String Source;//来源

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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