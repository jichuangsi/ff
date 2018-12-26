package cn.com.fintheircing.admin.system.model.holiday;

import javax.validation.constraints.Pattern;

public class HolidayModel {

    private String id;
    @Pattern(regexp = "^(20[0-9]{2}((0[1-9])|(1[0-2]))((0[1-9])|([12][0-9])|(30)|(31)))$",message = "日期输入错误")
    private String start;
    @Pattern(regexp = "^(20[0-9]{2}((0[1-9])|(1[0-2]))((0[1-9])|([12][0-9])|(30)|(31)))$",message = "日期输入错误")
    private String end;
    private String remarks;
    private String status;

    private long createdTime;
    private long updateTime;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
