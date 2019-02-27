package cn.com.fintheircing.admin.business.model.tranfer;

import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.taxation.model.TaxationModel;

import java.util.*;

public class TranferEntrustModel extends StockEntrustModel {

    public static final String ENTRUST_FLAG = "0";
    public static final String ENTRUST_DEAL_FLAG = "1";

    private Double businessMoney;   //交易产生的费用
    private Double taxationMoney;   //总税收
    private String userPhone;   //用户电话
    private Integer choseWay;   //产品num
    private String name;    //姓名
    private Double dealMoney;   //成交金额
    private String productId;

    private String beginTime;   //条件，开始时间
    private String endTime;     //条件，结束时间
    private Date entrustBegin;  //条件
    private Date entrustEnd;//条件
    private long dealBegin;//条件
    private long dealEnd;//条件
    private String dealFlag;    //判断查询种类，委托0，成交1，流水2
    private String productName;     //产品名
    private List<TaxationModel> list = new ArrayList<TaxationModel>();     //税收全部分类

    private long entrustTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getEntrustTime() {
        return entrustTime;
    }

    public void setEntrustTime(long entrustTime) {
        this.entrustTime = entrustTime;
    }

    public List<TaxationModel> getList() {
        return list;
    }

    public void setList(List<TaxationModel> list) {
        this.list = list;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public static String getEntrustFlag() {
        return ENTRUST_FLAG;
    }

    public static String getEntrustDealFlag() {
        return ENTRUST_DEAL_FLAG;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getEntrustBegin() {
        return entrustBegin;
    }

    public void setEntrustBegin(Date entrustBegin) {
        this.entrustBegin = entrustBegin;
    }

    public Date getEntrustEnd() {
        return entrustEnd;
    }

    public void setEntrustEnd(Date entrustEnd) {
        this.entrustEnd = entrustEnd;
    }

    public long getDealBegin() {
        return dealBegin;
    }

    public void setDealBegin(long dealBegin) {
        this.dealBegin = dealBegin;
    }

    public long getDealEnd() {
        return dealEnd;
    }

    public void setDealEnd(long dealEnd) {
        this.dealEnd = dealEnd;
    }

    public String getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag;
    }

    public Double getBusinessMoney() {
        return businessMoney;
    }

    public void setBusinessMoney(Double businessMoney) {
        this.businessMoney = businessMoney;
    }

    public Double getTaxationMoney() {
        return taxationMoney;
    }

    public void setTaxationMoney(Double taxationMoney) {
        this.taxationMoney = taxationMoney;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getChoseWay() {
        return choseWay;
    }

    public void setChoseWay(Integer choseWay) {
        this.choseWay = choseWay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(Double dealMoney) {
        this.dealMoney = dealMoney;
    }
}
