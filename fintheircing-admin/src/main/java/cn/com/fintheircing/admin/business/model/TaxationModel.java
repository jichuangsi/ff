package cn.com.fintheircing.admin.business.model;

public class TaxationModel {
    private String id;
    private String taxationName;
    private Double taxationRate;
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaxationName() {
        return taxationName;
    }

    public void setTaxationName(String taxationName) {
        this.taxationName = taxationName;
    }

    public Double getTaxationRate() {
        return taxationRate;
    }

    public void setTaxationRate(Double taxationRate) {
        this.taxationRate = taxationRate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
