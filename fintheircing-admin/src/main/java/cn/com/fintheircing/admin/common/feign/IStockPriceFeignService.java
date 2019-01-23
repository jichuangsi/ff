package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.common.feign.impl.StockPriceFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "ffstockprice",fallback = StockPriceFeignServiceFallBack.class)
public interface IStockPriceFeignService {
}
