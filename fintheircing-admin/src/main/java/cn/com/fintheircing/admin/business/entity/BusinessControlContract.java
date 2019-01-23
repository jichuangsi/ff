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
    private Integer controlType;   //ControlCode
    private Double costMoney;       //合约操作内扣款
    private Double addMoney;        //合约操作内补资金
    private Double lessMoney;       //操作后合约剩余
    private Double pickUpMoney;   //提取金额
    private Double windUpMoney;    //结算金额
    private Integer verifyStatus;    //审核状态
    private Double businessMoney;   //服务费
    private Double taxationMoney;   //税费

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

    public Double getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(Double costMoney) {
        this.costMoney = costMoney;
    }

    public Double getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(Double addMoney) {
        this.addMoney = addMoney;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Double getPickUpMoney() {
        return pickUpMoney;
    }

    public void setPickUpMoney(Double pickUpMoney) {
        this.pickUpMoney = pickUpMoney;
    }

    public Double getWindUpMoney() {
        return windUpMoney;
    }

    public void setWindUpMoney(Double windUpMoney) {
        this.windUpMoney = windUpMoney;
    }

    public Double getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(Double lessMoney) {
        this.lessMoney = lessMoney;
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

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }
}
