package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BusinessControlContract extends AbstractEntity{
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String contractId;
    private int controlType;   //ControlCode
    private double costMoney;       //合约操作内扣款
    private double addMoney;        //合约操作内补资金
    private double lessMoney;       //操作后合约剩余
    private double pickUpMoney;   //提取金额
    private double windUpMoney;    //结算金额
    private int verifyStatus;    //审核状态
    private double businessMoney;   //服务费
    private double taxationMoney;   //税费

    private int addStock;
    private int costStock;
    private int lessStock;
    private String stockId;

    private double borrowMoney;
    private double promisedMoney;
    private double warnningLine;
    private double abortLine;
    private double borrowRate;
    private long borrowTime;
    private double firstInterest;
    private double abortingLine;//预警平仓线

    public double getAbortingLine() {
        return abortingLine;
    }

    public void setAbortingLine(double abortingLine) {
        this.abortingLine = abortingLine;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getAddStock() {
        return addStock;
    }

    public void setAddStock(int addStock) {
        this.addStock = addStock;
    }

    public int getCostStock() {
        return costStock;
    }

    public void setCostStock(int costStock) {
        this.costStock = costStock;
    }

    public int getLessStock() {
        return lessStock;
    }

    public void setLessStock(int lessStock) {
        this.lessStock = lessStock;
    }

    public double getWarnningLine() {
        return warnningLine;
    }

    public void setWarnningLine(double warnningLine) {
        this.warnningLine = warnningLine;
    }

    public double getAbortLine() {
        return abortLine;
    }

    public void setAbortLine(double abortLine) {
        this.abortLine = abortLine;
    }

    public double getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(double borrowRate) {
        this.borrowRate = borrowRate;
    }

    public long getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(long borrowTime) {
        this.borrowTime = borrowTime;
    }

    public double getFirstInterest() {
        return firstInterest;
    }

    public void setFirstInterest(double firstInterest) {
        this.firstInterest = firstInterest;
    }

    public double getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(double borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public double getPromisedMoney() {
        return promisedMoney;
    }

    public void setPromisedMoney(double promisedMoney) {
        this.promisedMoney = promisedMoney;
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

    public int getControlType() {
        return controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }

    public double getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(double costMoney) {
        this.costMoney = costMoney;
    }

    public double getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(double addMoney) {
        this.addMoney = addMoney;
    }

    public double getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(double lessMoney) {
        this.lessMoney = lessMoney;
    }

    public double getPickUpMoney() {
        return pickUpMoney;
    }

    public void setPickUpMoney(double pickUpMoney) {
        this.pickUpMoney = pickUpMoney;
    }

    public double getWindUpMoney() {
        return windUpMoney;
    }

    public void setWindUpMoney(double windUpMoney) {
        this.windUpMoney = windUpMoney;
    }

    public int getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        this.verifyStatus = verifyStatus;
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
}
