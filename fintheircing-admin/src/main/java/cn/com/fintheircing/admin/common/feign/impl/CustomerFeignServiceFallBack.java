package cn.com.fintheircing.admin.common.feign.impl;


import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFeignServiceFallBack implements ICustomerFeignService {

    @Override
    public List<OnlineUserInfo> findAllInfo() {
        return null;
    }

    @Override
    public List<UserTokenInfo> findAllUser() {
        return null;
    }

    @Override
    public boolean outLine(String id) {
        return false;
    }

    @Override
    public List<OnlineUserInfo> findAllRecoding(OnlineUserInfo userInfo) {
        return null;
    }

    @Override
    public int deleteRecoding(String userId) {
        return 0;
    }
}
