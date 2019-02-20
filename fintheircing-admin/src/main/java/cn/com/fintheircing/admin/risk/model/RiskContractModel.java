package cn.com.fintheircing.admin.risk.model;

import cn.com.fintheircing.admin.business.model.ContractModel;

public class RiskContractModel extends ContractModel {

    public static String STATUS_SALE ="0";
    public static String STATUS_TOUCH = "1";

    private String warnningStr; //是否触发警戒线str
    private Integer warnningStatus;     //是否触发警戒线num
    private String abortStatus; //是否触发平仓线
    private String name;    //姓名
    private long expiredTime;   //下次缴费时间
    private Integer havingStock;    //是否持仓
    private Integer status; //合约状态
    private String contractStatus;  //合约状态str
    private double holdingRate; //持仓比例
    private Integer holdingAmount;  //持仓数
    private Integer lessDay;    //剩下天数
    private Integer canSell;    //可卖
    private Double riskRate;    //风险度
    private Double abortSafeRate;   //强平安全度
    private Double warnSafeRate;   //预警安全度

    public Double getWarnSafeRate() {
        return warnSafeRate;
    }

    public void setWarnSafeRate(Double warnSafeRate) {
        this.warnSafeRate = warnSafeRate;
    }

    public Integer getHoldingAmount() {
        return holdingAmount;
    }

    public void setHoldingAmount(Integer holdingAmount) {
        this.holdingAmount = holdingAmount;
    }

    public Integer getLessDay() {
        return lessDay;
    }

    public void setLessDay(Integer lessDay) {
        this.lessDay = lessDay;
    }

    public Integer getCanSell() {
        return canSell;
    }

    public void setCanSell(Integer canSell) {
        this.canSell = canSell;
    }

    public Double getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(Double riskRate) {
        this.riskRate = riskRate;
    }

    public Double getAbortSafeRate() {
        return abortSafeRate;
    }

    public void setAbortSafeRate(Double abortSafeRate) {
        this.abortSafeRate = abortSafeRate;
    }



    public double getHoldingRate() {
        return holdingRate;
    }

    public void setHoldingRate(double holdingRate) {
        this.holdingRate = holdingRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getHavingStock() {
        return havingStock;
    }

    public void setHavingStock(Integer havingStock) {
        this.havingStock = havingStock;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarnningStr() {
        return warnningStr;
    }

    public void setWarnningStr(String warnningStr) {
        this.warnningStr = warnningStr;
    }

    public Integer getWarnningStatus() {
        return warnningStatus;
    }

    public void setWarnningStatus(Integer warnningStatus) {
        this.warnningStatus = warnningStatus;
    }

    public String getAbortStatus() {
        return abortStatus;
    }

    public void setAbortStatus(String abortStatus) {
        this.abortStatus = abortStatus;
    }
}
