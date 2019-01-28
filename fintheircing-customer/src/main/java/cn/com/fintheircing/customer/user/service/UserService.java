package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.PayConfigModel;
import cn.com.fintheircing.customer.user.model.UserInfoModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.promise.AddPromiseMoneyModel;
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

    /**
     * 申请扩大融资
     * @param model
     * @return
     */
    boolean expandMoney(AddPromiseMoneyModel model);
}
