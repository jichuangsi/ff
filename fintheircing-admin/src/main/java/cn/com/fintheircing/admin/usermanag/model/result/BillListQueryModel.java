package cn.com.fintheircing.admin.usermanag.model.result;
/**
 * D支付对账交易查询
 *
 * @author yaoxiong
 * @date 2019/1/7
 */
public class BillListQueryModel {
    private String transDate;
    private String tradeId;
    private String encryptionParams;

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
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
