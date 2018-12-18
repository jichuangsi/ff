package cn.com.fintheircing.admin.proxy.model;

import java.util.List;

public class ProxyModel {
    private String proxyId;
    private String proxyNum;
    private String proxyName;
    private String proxyPosition;
    private String linkMan;
    private String linkPhone;
    private String status;
    private String remarks;
    private List<ProxyModel> proxyModels;
    private Double dayCommission;
    private Double monthCommission;
    private Double backCommission;

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

    private Integer pageIndex;
    private Integer pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public String getProxyPosition() {
        return proxyPosition;
    }

    public void setProxyPosition(String proxyPosition) {
        this.proxyPosition = proxyPosition;
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
