package cn.com.fintheircing.customer.user.service.Impl;

import cn.com.fintheircing.customer.user.dao.mapper.IUserClientInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    IUserInfoRepository userInfoRepository;
    @Resource
    IUserClientInfoMapper userClientInfoMapper;


    @Override
    public UserClientInfo getUserClientInfoByPhone(String userName) {
        return userInfoRepository.findOneByUserName(userName);
    }

    @Override
    public String getSaleManId(String id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return userClientInfoMapper.selectSaleMan(params).getInviterId();
    }
}
