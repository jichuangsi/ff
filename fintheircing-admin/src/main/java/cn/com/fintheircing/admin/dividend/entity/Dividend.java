package cn.com.fintheircing.admin.dividend.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dividend extends AbstractEntity {

    public static String STATUS_END = "1";
    public static String STATUS_WAIT = "0";

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String stockId;
    private double tenStockMoney;
    private int tenStockAmount;
    private long activeTimeMoney;
    private long activeTimeAmount;
    private double tenStockCost;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public double getTenStockMoney() {
        return tenStockMoney;
    }

    public void setTenStockMoney(double tenStockMoney) {
        this.tenStockMoney = tenStockMoney;
    }

    public int getTenStockAmount() {
        return tenStockAmount;
    }

    public void setTenStockAmount(int tenStockAmount) {
        this.tenStockAmount = tenStockAmount;
    }

    public long getActiveTimeMoney() {
        return activeTimeMoney;
    }

    public void setActiveTimeMoney(long activeTimeMoney) {
        this.activeTimeMoney = activeTimeMoney;
    }

    public long getActiveTimeAmount() {
        return activeTimeAmount;
    }

    public void setActiveTimeAmount(long activeTimeAmount) {
        this.activeTimeAmount = activeTimeAmount;
    }

    public double getTenStockCost() {
        return tenStockCost;
    }

    public void setTenStockCost(double tenStockCost) {
        this.tenStockCost = tenStockCost;
    }
}
