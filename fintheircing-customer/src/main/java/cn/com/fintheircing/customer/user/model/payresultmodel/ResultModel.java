package cn.com.fintheircing.customer.user.model.payresultmodel;
/**
 * Demo class
 *
 * @author yaoxiong
 * @date 2019/1/6
 */
public class ResultModel {
    private String result;
    private String returnUrl;
    private String failReason;
    private Object payInfo;

    @Override
    public String toString() {
        return "ResultModel{" +
                "result='" + result + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", failReason='" + failReason + '\'' +
                ", payInfo=" + payInfo +
                '}';
    }

    public Object getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(Object payInfo) {
        this.payInfo = payInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

}
