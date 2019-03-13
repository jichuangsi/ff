package cn.com.fintheircing.admin.usermanag.model;

import cn.com.fintheircing.admin.business.model.StockHoldingModel;

public class UserStockHoldingModel extends StockHoldingModel {

    private String contractNo;

    private String phone;
    private String userName;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
