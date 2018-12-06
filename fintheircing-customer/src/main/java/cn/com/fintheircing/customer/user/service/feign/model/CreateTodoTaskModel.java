package cn.com.fintheircing.customer.user.service.feign.model;

public class CreateTodoTaskModel {

	public static final String TASK_TYPE_REG = "REGISTER_EXAM";

	private String taskType;
	private String registerUserId;
	private String phoneNo;

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

}
