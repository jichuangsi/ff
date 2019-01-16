package cn.com.fintheircing.admin.business.entity;

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

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String taxName;     //税费名
    private Double taxRate;     //税费比例
    private Integer bsuinessTo;     //买卖方向
    private String remarks;     //备注

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

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getBsuinessTo() {
        return bsuinessTo;
    }

    public void setBsuinessTo(Integer bsuinessTo) {
        this.bsuinessTo = bsuinessTo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
