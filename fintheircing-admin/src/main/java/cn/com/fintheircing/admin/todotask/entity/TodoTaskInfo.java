package cn.com.fintheircing.admin.todotask.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class TodoTaskInfo {

	public static final String STATUS_ING = "0";
	public static final String STATUS_FIN = "1";
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String uuid;
	private String taskType;
	private String status;
	private String examerId;
	private String processorName;
	/**
	 * 各种类型任务对应不同信息表
	 */
	private String taskInfoId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExamerId() {
		return examerId;
	}

	public void setExamerId(String examerId) {
		this.examerId = examerId;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public String getTaskInfoId() {
		return taskInfoId;
	}

	public void setTaskInfoId(String taskInfoId) {
		this.taskInfoId = taskInfoId;
	}

}
