package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class BusinessContract extends AbstractEntity{
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String contractNum;   //合约编号
    private String userId;    //关联用户
    private String productId;    //关联产品
    private String transactorId;   //关联销售
    private Integer choseWay;    //选择付利息方式
    private Double promisedMoney;   //保证金
    private Double dangerourPrpmised;   //超危保证金
    private Double borrowMoney;   //借款
    private Double coldMoney;   //冻结资金
    private Double warningLine;    //警告线
    private Double abortLine;    //平仓线
    private Double firstInterest;   //初次利息
    @Version
    private int version;

    public Double getFirstInterest() {
        return firstInterest;
    }

    public void setFirstInterest(Double firstInterest) {
        this.firstInterest = firstInterest;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Double getColdMoney() {
        return coldMoney;
    }

    public void setColdMoney(Double coldMoney) {
        this.coldMoney = coldMoney;
    }


    public Double getDangerourPrpmised() {
        return dangerourPrpmised;
    }

    public void setDangerourPrpmised(Double dangerourPrpmised) {
        this.dangerourPrpmised = dangerourPrpmised;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTransactorId() {
        return transactorId;
    }

    public void setTransactorId(String transactorId) {
        this.transactorId = transactorId;
    }

    public Integer getChoseWay() {
        return choseWay;
    }

    public void setChoseWay(Integer choseWay) {
        this.choseWay = choseWay;
    }

    public Double getPromisedMoney() {
        return promisedMoney;
    }

    public void setPromisedMoney(Double promisedMoney) {
        this.promisedMoney = promisedMoney;
    }

    public Double getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(Double borrowMoney) {
        this.borrowMoney = borrowMoney;
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
