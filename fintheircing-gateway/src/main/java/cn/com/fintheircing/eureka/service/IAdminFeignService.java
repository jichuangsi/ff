package cn.com.fintheircing.eureka.service;

import cn.com.fintheircing.eureka.model.TranferUrlModel;
import cn.com.fintheircing.eureka.service.impl.AdminFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="ffadmin",fallback = AdminFeignServiceFallBack.class)
public interface IAdminFeignService {

    @RequestMapping("/IsPromisedUrls")
    public Boolean checkPromisedUrl(@RequestBody TranferUrlModel model);
}
