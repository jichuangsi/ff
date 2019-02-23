package cn.com.fintheircing.admin.proxy.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProxyModel {
    private String proxyId;
    private String proxyNum;    //代理编号
    @NotBlank(message = "用户名不可为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{4,16}$",message = "用户名应为4到16位（字母，数字，下划线，减号）")
    private String proxyName;   //代理名称
    private Integer roleGrade;  //角色定位，1为代理1,2为代理2
    private String linkMan;     //联系人
    private String linkPhone;   //联系人电话
    private String status;      //账户状态
    private String remarks;     //备注
    private List<ProxyModel> proxyModels = new ArrayList<ProxyModel>();     //子代理商
    private Double dayCommission;       //日收佣
    private Double monthCommission;     //月收佣
    private Double backCommission;      //返佣
    private Date createdTime;       //创建时间
    private Date updateTime;        //修改时间
    private String bossId;      //上级id

    private String beginTime;       //查询时间的接口

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getDayCommission() {
        return dayCommission;
    }

    public void setDayCommission(Double dayCommission) {
        this.dayCommission = dayCommission;
    }

    public Double getMonthCommission() {
        return monthCommission;
    }

    public void setMonthCommission(Double monthCommission) {
        this.monthCommission = monthCommission;
    }

    public Double getBackCommission() {
        return backCommission;
    }

    public void setBackCommission(Double backCommission) {
        this.backCommission = backCommission;
    }

    private Integer page;
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<ProxyModel> getProxyModels() {
        return proxyModels;
    }

    public void setProxyModels(List<ProxyModel> proxyModels) {
        this.proxyModels = proxyModels;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

    public String getProxyNum() {
        return proxyNum;
    }

    public void setProxyNum(String proxyNum) {
        this.proxyNum = proxyNum;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public Integer getRoleGrade() {
        return roleGrade;
    }

    public void setRoleGrade(Integer roleGrade) {
        this.roleGrade = roleGrade;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
