package cn.com.fintheircing.admin.common.feign.impl;


import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.usermanag.model.pay.PayConfigModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;

import cn.com.fintheircing.admin.usermanag.model.ｍes.MesInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerFeignServiceFallBack implements ICustomerFeignService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public List<OnlineUserInfo> findAllInfo() {
        logger.debug("获取在线用户失败");
        return null;
    }

    @Override
    public List<UserTokenInfo> findAllUser() {
        return null;
    }

    @Override
    public boolean outLine(String id) {
        return false;
    }

    @Override
    public List<OnlineUserInfo> findAllRecoding() {
        return null;
    }

    @Override
    public int deleteRecoding(String userId) {
        return 0;
    }

    @Override
    public PayConfigModel getPayConfig() {
        logger.debug("获取第三方支付信息失败");
        return new PayConfigModel();
    }

    /**
     * 获得待确认所有的 信息
     *
     * @return
     */
    @Override
    public ResponseModel recodPayInfo() {
        logger.debug("获得待确认所有的 信息失败");
        return new ResponseModel();
    }

    @Override
    public ResponseModel<String> rudeEndContract(ContractModel model) {
        return ResponseModel.fail("");
    }

    @Override
    public ResponseModel sendMesg(MesInfoModel model) {
        return new ResponseModel();
    }
}
