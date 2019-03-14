package cn.com.fintheircing.admin.usermanag.model.pay;

/**
 * 充值model class
 *
 * @author yaoxiong
 * @date 2019/1/14
 */
public class PayInfoModel {
    private double amount;
    /**
     * 充值方式　微信/支付宝
     */
    private String payWay;
    private String method;
    private String netQuery;
    private String userName;
    private String userId;
    private String remark;
    private String payStatus;//支付状态



    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNetQuery() {
        return netQuery;
    }

    public void setNetQuery(String netQuery) {
        this.netQuery = netQuery;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
}
