package cn.com.fintheircing.customer.user.service.Impl;

import cn.com.fintheircing.customer.user.dao.mapper.IUserClientInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.PayConfigModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${custom.pay.url}")
    private String url;
    @Value("${custom.pay.reciveWay}")
    private String reciveWay;
    @Value("${custom.pay.weChatOrAilpay}")
    private String weChatOrAilpay;
    @Override
    public UserClientInfo findOneByUserName(String userName) {
        return userInfoRepository.findOneByUserName(userName);
    }

    @Override
    public String getSaleManId(String id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return userClientInfoMapper.selectSaleMan(params).getInviterId();
    }

    @Override
    public PayConfigModel payForNet(UserTokenInfo userInfo) {
        PayConfigModel model =new PayConfigModel();
        model.setPhone(userInfo.getPhone());
        model.setUserId(userInfo.getUuid());
        model.setUserName(userInfo.getUserName());
        model.setWay(url);
        return model;
    }
}
