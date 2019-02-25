package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * 委托单
 */
@Entity
public class BusinessStockEntrust extends AbstractEntity {

    public final static Integer STOCK_BUY = 0;
    public final static Integer STOCK_SELL = 1;

    public final static String STOCK_ORDER = "0";
    public final static String STOCK_CANCEL_ORDER = "1";

    public final static String DEAL_ADMIN = "1";
    public final static String DEAL_CUSTOMER = "0";

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String userId;  //关联用户
    private String stockId;     //关联股票
    private int businessTo;     //买卖方向
    private int entrustStatus;      //委托状态
    private double businessPrice;      //委托交易出去的单价
    private int businessAmount;      ///总数量
    private String contractId;
    private String cancelOrder;
    private double dealPrice;   //买入,卖出的成交价钱
    private String dealNo;     //委托编号
    private int dealNum;     //成交数量
    private long dealTime;      //成交时间
    private String montherAccount;  //母账户
    private String holdingId;
    private String cancelNo;
    private double coldMoney;
    private double businessMoney;
    private double taxationMoney;//税收
    private String dealMan;
    private String dealFrom;
    @Version
    private int version;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getBusinessTo() {
        return businessTo;
    }

    public void setBusinessTo(int businessTo) {
        this.businessTo = businessTo;
    }

    public int getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(int entrustStatus) {
        this.entrustStatus = entrustStatus;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }

    public int getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(int businessAmount) {
        this.businessAmount = businessAmount;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(String cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }

    public int getDealNum() {
        return dealNum;
    }

    public void setDealNum(int dealNum) {
        this.dealNum = dealNum;
    }

    public long getDealTime() {
        return dealTime;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public String getMontherAccount() {
        return montherAccount;
    }

    public void setMontherAccount(String montherAccount) {
        this.montherAccount = montherAccount;
    }

    public String getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    public String getCancelNo() {
        return cancelNo;
    }

    public void setCancelNo(String cancelNo) {
        this.cancelNo = cancelNo;
    }

    public double getColdMoney() {
        return coldMoney;
    }

    public void setColdMoney(double coldMoney) {
        this.coldMoney = coldMoney;
    }

    public double getBusinessMoney() {
        return businessMoney;
    }

    public void setBusinessMoney(double businessMoney) {
        this.businessMoney = businessMoney;
    }

    public double getTaxationMoney() {
        return taxationMoney;
    }

    public void setTaxationMoney(double taxationMoney) {
        this.taxationMoney = taxationMoney;
    }

    public String getDealMan() {
        return dealMan;
    }

    public void setDealMan(String dealMan) {
        this.dealMan = dealMan;
    }

    public String getDealFrom() {
        return dealFrom;
    }

    public void setDealFrom(String dealFrom) {
        this.dealFrom = dealFrom;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
