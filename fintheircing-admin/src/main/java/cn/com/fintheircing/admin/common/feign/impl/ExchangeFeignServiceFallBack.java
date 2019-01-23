package cn.com.fintheircing.admin.common.feign.impl;

import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.model.*;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeFeignServiceFallBack implements IExchangeFeignService {

    @Override
    public ResponseModel<String> sendBuyOrder(BuyOrderRequestModel buyOrderRequestModel) {
        return null;
    }

    @Override
    public ResponseModel<String> sendSellOrder(SellOrderRequestModel sellOrderRequestModel) {
        return ResponseModel.fail("");
    }

    @Override
    public ResponseModel<List<TodayOrder>> getTodayOrderList(String motnerAccount) {
        return ResponseModel.fail("");
    }

    @Override
    public ResponseModel<String> cancelOrder(CancleOrderRequestModel cancleOrderRequestModel) {
        return ResponseModel.fail("");
    }

    @Override
    public ResponseModel<List<CanCancleOrder>> getCanCancleOrderList(String motnerAccount) {
        return ResponseModel.fail("");
    }

    @Override
    public ResponseModel<List<TodayAcceptOrder>> getTodayAcceptOrderList(String motnerAccount) {
        return ResponseModel.fail("");
    }
}
