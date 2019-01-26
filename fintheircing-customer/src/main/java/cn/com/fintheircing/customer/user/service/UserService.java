package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.PayConfigModel;
import cn.com.fintheircing.customer.user.model.UserInfoModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;

public interface UserService {
    UserClientInfo findOneByUserName(String userName);

    String getSaleManId(String id);

    /**
     * 网关支付
     * @param userInfo
     * @return
     */
    PayConfigModel payForNet(UserTokenInfo userInfo);


    UserInfoModel getUserInfo(UserTokenInfo userInfo);

    /**
     * 记录并且修改余额
     * @param model
     * @return
     */
     boolean addOrUseMoney(RecodeInfoPayModel model);
}
