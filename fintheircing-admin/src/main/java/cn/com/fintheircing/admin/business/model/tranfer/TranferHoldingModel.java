package cn.com.fintheircing.admin.business.model.tranfer;

import cn.com.fintheircing.admin.business.model.StockHoldingModel;

public class TranferHoldingModel extends StockHoldingModel {

    private String contractNum;     //合约代码
    private String userName;    //用户名
    private String userPhone;   //用户电话
    private String name;    //姓名
    private Integer choseWay;   //产品num
    private String productName;     //产品str

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

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChoseWay() {
        return choseWay;
    }

    public void setChoseWay(Integer choseWay) {
        this.choseWay = choseWay;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
