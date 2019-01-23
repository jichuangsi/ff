package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.common.feign.impl.ExchangeFeignServiceFallBack;
import cn.com.fintheircing.admin.common.feign.model.*;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "ffexchange",fallback = ExchangeFeignServiceFallBack.class)
public interface IExchangeFeignService {

    @RequestMapping(value = "/sendBuyOrder")
    public ResponseModel<String> sendBuyOrder(@RequestBody BuyOrderRequestModel buyOrderRequestModel);

    @RequestMapping(value = "/sendSellOrder")
    public ResponseModel<String> sendSellOrder(@RequestBody SellOrderRequestModel sellOrderRequestModel);

    @RequestMapping(value = "/getTodayOrderList/{motnerAccount}")
    public ResponseModel<List<TodayOrder>> getTodayOrderList(@PathVariable(value = "motnerAccount") String motnerAccount);

    @RequestMapping(value = "/cancelOrder")
    public ResponseModel<String> cancelOrder( @RequestBody CancleOrderRequestModel cancleOrderRequestModel);

    @RequestMapping(value = "/getCanCancleOrderList/{motnerAccount}")
    public ResponseModel<List<CanCancleOrder>> getCanCancleOrderList(@PathVariable("motnerAccount") String motnerAccount);


    @RequestMapping(value = "/getTodayAcceptOrderList/{motnerAccount}")
    public ResponseModel<List<TodayAcceptOrder>> getTodayAcceptOrderList(@PathVariable("motnerAccount") String motnerAccount);

}
