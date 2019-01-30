package cn.com.fintheircing.admin.scheduling.model;

import java.util.HashSet;
import java.util.Set;

public class ContractHoldingJsonModel {
    Set<ContractHoldingModel> contractHoldingModels = new HashSet<ContractHoldingModel>();

    public Set<ContractHoldingModel> getContractHoldingModels() {
        return contractHoldingModels;
    }

    public void setContractHoldingModels(Set<ContractHoldingModel> contractHoldingModels) {
        this.contractHoldingModels = contractHoldingModels;
    }
}
