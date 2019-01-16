package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.common.feign.impl.ExchangeFeignServiceFallBack;
import cn.com.fintheircing.admin.common.feign.model.BuyOrderRequestModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "ffexchange",fallback = ExchangeFeignServiceFallBack.class)
public interface IExchangeFeignService {

    @RequestMapping("/sendBuyOrder")
    public ResponseModel<String> sendBuyOrder(@RequestBody BuyOrderRequestModel buyOrderRequestModel);
}
