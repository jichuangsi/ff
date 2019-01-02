package cn.com.fintheircing.admin.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AskMoneyInfo extends AbstractEntity  {
    public final static String STATUS_EXIST = "0";
    public final static String STATUS_NOTEXIST = "1";
    @Id
    @GeneratedValue
    private String id;
    private String userId;
    private String applyTime;
    private String applyMoney;//申请金额
    private String trulyMoney;//实际到账
    private String payAcount;//付款账号
    private String payFrom;//支付渠道
    private String taskstatus;//审核状态
    private String status;//状态
    private String bankCard;//银行卡号
    private String bankName;//银行名字
    private String Recharge; //充值金额
    private String RechargeTime; //充值时间
    private String ArrivalTime; //到账时间
    private String payWay;//打款方式
    private String remark;//备注

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRecharge() {
        return Recharge;
    }

    public void setRecharge(String recharge) {
        Recharge = recharge;
    }

    public String getRechargeTime() {
        return RechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        RechargeTime = rechargeTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(String applyMoney) {
        this.applyMoney = applyMoney;
    }

    public String getTrulyMoney() {
        return trulyMoney;
    }

    public void setTrulyMoney(String trulyMoney) {
        this.trulyMoney = trulyMoney;
    }

    public String getPayAcount() {
        return payAcount;
    }

    public void setPayAcount(String payAcount) {
        this.payAcount = payAcount;
    }

    public String getPayFrom() {
        return payFrom;
    }

    public void setPayFrom(String payFrom) {
        this.payFrom = payFrom;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }
}
