package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BusinessContractRisk extends AbstractEntity{

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String contractId;      //关联合约表
    private Double warningLine;    //警告线
    private Double abortLine;    //平仓线

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
