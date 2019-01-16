package cn.com.fintheircing.customer.user.model.payresultmodel;
/**
 * 支付宝或者微信二维码返回地址
 *
 * @author yaoxiong
 * @date 2019/1/14
 */
public class AppResultModel {
    private String result;
    private String qrcodeUrl;
    private String failReason;

    /**
     * 支付类型
     */
    private String payType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
