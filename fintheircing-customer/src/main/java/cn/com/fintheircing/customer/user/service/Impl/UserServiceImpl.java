package cn.com.fintheircing.customer.user.service.Impl;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IUserClientInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserAccount;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.PayConfigModel;
import cn.com.fintheircing.customer.user.model.UserInfoModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.service.UserService;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    IUserInfoRepository userInfoRepository;
    @Resource
    IUserClientInfoMapper userClientInfoMapper;
    @Resource
    private IRecodInfoPayRepository iRecodInfoPayRepository;
    @Resource
    private IUserAccountRepository iUserAccountRepository;
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return userClientInfoMapper.selectSaleMan(params).getInviterId();
    }

    @Override
    public PayConfigModel payForNet(UserTokenInfo userInfo) {
        PayConfigModel model = new PayConfigModel();
        model.setPhone(userInfo.getPhone());
        model.setUserId(userInfo.getUuid());
        model.setUserName(userInfo.getUserName());
        model.setWay(url);
        return model;
    }

    @Override
    public UserInfoModel getUserInfo(UserTokenInfo userInfo) {
        UserInfoModel model = new UserInfoModel();
        model.setUserName(userInfo.getUserName());
        model.setUserId(userInfo.getUuid());
        model.setPhone(userInfo.getPhone());
        model.setAccount(iUserAccountRepository.findAccountByUserId(userInfo.getUuid()));
        return model;
    }

    /**
     * 记录并且修改余额
     *
     * @param model
     * @return
     */
    @Override
    public boolean addOrUseMoney(RecodeInfoPayModel model) {
        if (model.getCostCount() > iUserAccountRepository.findAccountByUserId(model.getUserId())) {
            return false;
        } else {
            UserAccount byId = iUserAccountRepository.findOneByUserId(model.getUserId());
            if (!StringUtils.isEmpty(byId)) {
                //扣款
                byId.setAccount(byId.getAccount() - model.getCostCount());
                iUserAccountRepository.save(byId);
                // 记录
                iRecodInfoPayRepository.save(Model2Entity.UpdateRecodeInfoPayModel(model));
                return true;
            } else {
                return false;
            }


        }
    }
}