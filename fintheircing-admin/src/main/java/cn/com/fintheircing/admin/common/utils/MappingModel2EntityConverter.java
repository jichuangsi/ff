package cn.com.fintheircing.admin.common.utils;

import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.model.ContractModel;

public final class MappingModel2EntityConverter {

    private MappingModel2EntityConverter(){}


    public static final BusinessContract CONVERTERFORCONTRACT(ContractModel model){
        BusinessContract contract = new BusinessContract();
        contract.setAbortLine(model.getAbortLine());
        contract.setBorrowMoney(model.getBorrowMoney());
        contract.setChoseWay(model.getChoseWay());
        contract.setColdMoney(model.getColdCash());
        contract.setContractNum(model.getContractNum());
        contract.setDangerourPrpmised(model.getDangerCash());
        contract.setProductId(model.getProductModel().getId());
        contract.setPromisedMoney(model.getPromisedMoney());
        contract.setTransactorId(model.getSaleManId());
        contract.setUserId(model.getUserId());
        contract.setWarningLine(model.getWarningLine());
        contract.setUuid(model.getId());
        contract.setFirstInterest(model.getFirst());

        return contract;
    }
}
