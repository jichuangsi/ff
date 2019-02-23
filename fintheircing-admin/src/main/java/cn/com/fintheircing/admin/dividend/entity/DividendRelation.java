package cn.com.fintheircing.admin.dividend.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DividendRelation extends AbstractEntity {

    public static String VALIDATE_WAIT = "0";
    public static String VALIDATE_PASS = "1";
    public static String VALIDATE_SUS = "2";

    public static String CHOSE_STOCK = "0";
    public static String  CHOSE_MONEY = "1";

    public static String DIVIDEND_WAIT = "0";
    public static String DIVIDEND_END = "1";


    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String contractId;
    private String dividendId;
    private String stockId;
    private String validateStatus;
    private long happenTime;
    private String choseWay;
    private double tenStockMoney;
    private int tenStockAmount;
    private double tenStockCost;
    private double amount;
    private long validateTime;

    private String dividendStatus;

    public long getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(long validateTime) {
        this.validateTime = validateTime;
    }

    public String getDividendStatus() {
        return dividendStatus;
    }

    public void setDividendStatus(String dividendStatus) {
        this.dividendStatus = dividendStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public double getTenStockCost() {
        return tenStockCost;
    }

    public void setTenStockCost(double tenStockCost) {
        this.tenStockCost = tenStockCost;
    }

    public long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(long happenTime) {
        this.happenTime = happenTime;
    }

    public String getChoseWay() {
        return choseWay;
    }

    public void setChoseWay(String choseWay) {
        this.choseWay = choseWay;
    }

    public String getValidateStatus() {
        return validateStatus;
    }

    public void setValidateStatus(String validateStatus) {
        this.validateStatus = validateStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getDividendId() {
        return dividendId;
    }

    public void setDividendId(String dividendId) {
        this.dividendId = dividendId;
    }
}
