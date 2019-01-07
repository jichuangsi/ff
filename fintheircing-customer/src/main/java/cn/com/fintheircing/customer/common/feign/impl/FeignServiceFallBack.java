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
    public List<ProductModel> getProductModel(Integer productNo) {
        logger.error("调用feign失败，未获取product");
        return new ArrayList<>();
    }

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
        return new ArrayList<>();
    }

    @Override
    public String getInviteId(String inviteCode) {
        return "";
    }

    @Override
    public Boolean canBuy(Integer productNo, String userId) {
        return false;
    }

    @Override
    public Boolean saveContract(ContractModel model) {
        return false;
    }

    @Override
    public Boolean saveUserSpread(UserTokenInfo userInfo) {
        return false;
    }

  /*  @Override
    public Boolean existContractNum(String contractNum) {
        return false;
    }*/

    @Override
    public SpreadModel getOwnSpread(String userId) {
        return new SpreadModel();
    }
}
