package cn.com.fintheircing.admin.common.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

	protected Date createdTime = new Date();
	protected String creatorId;
	protected String creatorName;
	protected Date updatedTime = new Date();
	protected String updateUserId;
	protected String updateUserName;
	protected String deleteFlag = "0";

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