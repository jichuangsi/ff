package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BusinessContractRisk extends AbstractEntity{

    public static final Integer SHUTDOWN_BUY = 0;
    public static final Integer SHUTDOWN_NOT_BUY = 1;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String contractId;      //关联合约表
    private Double warningLine;    //警告线
    private Double abortLine;    //平仓线
    private Double customerMaxAccount;  //最大购买
    private Double venturEditionMaxAccount;     //创业股最大购买
    private Double holdOverFiveAvg;     //持仓不超过五日平均
    private Double holdOverCurrency;       //持仓不超过流通市值
    private Integer stockShutDown;      //跌停能否购买

    public Double getCustomerMaxAccount() {
        return customerMaxAccount;
    }

    public void setCustomerMaxAccount(Double customerMaxAccount) {
        this.customerMaxAccount = customerMaxAccount;
    }

    public Double getVenturEditionMaxAccount() {
        return venturEditionMaxAccount;
    }

    public void setVenturEditionMaxAccount(Double venturEditionMaxAccount) {
        this.venturEditionMaxAccount = venturEditionMaxAccount;
    }

    public Double getHoldOverFiveAvg() {
        return holdOverFiveAvg;
    }

    public void setHoldOverFiveAvg(Double holdOverFiveAvg) {
        this.holdOverFiveAvg = holdOverFiveAvg;
    }

    public Double getHoldOverCurrency() {
        return holdOverCurrency;
    }

    public void setHoldOverCurrency(Double holdOverCurrency) {
        this.holdOverCurrency = holdOverCurrency;
    }

    public Integer getStockShutDown() {
        return stockShutDown;
    }

    public void setStockShutDown(Integer stockShutDown) {
        this.stockShutDown = stockShutDown;
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

    public Double getWarningLine() {
        return warningLine;
    }

    public void setWarningLine(Double warningLine) {
        this.warningLine = warningLine;
    }

    public Double getAbortLine() {
        return abortLine;
    }

    public void setAbortLine(Double abortLine) {
        this.abortLine = abortLine;
    }
}
