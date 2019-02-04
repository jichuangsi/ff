package cn.com.fintheircing.admin.business.synchronize;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class SynchronizeComponent {

    @Resource
    private BusinessService businessService;

    public String synchronizedSaveContract(ContractModel model) {
        synchronized (model.getContractNum().intern()) {
            return businessService.saveContract(model);
        }
    }

    public void synchronizedCostColdContract(StockEntrustModel model) throws BusinessException {
        synchronized (model.getContractId().intern()) {
            businessService.costColdContract(model);
        }
    }

    public void synchronizedDealStockHand(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        synchronized (model.getId().intern()) {
            businessService.dealStockHand(userInfo, model);
        }
    }

    public Map<String, String> synchronizedCheckEntrust(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        synchronized (model.getId().intern()) {
           return businessService.checkEntrust(userInfo, model);
        }
    }

    public void synchronizedSellHoldStock(StockHoldingModel model) throws BusinessException {
        synchronized (model.getContractId().intern()) {
            businessService.sellHoldStock(model);
        }
    }

    public void synchronizedDealSellHand(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        synchronized (model.getId().intern()) {
            businessService.dealSellHand(userInfo, model);
        }
    }

    public void synchronizedCancelOrder(StockEntrustModel model) throws BusinessException {
        synchronized (model.getContractId().intern()) {
            businessService.cancelOrder(model);
        }
    }

    public void synchronizedDealBackHand(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        synchronized (model.getId().intern()) {
            businessService.dealBackHand(userInfo, model);
        }
    }

    public ContractModel synchronizedEndContract(ContractModel model) throws BusinessException{
        synchronized (model.getId().intern()){
            return businessService.endContract(model);
        }
    }
}
