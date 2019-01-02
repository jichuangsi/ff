package cn.com.fintheircing.customer.user.service.Impl;

import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.service.feign.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
    IUserInfoRepository iUserInfoRepository;
    @Override
    public UserClientInfo getUserClientInfoByPhone(String userName) {
        return iUserInfoRepository.findOneByUserName(userName);

    }
}
