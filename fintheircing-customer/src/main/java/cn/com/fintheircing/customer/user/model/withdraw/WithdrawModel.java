package cn.com.fintheircing.customer.user.model.withdraw;
/**
 * 提现
 *
 * @author yaoxiong
 * @date 2019/1/22
 */
public class WithdrawModel {
    private double amount;//提现金额
    private String aim;//提现地方--可以是银行卡,支付宝,微信
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

}
