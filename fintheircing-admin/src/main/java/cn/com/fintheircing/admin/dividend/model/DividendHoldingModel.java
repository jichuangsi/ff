package cn.com.fintheircing.admin.dividend.model;

import cn.com.fintheircing.admin.business.model.StockHoldingModel;

public class DividendHoldingModel extends StockHoldingModel {

    private String userName;
    private String phone;
    private String contractNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}
