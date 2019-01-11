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
    private Double lessMoney;
    private Double pickUpMoney;   //提取金额
    private Double windUpMoney;    //结算金额
    private Integer verifyStatus;    //审核状态

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
