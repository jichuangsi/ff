package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.common.feign.impl.StockPriceFeignServiceFallBack;
import cn.com.fintheircing.admin.common.feign.model.GetQuotesTenListRequestModel;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "ffstockprice",fallback = StockPriceFeignServiceFallBack.class)
public interface IStockPriceFeignService {

    @RequestMapping("/getQuotesTenList")
    public ResponseModel<List<QuotesTenModel>> getQuotesTenList(@RequestBody GetQuotesTenListRequestModel model);
}
