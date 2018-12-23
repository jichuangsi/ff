package cn.com.fintheircing.sms.mq.consumer.model;

public class SendSmsModel {
	
	public static final String VALCODE_TYPE = "valCode"; 

	private String phoneNo;
	private String type;
	private String content;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
