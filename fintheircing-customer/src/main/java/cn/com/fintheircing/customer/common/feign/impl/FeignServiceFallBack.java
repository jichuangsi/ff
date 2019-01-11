package cn.com.fintheircing.customer.common.feign.impl;

import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.feign.model.CreateTodoTaskModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.common.model.RoleModel;
import cn.com.fintheircing.customer.user.model.SpreadModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeignServiceFallBack implements IAdminFeignService {
    Logger logger = LoggerFactory.getLogger(getClass());




    @Override
    public ResponseModel<Object> createRegTodoTask(CreateTodoTaskModel model) {
        return new ResponseModel<Object>("", ResultCode.SYS_BUSY, ResultCode.SYS_BUSY_MSG, null);
    }

    @Override
    public Boolean isExistBlackList(String ip) {
        logger.error(ResultCode.SYS_BUSY_BLACK);
        return true;
    }

    @Override
    public List<RoleModel> getRoles() {
        logger.error("调用feign失败，获取所有角色失败");
        return new ArrayList<>();
    }

    @Override
    public String getInviteId(String inviteCode) {
        logger.error("调用feign失败，获取邀请人id失败");
        return "";
    }

    @Override
    public Boolean canBuy(Integer productNo, String userId) {
        logger.error("调用feign失败，调用查看购买种类失败");
        return false;
    }

    @Override
    public Boolean saveContract(ContractModel model) {
        logger.error("调用feign失败，保存合约失败");
        return false;
    }

    @Override
    public Boolean saveUserSpread(UserTokenInfo userInfo) {
        logger.error("调用feign失败，保存用户推广页失败");
        return false;
    }

  /*  @Override
    public Boolean existContractNum(String contractNum) {
        return false;
    }*/

    @Override
    public SpreadModel getOwnSpread(String userId) {
        logger.error("调用feign失败，未获取用户推广页面");
        return new SpreadModel();
    }

    @Override
    public List<ContractModel> getCurrentContract(String userId) {
        logger.error("调用feign失败，未获取合约列表");
        return new ArrayList<>();
    }

    @Override
    public List<ProductModel> getProductModels(Integer productNo) {
        logger.error("调用feign失败，未获取product列表");
        return new ArrayList<>();
    }

    @Override
    public ProductModel getProduct(String productId) {
        logger.error("调用feign失败，获取product失败");
        return null;
    }

    @Override
    public Boolean isExistWhiteList(String stockNum) {
        logger.error("调用feign失败，不能判断是否存在白名单");
        return false;
    }

    @Override
    public Boolean costColdContract(String contractId, Double coldMoney) {
        logger.error("修改合约冻结资金失败");
        return false;
    }
}
