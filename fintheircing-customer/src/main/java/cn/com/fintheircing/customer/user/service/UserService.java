package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.*;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/addOrUseMoney")
     boolean addOrUseMoney(RecodeInfoPayModel model);

    void updatePass(UserTokenInfo userInfo, PassWordModel model) throws LoginException;

    void validatePass(UserTokenInfo userInfo,PassWordModel model) throws LoginException;

    void userCer(UserTokenInfo userInfo, UserCerModel model) throws LoginException;

}
