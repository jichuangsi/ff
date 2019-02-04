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
