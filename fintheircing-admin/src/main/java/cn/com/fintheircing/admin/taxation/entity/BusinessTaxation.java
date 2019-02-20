package cn.com.fintheircing.admin.taxation.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 税费计算
 */
@Entity
public class BusinessTaxation extends AbstractEntity{

    public static final String BUSINESS_BUY = "0";
    public static final String BUSINESS_SELL = "1";

    public static final String FIXED_MONEY = "0";
    public static final String PERCENT_MONEY = "1";

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String taxName;     //税费名
    private double taxRate;     //税费比例
    private String bsuinessTo;     //买卖方向
    private String labelName;      //标签名，动态生成表格时的标签
    private String remarks;     //备注
    private String fixed;

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getBsuinessTo() {
        return bsuinessTo;
    }

    public void setBsuinessTo(String bsuinessTo) {
        this.bsuinessTo = bsuinessTo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
