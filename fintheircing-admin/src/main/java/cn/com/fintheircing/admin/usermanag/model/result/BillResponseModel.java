package cn.com.fintheircing.admin.usermanag.model.result;

import java.util.Date;

/**
 * 支付结果查询-->返回结果参数 class
 *
 * @author yaoxiong
 * @date 2019/1/7
 */
public class BillResponseModel {
    private String result;
    private String failReason;
    private String orderId;
    private String payStatus;
    private String transAmount;
    private String transTime;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }
}
