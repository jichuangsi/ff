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
    private String expiredDay;  //到期日
    private Double coldMoney;   //冻结资金
    private String  riskId;     //关联风控
    private Double firstInterest;   //初次利息
    private Double availableMoney;  //可用资金
    private Integer contractStatus;     //合约状态，新建，交易中，结束交易
    private Integer rudeStatus;     //是否强制平仓
    @Version
    private int version;

    public String getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(String expiredDay) {
        this.expiredDay = expiredDay;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getRudeStatus() {
        return rudeStatus;
    }

    public void setRudeStatus(Integer rudeStatus) {
        this.rudeStatus = rudeStatus;
    }

    public String getRiskId() {
        return riskId;
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId;
    }

    public Double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(Double availableMoney) {
        this.availableMoney = availableMoney;
    }

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

}
