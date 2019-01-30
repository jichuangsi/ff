package cn.com.fintheircing.customer.common.feign.impl;

import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.model.StockHoldingModel;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.feign.model.CreateTodoTaskModel;
import cn.com.fintheircing.customer.common.feign.model.StockEntrustModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.common.model.RoleModel;
import cn.com.fintheircing.customer.user.model.SpreadModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.AppResultModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.ResultModel;
import cn.com.fintheircing.customer.user.model.queryModel.AppQueryModel;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeignServiceFallBack implements IAdminFeignService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<StockEntrustModel> getUnFinishedEntrust(ContractModel model) {
        return new ArrayList<>();
    }

    @Override
    public ResponseModel entrustCancelOrder(StockEntrustModel model) {
        return new ResponseModel();
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
    public ResponseModel<String> saveContract(ContractModel model) {
        logger.error("调用feign失败，保存合约失败");
        return ResponseModel.fail("", "保存合约失败");
    }

    @Override
    public ResponseModel<String> saveUserSpread(UserTokenInfo userInfo) {
        logger.error("调用feign失败，保存用户推广页失败");
        return ResponseModel.fail("", "保存用户推广页失败");
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
        return new ProductModel();
    }

    @Override
    public Boolean isExistWhiteList(String stockNum) {
        logger.error("调用feign失败，不能判断是否存在白名单");
        return false;
    }

    @Override
    public ResponseModel<String> saveStockEntrust(StockEntrustModel model) {
        logger.error("调用feign失败，保存买入申请单失败");
        return ResponseModel.fail("", ResultCode.SYS_ERROR_MSG);
    }

    @Override
    public ResultModel getWayToPay() {
        logger.error("读取第三方支付信息失败");
        return null;
    }

    /**
     * 返回二维码支付地址
     *
     * @return
     */
    @Override
    public AppResultModel payForQRCode(AppQueryModel model) {
        logger.error("读取二维码支付地址信息失败");
        return new AppResultModel();
    }

    /**
     * 返回更新用户信息
     *
     * @param model
     * @return
     */
    @Override
    public RecodeInfoPayModel updatePayInfo(RecodeInfoPayModel model) {
        logger.error("从操作员获取更新数据失败");
        return new RecodeInfoPayModel();
    }

    @Override
    public List<StockHoldingModel> getCurrentHolding(StockHoldingModel model) {
        logger.error("调用feign失败,未获取持仓");
        return null;
    }

    @Override
    public ResponseModel<String> sellHoldStockEntrust(StockHoldingModel model) {
        logger.error("调用feign失败，未委托卖出成功");
        return ResponseModel.fail("", "未委托卖出成功");
    }

    @Override
    public StockHoldingModel getMaxBuyAmount(StockHoldingModel model) {
        logger.error("调用feign失败，未获得最大购买量");
        return new StockHoldingModel();
    }

    /*@Override
    public void testFeign() {

    }*/

    @Override
    public ContractModel getBusinessInfo(UserTokenInfo userTokenInfo) {
        logger.error("调用feign失败，未获得合约总交易数据");
        return new ContractModel();
    }

    @Override
    public ResponseModel<String> endContract(ContractModel model) {
        logger.error("调用feign失败，未中止合约");
        return ResponseModel.fail("");
    }

    @Override
    public ResponseModel<PageInfo<StockEntrustModel>> getContractEntrusts(StockEntrustModel model) {
        logger.error("调用feign失败，未获取委托单");
        return ResponseModel.fail("");
    }
}
