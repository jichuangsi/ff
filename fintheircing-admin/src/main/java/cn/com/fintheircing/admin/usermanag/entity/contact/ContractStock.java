package cn.com.fintheircing.admin.usermanag.entity.contact;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ContractStock")
public class ContractStock {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;
    private String stockId;//股票代码ID
    private String stockName;//股票名称
    private String stockAccount;//数量
    private String buyPrice;//买入价
    private String MarketPrice;//市价
    private String MarketValue;//市值
    private String buyTime;//购入时间

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ContractStock")
//    private ContactInfo contactInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

//    public ContactInfo getContactInfo() {
//        return contactInfo;
//    }
//
//    public void setContactInfo(ContactInfo contactInfo) {
//        this.contactInfo = contactInfo;
//    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockAccount() {
        return stockAccount;
    }

    public void setStockAccount(String stockAccount) {
        this.stockAccount = stockAccount;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        MarketPrice = marketPrice;
    }

    public String getMarketValue() {
        return MarketValue;
    }

    public void setMarketValue(String marketValue) {
        MarketValue = marketValue;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }
}
