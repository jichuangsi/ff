package cn.com.fintheircing.customer.user.model.promise;
/**
 * 保证金
 *
 * @author yaoxiong
 * @date 2019/1/15
 */
public class PromiseModel {
    private String userId;
    private String userName;
    private String phone;
    private String BusinessContractId;
    private double cash;
    private double beforeCash;
    private double afterCash;
    private int payStatus=0;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getBusinessContractId() {
        return BusinessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        BusinessContractId = businessContractId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getBeforeCash() {
        return beforeCash;
    }

    public void setBeforeCash(double beforeCash) {
        this.beforeCash = beforeCash;
    }

    public double getAfterCash() {
        return afterCash;
    }

    public void setAfterCash(double afterCash) {
        this.afterCash = afterCash;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }
}
