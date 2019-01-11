package cn.com.fintheircing.admin.usermanag.model.pay;

import java.util.Date;

public class PayResultModel {
    /**
     * 接口调用结果0-不成功；1-成功
     */
    String result;
    /**
     * failReason	失败原因
     */
    String failReason;
    /**
     * 支付单号
     */
    String orderId;
    /**
     * 付款状态，0-未付款；1-已付款
     */
    String payStatus;
    /**
     * 交易金额
     */
    String transAmount;
    /**
     * 支付完成时间
     */
    String transTime;

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
