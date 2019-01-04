package cn.com.fintheircing.admin.systemdetect.model;

/**
 * ProductModel
 *
 * @author 姚雄
 * @date 2019/1/2
 */
public class ProductModel {
    private String id;
    /**
     * 买入时收取的交易金额
     */
    private double entryAmount;
    /**
     * 收市时收取的持股市值
     */
    private double outAmount;
    /**
     * 交易时收取的持股市值
     */
    private double moneyInDeal;
    /**
     *开立合约时收取的配资额
     */
    private double moneyInContact;
    /**
     * 配资时长
     */
    private double financingTime;
    /**
     * 杠杆比率
     */
    private int leverRate;
    /**
     * 警戒线
     */
    private double wornLine;
    /**
     * 平仓线
     */
    private double liquidation;
    /**
     * 日配或者月配或者特殊
     */
    private Integer allot;

    private String allotStr;

    public String getAllotStr() {
        return allotStr;
    }

    public void setAllotStr(String allotStr) {
        this.allotStr = allotStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getEntryAmount() {
        return entryAmount;
    }

    public void setEntryAmount(double entryAmount) {
        this.entryAmount = entryAmount;
    }

    public double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(double outAmount) {
        this.outAmount = outAmount;
    }

    public double getMoneyInDeal() {
        return moneyInDeal;
    }

    public void setMoneyInDeal(double moneyInDeal) {
        this.moneyInDeal = moneyInDeal;
    }

    public double getMoneyInContact() {
        return moneyInContact;
    }

    public void setMoneyInContact(double moneyInContact) {
        this.moneyInContact = moneyInContact;
    }

    public double getFinancingTime() {
        return financingTime;
    }

    public void setFinancingTime(double financingTime) {
        this.financingTime = financingTime;
    }

    public int getLeverRate() {
        return leverRate;
    }

    public void setLeverRate(int leverRate) {
        this.leverRate = leverRate;
    }

    public double getWornLine() {
        return wornLine;
    }

    public void setWornLine(double wornLine) {
        this.wornLine = wornLine;
    }

    public double getLiquidation() {
        return liquidation;
    }

    public void setLiquidation(double liquidation) {
        this.liquidation = liquidation;
    }

    public Integer getAllot() {
        return allot;
    }

    public void setAllot(Integer allot) {
        this.allot = allot;
    }
}
