package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.common.feign.impl.StockPriceFeignServiceFallBack;
import cn.com.fintheircing.admin.common.feign.model.GetQuotesTenListRequestModel;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "ffstockprice", fallback = StockPriceFeignServiceFallBack.class)
public interface IStockPriceFeignService {

    @PostMapping("/getQuotesTenList")
    public ResponseModel<List<QuotesTenModel>> getQuotesTenList(@RequestBody GetQuotesTenListRequestModel model);

    @PostMapping("/getQuotesTenListTest")
    public ResponseModel<List<QuotesTenModel>> getQuotesTenListTest();
}
