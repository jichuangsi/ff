package cn.com.fintheircing.admin.scheduling.model;

import java.util.HashSet;
import java.util.Set;

public class ContractJsonModel {
    Set<String> contractIds = new HashSet<String>();

    public Set<String> getContractIds() {
        return contractIds;
    }

    public void setContractIds(Set<String> contractIds) {
        this.contractIds = contractIds;
    }
}
