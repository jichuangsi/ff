package cn.com.fintheircing.customer.user.model.queryModel;
/**
 * NetQueryModel class
 *
 * @author yaoxiong
 * @date 2019/1/6
 */
public class NetQueryModel {
    /**
     * 支付单号
     */
    private String orderId;
    /**
     * orderName	支付单号名称
     */
    private String orderName;
    /**
     * 付款方编号
     */
    private String payerNo;
    private String payerName;
    /**
     * 金额
     */
    private String amount;
    /**
     * 商户号
     */
    private String tradeId;
    private String returnUrl;
    /**
     * 通知url
     */
    private String noticeUrl;
    /**
     * 加密参数
     */
    private String encryptionParams;
    private String remark;
    /**
     * 主题颜色
     */
    private String themeColor;
    private String backUrl;
    /**
     * 浏览器标识
     */
    private String browserUA;
//    /**
//     * 微信号
//     */
//    private String openId;
//    /**
//     * 商品名字
//     */
//    private String goodsName;
//    /**
//     * 客户端类型
//     */
//    private String appType;
//    /**
//     * 微信公众号
//     */
//    private String appId;
//    /**
//     * 二维码支付类型
//     */
//    private String payType;

//    public String getPayType() {
//        return payType;
//    }
//
//    public void setPayType(String payType) {
//        this.payType = payType;
//    }
//
//    public String getOpenId() {
//        return openId;
//    }
//
//    public void setOpenId(String openId) {
//        this.openId = openId;
//    }
//
//    public String getGoodsName() {
//        return goodsName;
//    }
//
//    public void setGoodsName(String goodsName) {
//        this.goodsName = goodsName;
//    }
//
//    public String getAppType() {
//        return appType;
//    }
//
//    public void setAppType(String appType) {
//        this.appType = appType;
//    }
//
//    public String getAppId() {
//        return appId;
//    }
//
//    public void setAppId(String appId) {
//        this.appId = appId;
//    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getPayerNo() {
        return payerNo;
    }

    public void setPayerNo(String payerNo) {
        this.payerNo = payerNo;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public String getEncryptionParams() {
        return encryptionParams;
    }

    public void setEncryptionParams(String encryptionParams) {
        this.encryptionParams = encryptionParams;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getBrowserUA() {
        return browserUA;
    }

    public void setBrowserUA(String browserUA) {
        this.browserUA = browserUA;
    }
}
