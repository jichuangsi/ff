package cn.com.fintheircing.admin.business.entity.recode;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class StockEntrustRecode  {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String submitterId;//提交的人
    private String submitterName;//提交的人名字
    private String contactId;//
    private String userId;
    private String userName;
    private String phone;
    private String applyType;//申请类型
    private Date buyTime;//购入时间
    private Date createTime=new Date();//申请时间
    private String remark;
    private Integer checkStatus=0;//0为未审核,1为通过,2为未通过
    private String stockId;
    private String stockNum;//股票代码
    private String stockName;
    private int amount;
    private double dealPrice;
    private String BusinessStockEntrustId;
    private double codeMoney;
    private double userfulMoney;

    public double getCodeMoney() {
        return codeMoney;
    }

    public void setCodeMoney(double codeMoney) {
        this.codeMoney = codeMoney;
    }

    public double getUserfulMoney() {
        return userfulMoney;
    }

    public void setUserfulMoney(double userfulMoney) {
        this.userfulMoney = userfulMoney;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getBusinessStockEntrustId() {
        return BusinessStockEntrustId;
    }

    public void setBusinessStockEntrustId(String businessStockEntrustId) {
        BusinessStockEntrustId = businessStockEntrustId;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }
}
