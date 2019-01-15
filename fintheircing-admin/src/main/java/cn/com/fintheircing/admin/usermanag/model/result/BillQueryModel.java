package cn.com.fintheircing.admin.usermanag.model.result;
/**
 * queryModel  返回待确认充值数据
 *
 * @author yaoxiong
 * @date 2016/10/31
 */
public class BillQueryModel {
    private String orderId;
    private String tradeId;
    private String encryptionParams;
    private String transDate;

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
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

    public String getEncryptionParams() {
        return encryptionParams;
    }

    public void setEncryptionParams(String encryptionParams) {
        this.encryptionParams = encryptionParams;
    }
}
