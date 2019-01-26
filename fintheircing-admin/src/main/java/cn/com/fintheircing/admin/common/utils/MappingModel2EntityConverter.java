package cn.com.fintheircing.admin.common.utils;

import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.model.ContractModel;

public final class MappingModel2EntityConverter {

    private MappingModel2EntityConverter(){}


    public static final BusinessContract CONVERTERFORCONTRACT(ContractModel model){
        BusinessContract contract = new BusinessContract();
        contract.setBorrowMoney(model.getBorrowMoney());
        contract.setChoseWay(model.getChoseWay());
        contract.setColdMoney(model.getColdCash()==null?0.0:model.getColdCash());
        contract.setContractNum(model.getContractNum());
        contract.setDangerourPrpmised(model.getDangerCash()==null?0.0:model.getDangerCash());
        contract.setProductId(model.getProductModel().getId());
        contract.setPromisedMoney(model.getPromisedMoney()==null?0.0:model.getPromisedMoney());
        contract.setTransactorId(model.getSaleManId());
        contract.setUserId(model.getUserId());
        contract.setUuid(model.getId());
        contract.setFirstInterest(model.getFirst()==null?0.0:model.getFirst());
        return contract;
    }
}
