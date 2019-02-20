package cn.com.fintheircing.admin.risk.model;

public class RiskControlModel {

    public static String WARNNING_SAFE = "0";
    public static String WARNNING_DANGOUR = "1";

    public static String HOLDING_HAD = "0";
    public static String HOLDING_NULL_HAD ="1";

    private String userName;
    private String contractNo;
    private String name;
    private String userPhone;
    private Integer warnningStatus;
    private Integer havingStock;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getWarnningStatus() {
        return warnningStatus;
    }

    public void setWarnningStatus(Integer warnningStatus) {
        this.warnningStatus = warnningStatus;
    }

    public Integer getHavingStock() {
        return havingStock;
    }

    public void setHavingStock(Integer havingStock) {
        this.havingStock = havingStock;
    }
}
