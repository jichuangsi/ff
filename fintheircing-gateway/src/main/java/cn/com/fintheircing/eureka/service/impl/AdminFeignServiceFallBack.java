package cn.com.fintheircing.eureka.service.impl;

import cn.com.fintheircing.eureka.service.IAdminFeignService;
import org.springframework.stereotype.Component;

@Component
public class AdminFeignServiceFallBack implements IAdminFeignService {
    @Override
    public Boolean checkPromisedUrl(TranferUrlModel model) {
        return false;
    }
}
