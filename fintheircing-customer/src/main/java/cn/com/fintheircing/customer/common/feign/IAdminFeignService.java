package cn.com.fintheircing.customer.common.feign;

import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.FlowModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.model.StockHoldingModel;
import cn.com.fintheircing.customer.common.feign.impl.FeignServiceFallBack;
import cn.com.fintheircing.customer.common.feign.model.CreateTodoTaskModel;
import cn.com.fintheircing.customer.common.feign.model.StockEntrustModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.common.model.RoleModel;
import cn.com.fintheircing.customer.user.model.SpreadModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.AppResultModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.PayInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.ResultModel;
import cn.com.fintheircing.customer.user.model.queryModel.AppQueryModel;
import cn.com.fintheircing.customer.user.model.queryModel.NetQueryModel;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ffadmin", fallback = FeignServiceFallBack.class)
public interface IAdminFeignService {

    //获取所有该种类产品
    @RequestMapping("/adminF/getProducts")
    List<ProductModel> getProductModels(@RequestParam("productNo") Integer productNo);

    //创建事务
    @RequestMapping(value = "/todoTask/createRegTodoTask")
    ResponseModel<Object> createRegTodoTask(CreateTodoTaskModel model);

    //是否存在黑名单
    @RequestMapping(value = "/adminF/isExistBlackList")
    Boolean isExistBlackList(@RequestParam("ip") String ip);

    //查出所有角色人
    @RequestMapping(value = "/adminF/getRoles")
    List<RoleModel> getRoles();

    //获得邀请人
    @RequestMapping(value = "/adminF/getInviteId")
    String getInviteId(@RequestParam("inviteCode") String inviteCode);

    //查看是否能购买相同套餐
    @RequestMapping(value = "/adminF/canBuy")
    Boolean canBuy(@RequestParam("productNo") Integer productNo, @RequestParam("userId") String userId);

    //创建合约
    @RequestMapping(value = "/adminF/saveContract")
    ResponseModel<String> saveContract(@RequestBody ContractModel model);

    //创建用户的推广
    @RequestMapping(value = "/adminF/saveUserSpread")
    ResponseModel<String> saveUserSpread(@RequestBody UserTokenInfo userInfo);


    /*//判断是否存在相同合约编号
    @RequestMapping(value = "/adminF/existContractNum")
    Boolean existContractNum(@RequestParam("contractNum") String contractNum);*/

    //获取用户推广
    @RequestMapping(value = "/adminF/getOwnSpread")
    SpreadModel getOwnSpread(@RequestParam("userId") String userId);

    //获取当前持有的所有合约
    @RequestMapping(value = "/adminF/getCurrentContract")
    List<ContractModel> getCurrentContract(@RequestParam("userId") String userId);

    //获取当前的产品
    @RequestMapping(value = "/adminF/getProduct")
    ProductModel getProduct(@RequestParam("productId") String productId);

    //判断是否存在白名单内
    @RequestMapping(value = "adminF/isExistWhiteList")
    Boolean isExistWhiteList(@RequestParam("stockNum") String stockNum);

    /**
     * 读取第三方支付配置信息
     *
     * @param
     * @return
     */
    @RequestMapping("/recodPayInfo")
     PayInfoModel recodPayInfo();

    /**
     * 返回二维码支付地址
     *
     * @return
     */
    @RequestMapping("adminF/payForQRCode")
    AppResultModel payForQRCode(@RequestBody AppQueryModel model);

    /**
     * 返回更新用户信息
     *
     * @param model
     * @return
     */
    @RequestMapping("adminF/updatePayInfo")
    RecodeInfoPayModel updatePayInfo(@RequestBody RecodeInfoPayModel model);

    //保存股票申请
    @RequestMapping(value = "adminF/saveStockEntrust")
    ResponseModel<String> saveStockEntrust(@RequestBody StockEntrustModel model);

    //查看当前合约的该股持仓
    @RequestMapping(value = "adminF/getCurrentHolding")
    List<StockHoldingModel> getCurrentHolding(@RequestBody StockHoldingModel model);

    //卖出持仓
    @RequestMapping(value = "adminF/sellHoldStockEntrust")
    ResponseModel<String> sellHoldStockEntrust(@RequestBody StockHoldingModel model);

    @RequestMapping(value = "adminF/getUnFinishedEntrust")
    List<StockEntrustModel> getUnFinishedEntrust(@RequestBody ContractModel model);

    @RequestMapping(value = "adminF/entrustCancelOrder")
    ResponseModel<String> entrustCancelOrder(@RequestBody StockEntrustModel model);

    @RequestMapping(value = "adminF/getMaxBuyAmount")
    StockHoldingModel getMaxBuyAmount(@RequestBody StockHoldingModel model);

    /* @RequestMapping(value = "adminF/testFeign")
     void testFeign();*/
    @RequestMapping(value = "adminF/getBusinessInfo")
    ContractModel getBusinessInfo(UserTokenInfo userTokenInfo);

    @RequestMapping(value = "adminF/endContract")
    ResponseModel<ContractModel> endContract(@RequestBody ContractModel model);

    @RequestMapping(value = "adminF/getContractEntrusts")
    ResponseModel<PageInfo<StockEntrustModel>> getContractEntrusts(@RequestBody StockEntrustModel model);

    @RequestMapping("adminF/endContractAndSell")
    ResponseModel endContractAndSell(@RequestParam("userId") String userId,@RequestParam("contractId")String contractId);

    @RequestMapping("adminF/getMoneyFlow")
    ResponseModel<PageInfo<FlowModel>> getMoneyFlow(@RequestParam("contractId") String contractId,@RequestParam("index") int index,@RequestParam("size") int size);

}
