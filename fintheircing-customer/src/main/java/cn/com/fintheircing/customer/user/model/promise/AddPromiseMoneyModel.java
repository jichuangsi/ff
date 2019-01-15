package cn.com.fintheircing.customer.user.model.promise;

public class AddPromiseMoneyModel {
    private String BusinessContractId;
    private Double cash;

    public String getBusinessContractId() {
        return BusinessContractId;
    }

    public void setBusinessContractId(String businessContractId) {
        BusinessContractId = businessContractId;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }
}
