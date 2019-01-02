package cn.com.fintheircing.customer.user.service.feign;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;

public interface UserService {
    UserClientInfo getUserClientInfoByPhone(String userName);
}
