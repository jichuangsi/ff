package cn.com.fintheircing.admin.proxy.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Commission {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String salemanId;

    private Double dayCommission;  //日收佣比例
    private Double monthCommission;  //月收佣比例
    private Double backCommission;  //返佣比例

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSalemanId() {
        return salemanId;
    }

    public void setSalemanId(String salemanId) {
        this.salemanId = salemanId;
    }

    public Double getDayCommission() {
        return dayCommission;
    }

    public void setDayCommission(Double dayCommission) {
        this.dayCommission = dayCommission;
    }

    public Double getMonthCommission() {
        return monthCommission;
    }

    public void setMonthCommission(Double monthCommission) {
        this.monthCommission = monthCommission;
    }

    public Double getBackCommission() {
        return backCommission;
    }

    public void setBackCommission(Double backCommission) {
        this.backCommission = backCommission;
    }
}
