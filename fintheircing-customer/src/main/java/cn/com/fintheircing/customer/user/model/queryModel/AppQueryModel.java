package cn.com.fintheircing.customer.user.model.queryModel;
/**
 * AppQueryModel class
 *
 * @author yaoxiong
 * @date 2019/1/7
 */
public class AppQueryModel {
    private String orderId;
    private String orderName;
    private String tradeId;

    private String amount;
    /**
     * 支付方名称
     */
    private String payerName;
    private String payerNo;
    /**
     * 非必填
     */
    private String noticeUrl;
    /**
     *必填，WXPAY|ALIPAY
     * */
    private String payType;
    private String appId;
    /**
     * 进行MD5加密 加密参数
     */
    private String encryptionParams;
    private String remark;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerNo() {
        return payerNo;
    }

    public void setPayerNo(String payerNo) {
        this.payerNo = payerNo;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEncryptionParams() {
        return encryptionParams;
    }

    public void setEncryptionParams(String encryptionParams) {
        this.encryptionParams = encryptionParams;
    }
}
