package cn.com.fintheircing.sms.entity;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {
	protected String id;
	protected String userId;
	protected String userName;
	protected String phone;
	protected Date createdTime = new Date();
	protected String creatorId;
	protected String creatorName;
	protected Date updatedTime = new Date();
	protected String updateUserId;
	protected String updateUserName;
	protected String deleteFlag = "0";

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getUserId() {
		return userId;
	}

	public final void setUserId(String userId) {
		this.userId = userId;
	}

	public final String getUserName() {
		return userName;
	}

	public final void setUserName(String userName) {
		this.userName = userName;
	}

	public final String getPhone() {
		return phone;
	}

	public final void setPhone(String phone) {
		this.phone = phone;
	}

	public final Date getCreatedTime() {
		return createdTime;
	}

	public final void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public final String getCreatorId() {
		return creatorId;
	}

	public final void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public final String getCreatorName() {
		return creatorName;
	}

	public final void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public final Date getUpdatedTime() {
		return updatedTime;
	}

	public final void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public final String getUpdateUserId() {
		return updateUserId;
	}

	public final void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public final String getUpdateUserName() {
		return updateUserName;
	}

	public final void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public final String getDeleteFlag() {
		return deleteFlag;
	}

	public final void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}