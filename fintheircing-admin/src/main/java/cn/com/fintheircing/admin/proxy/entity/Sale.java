package cn.com.fintheircing.admin.proxy.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sale extends AbstractEntity {

    public final  static  String BUSINESS_BUY = "0";
    public final  static  String BUSINESS_SALE = "1";

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;
    private String contractNum; //合约编号
    private String sharesId;  //股票代码
    private String sharesName;  //股票名
    private String businessTo;  //买入卖出
    private Double count;  //数量
    private Double amount;  //总金额
    private Double commission;  //收佣
    private Double cost;  //成本
    private Double gain;  //收获
    private String salemanId;  //销售员本人
    private Double stampDuty;  //印花税
    private Double transferFee;  //过户费
    private Double five;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSharesId() {
        return sharesId;
    }

    public void setSharesId(String sharesId) {
        this.sharesId = sharesId;
    }

    public String getSharesName() {
        return sharesName;
    }

    public void setSharesName(String sharesName) {
        this.sharesName = sharesName;
    }

    public String getBusinessTo() {
        return businessTo;
    }

    public void setBusinessTo(String businessTo) {
        this.businessTo = businessTo;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public String getSalemanId() {
        return salemanId;
    }

    public void setSalemanId(String salemanId) {
        this.salemanId = salemanId;
    }

    public Double getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(Double stampDuty) {
        this.stampDuty = stampDuty;
    }

    public Double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }

    public Double getFive() {
        return five;
    }

    public void setFive(Double five) {
        this.five = five;
    }
}
