package cn.com.fintheircing.admin.usermanag.model.pay;

public class PayResponseModel<T> {
    private String result;
    private String failReason;
    private String orderId;
    private T payInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(T payInfo) {
        this.payInfo = payInfo;
    }

}