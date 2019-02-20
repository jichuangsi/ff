package cn.com.fintheircing.admin.business.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class BusinessContract extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String contractNum;   //合约编号
    private String userId;    //关联用户
    private String productId;    //关联产品
    private String transactorId;   //关联销售
    private int choseWay;    //选择付利息方式
    private double promisedMoney;   //保证金
    private double dangerourPrpmised;   //超危保证金
    private double borrowMoney;   //借款
    private long expiredTime;  //到期日
    private double onceServerMoney;
    private double coldMoney;   //
    private String riskId;     //关联风控冻结资金
    private double firstInterest;   //初次利息
    private double availableMoney;  //可用资金
    private int contractStatus;     //合约状态，新建，交易中，结束交易
    private int rudeStatus;     //是否强制平仓
    private int warnningStatus;
    @Version
    private int version;

    public int getWarnningStatus() {
        return warnningStatus;
    }

    public void setWarnningStatus(int warnningStatus) {
        this.warnningStatus = warnningStatus;
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

    public int getChoseWay() {
        return choseWay;
    }

    public void setChoseWay(int choseWay) {
        this.choseWay = choseWay;
    }

    public double getPromisedMoney() {
        return promisedMoney;
    }

    public void setPromisedMoney(double promisedMoney) {
        this.promisedMoney = promisedMoney;
    }

    public double getDangerourPrpmised() {
        return dangerourPrpmised;
    }

    public void setDangerourPrpmised(double dangerourPrpmised) {
        this.dangerourPrpmised = dangerourPrpmised;
    }

    public double getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(double borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public double getOnceServerMoney() {
        return onceServerMoney;
    }

    public void setOnceServerMoney(double onceServerMoney) {
        this.onceServerMoney = onceServerMoney;
    }

    public double getColdMoney() {
        return coldMoney;
    }

    public void setColdMoney(double coldMoney) {
        this.coldMoney = coldMoney;
    }

    public String getRiskId() {
        return riskId;
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId;
    }

    public double getFirstInterest() {
        return firstInterest;
    }

    public void setFirstInterest(double firstInterest) {
        this.firstInterest = firstInterest;
    }

    public double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(double availableMoney) {
        this.availableMoney = availableMoney;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    public int getRudeStatus() {
        return rudeStatus;
    }

    public void setRudeStatus(int rudeStatus) {
        this.rudeStatus = rudeStatus;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
