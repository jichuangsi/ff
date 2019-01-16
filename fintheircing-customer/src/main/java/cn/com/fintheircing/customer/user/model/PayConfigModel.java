package cn.com.fintheircing.customer.user.model;
/**
 * 生成第三方支付配置信息
 *
 * @author yaoxiong
 * @date 2016/1/14
 */
public class PayConfigModel {
    private String Way;
    private String userId;
    private String phone;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getWay() {
        return Way;
    }

    public void setWay(String way) {
        Way = way;
    }
}
