package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;

public interface UserService {
    UserClientInfo getUserClientInfoByPhone(String userName);

    String getSaleManId(String id);
}
