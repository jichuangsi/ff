package cn.com.fintheircing.admin.usermanag.model.pay;
/**
 * AppQueryModel class
 *
 * @author yaoxiong
 * @date 2019/1/7
 */
public class AppQueryModel {
    private String orderId;
    private String tradeId;
    /**
     * 非必填，微信端必填
     */
    private String openId;
    private String goodName;
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
    private String appType;
    /**
     * 非必填，微信端必填
     */
    private String appId;
    /**
     * 进行MD5加密 加密参数
     */
    private String encryptionParams;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
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

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
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
