package cn.com.fintheircing.customer.business.service.feign;

import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.service.feign.impl.BusinessFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "ffadmin",fallback = BusinessFeignServiceImpl.class)
public interface IBusinessFeignService {


    @RequestMapping("/adminF/getProduct")
    ProductModel getProductModel(@RequestBody ProductModel model);
}
