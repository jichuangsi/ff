package cn.com.fintheircing.customer.user.model.payresultmodel;

import java.util.Date;

/**
 * 充值model class
 *
 * @author yaoxiong
 * @date 2019/1/14
 */
public class PayInfoModel {
    private String amount;
    /**
     * 充值方式
     */
    private String Way;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWay() {
        return Way;
    }

    public void setWay(String way) {
        Way = way;
    }
}
