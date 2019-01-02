package cn.com.fintheircing.admin.common.entity.Contact;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name ="contactInfo")
public class contactInfo {
    @Id
    @GeneratedValue
    private String id;
    private String contactId;//合约编号
    private Date createTime;//发生时间
    private String goodsType;//产品信息
    private String borrowMoney;//借款金额
    private Date borrowTime;//借款时间
    private Date remainderTime;//剩余时间
    private String assureMoney;//保证金金额
    private String deficitAmount;//亏损警戒线
    private String deficitLine;//亏损平仓线
    private String contactAllMoney;//合约总资产
    private String availableCash;//可用余额
    private String stockValue;//股票市值
    private String floatFilled;//浮盈
    private String status;//状态

    @OneToOne
    @JoinColumn(name="contactDetails_id")//关联的表为contactDetails表，其主键是id
    private contactDetails contactDetails;
    @OneToOne
    @JoinColumn(name="contractStock_id")//关联的表为contractStock表，其主键是id
    private contractStock contractStock;

    public contractStock  getContractStock() {
        return contractStock;
    }

    public void setContractStock(contractStock contractStock) {
        this.contractStock = contractStock;
    }

    public contactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(contactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(String borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getRemainderTime() {
        return remainderTime;
    }

    public void setRemainderTime(Date remainderTime) {
        this.remainderTime = remainderTime;
    }

    public String getAssureMoney() {
        return assureMoney;
    }

    public void setAssureMoney(String assureMoney) {
        this.assureMoney = assureMoney;
    }

    public String getDeficitAmount() {
        return deficitAmount;
    }

    public void setDeficitAmount(String deficitAmount) {
        this.deficitAmount = deficitAmount;
    }

    public String getDeficitLine() {
        return deficitLine;
    }

    public void setDeficitLine(String deficitLine) {
        this.deficitLine = deficitLine;
    }

    public String getContactAllMoney() {
        return contactAllMoney;
    }

    public void setContactAllMoney(String contactAllMoney) {
        this.contactAllMoney = contactAllMoney;
    }

    public String getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(String availableCash) {
        this.availableCash = availableCash;
    }

    public String getStockValue() {
        return stockValue;
    }

    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }

    public String getFloatFilled() {
        return floatFilled;
    }

    public void setFloatFilled(String floatFilled) {
        this.floatFilled = floatFilled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
