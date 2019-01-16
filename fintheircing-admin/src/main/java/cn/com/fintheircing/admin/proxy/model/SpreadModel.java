package cn.com.fintheircing.admin.proxy.model;

public class SpreadModel {

    private String id;
    private String proxyNum;
    private String proxyName;
    private String empNum;
    private String empName;
    private String userName;
    private String userNum;
    private String spreadNum;
    private String spreadLink;
    private byte[] qRcode;
    private Integer position;
    private Integer pageIndex;
    private Integer pageSize;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getSpreadNum() {
        return spreadNum;
    }

    public void setSpreadNum(String spreadNum) {
        this.spreadNum = spreadNum;
    }

    public String getSpreadLink() {
        return spreadLink;
    }

    public void setSpreadLink(String spreadLink) {
        this.spreadLink = spreadLink;
    }

    public byte[] getqRcode() {
        return qRcode;
    }

    public void setqRcode(byte[] qRcode) {
        this.qRcode = qRcode;
    }
}
