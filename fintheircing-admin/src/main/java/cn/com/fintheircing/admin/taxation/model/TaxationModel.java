package cn.com.fintheircing.admin.taxation.model;

public class TaxationModel {
    private String id;
    private String taxationName;
    private Double taxationRate;
    private String remarks;
    private String labelName;
    private String businessTo;
    private long updateTime;
    private String fixed;

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getBusinessTo() {
        return businessTo;
    }

    public void setBusinessTo(String businessTo) {
        this.businessTo = businessTo;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

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
