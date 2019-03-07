package cn.com.fintheircing.admin.business.utils;

import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.entity.order.AcceptOrder;
import cn.com.fintheircing.admin.business.entity.order.HistoryOrder;
import cn.com.fintheircing.admin.business.entity.record.StockEquityRecord;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.record.StockEquityModel;
import cn.com.fintheircing.admin.common.feign.model.TodayAcceptOrder;
import cn.com.fintheircing.admin.common.feign.model.TodayOrder;

public final class MappingModel2EntityConverter {

    private MappingModel2EntityConverter(){}

    public static StockEquityRecord CONVERTERFROMSTOCKEQUITYMODEL(StockEquityModel model){
        StockEquityRecord equityRecord = new StockEquityRecord();
        equityRecord.setAmount(model.getAmount());
        equityRecord.setApplyType(model.getApplyType());
        equityRecord.setContactId(model.getContractId());
        if (null == model.getDealPrice()){
            model.setDealPrice(0.0);
        }
        equityRecord.setDealPrice(model.getDealPrice());
        equityRecord.setRemark(model.getRemarks());
        equityRecord.setStockCode(model.getStockCode());
        equityRecord.setStockName(model.getStockName());
        return equityRecord;
    }

    public static AcceptOrder CONVERTERFROMTODAYACCEPTORDER(TodayAcceptOrder order){
        AcceptOrder acceptOrder = new AcceptOrder();
        acceptOrder.setAcceptNum(order.getAcceptNum());
        acceptOrder.setAcceptTimeStr(order.getAcceptTimeStr());
        acceptOrder.setAcceptTotalPrice(order.getAcceptTotalPrice());
        acceptOrder.setActPrice(order.getActPrice());
        acceptOrder.setActQuantity(order.getActQuantity());
        acceptOrder.setExchangeId(order.getExchangeId());
        acceptOrder.setOperFlag(order.getOperFlag());
        acceptOrder.setOperName(order.getOperName());
        acceptOrder.setOrderNumber(order.getOrderNumber());
        acceptOrder.setOrderPrice(order.getOrderPrice());
        acceptOrder.setOrderQuantity(order.getOrderQuantity());
        acceptOrder.setOrderType(order.getOrderType());
        acceptOrder.setOrderTypeName(order.getOrderTypeName());
        acceptOrder.setQuoteName(order.getQuoteName());
        acceptOrder.setShareholderCode(order.getShareholderCode());
        acceptOrder.setShareholderCodeType(order.getShareholderCodeType());
        acceptOrder.setStatus(order.getStatus());
        acceptOrder.setStockCode(order.getStockCode());
        acceptOrder.setStockName(order.getStockName());
        return acceptOrder;
    }

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

    public static HistoryOrder CONVERTERFROMTODAYORDER(TodayOrder todayOrder){
        HistoryOrder order = new HistoryOrder();
        order.setActPrice(todayOrder.getActPrice());
        order.setActQuantity(todayOrder.getActQuantity());
        order.setCancleQuantity(todayOrder.getCancleQuantity());
        order.setExchangeId(todayOrder.getExchangeId());
        order.setOperFlag(todayOrder.getOperFlag());
        order.setOperName(todayOrder.getOperName());
        order.setOrderNumber(todayOrder.getOrderNumber());
        order.setOrderPrice(todayOrder.getOrderPrice());
        order.setOrderQuantity(todayOrder.getOrderQuantity());
        order.setOrderType(todayOrder.getOrderType());
        order.setOrderTypeName(todayOrder.getOrderTypeName());
        order.setQuoteName(todayOrder.getQuoteName());
        order.setShareholderCode(todayOrder.getShareholderCode());
        order.setShareholderCodeType(todayOrder.getShareholderCodeType());
        order.setStatus(todayOrder.getStatus());
        order.setStockCode(todayOrder.getStockCode());
        order.setStockName(todayOrder.getStockName());
        order.setTimeStr(todayOrder.getTimeStr());
        return order;
    }
}
