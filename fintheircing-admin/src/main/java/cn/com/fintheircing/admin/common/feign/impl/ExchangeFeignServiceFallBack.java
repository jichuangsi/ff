package cn.com.fintheircing.admin.common.feign.impl;

import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.model.BuyOrderRequestModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import org.springframework.stereotype.Service;

@Service
public class ExchangeFeignServiceFallBack implements IExchangeFeignService {

    @Override
    public ResponseModel<String> sendBuyOrder(BuyOrderRequestModel buyOrderRequestModel) {
        return null;
    }
}
