package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

/**
 * 委托单
 */
@Entity
public class BusinessStockEntrust extends AbstractEntity {

    public final static Integer STOCK_BUY = 0;
    public final static Integer STOCK_SELL = 1;

    public final static String STOCK_ORDER = "0";
    public final static String STOCK_CANCEL_ORDER = "1";

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String userId;  //关联用户
    private String stockId;     //关联股票
    private Integer businessTo;     //买卖方向
    private Integer entrustStatus;      //委托状态
    private Double businessPrice;      //交易单价
    private Integer businessAmount;      ///总数量
    private String contractId;
    private String cancelOrder;
    private Double dealPrice;   //成交价钱
    private String dealNo;     //委托编号
    private String dealNum;     //成交数量
    private Date dealTime;      //成交时间
    private String montherAccount;  //母账户
    private String holdingId;
    private String cancelNo;
    @Version
    private Integer version;

    public String getCancelNo() {
        return cancelNo;
    }

    public void setCancelNo(String cancelNo) {
        this.cancelNo = cancelNo;
    }

    public String getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(String cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    public String getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getMontherAccount() {
        return montherAccount;
    }

    public void setMontherAccount(String montherAccount) {
        this.montherAccount = montherAccount;
    }

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

    public Integer getBusinessTo() {
        return businessTo;
    }

    public void setBusinessTo(Integer businessTo) {
        this.businessTo = businessTo;
    }

    public Integer getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(Integer entrustStatus) {
        this.entrustStatus = entrustStatus;
    }


    public Double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public Double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(Double businessPrice) {
        this.businessPrice = businessPrice;
    }

    public Integer getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(Integer businessAmount) {
        this.businessAmount = businessAmount;
    }

    public String getDealNum() {
        return dealNum;
    }

    public void setDealNum(String dealNum) {
        this.dealNum = dealNum;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }
}
