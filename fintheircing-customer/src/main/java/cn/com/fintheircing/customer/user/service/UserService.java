package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.exception.AccountPayException;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.*;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.withdraw.WithdrawModel;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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

    /**
     * 修改头像
     * @param base64
     * @param uuid
     */
    boolean setAvatar(Encode64 base64, String uuid) throws IOException;
    UserTokenInfo getUserTokenInfo(String id);

    RecodeInfoPayModel withdrawCash(String uuid, WithdrawModel model) throws AccountPayException;

    /**
     * 增加或者修改支付密码
     * @param uuid
     * @param txPassword
     */
    boolean addOrChangePassword(String uuid, String txPassword) throws AccountPayException;

    /**
     * 检查是否设置支付密码
     * @param uuid
     * @return
     */
    boolean checkTxPassword(String uuid);
}
