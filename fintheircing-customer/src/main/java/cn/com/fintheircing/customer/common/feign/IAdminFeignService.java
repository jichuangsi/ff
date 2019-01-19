package cn.com.fintheircing.customer.common.feign;

import cn.com.fintheircing.customer.business.model.ContractModel;
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
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodInfoPayModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.ResultModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ffadmin",fallback = FeignServiceFallBack.class)
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
    Boolean saveContract(@RequestBody ContractModel model);


    //创建用户的推广
    @RequestMapping(value = "/adminF/saveUserSpread")
    Boolean saveUserSpread(@RequestBody UserTokenInfo userInfo);


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
     * 返回一个第三方网关接口
     * @param
     * @return
     */
    @RequestMapping(value = "adminF/getWayToPay")
    ResultModel getWayToPay();

    /**
     * 返回二维码支付地址
     * @return
     */
    @RequestMapping("adminF/payForQRCode")
    AppResultModel payForQRCode();

    /**
     * 返回更新用户信息
     * @param model
     * @return
     */
    @RequestMapping("adminF/updatePayInfo")
    RecodInfoPayModel updatePayInfo(@RequestBody RecodInfoPayModel model);

    //保存股票申请
    @RequestMapping(value = "adminF/saveStockEntrust")
    ResponseModel<String> saveStockEntrust(@RequestBody StockEntrustModel model);


    //查看当前合约的该股持仓
    @RequestMapping(value = "adminF/getCurrentHolding")
    StockHoldingModel getCurrentHolding(@RequestBody StockHoldingModel model);


    //卖出持仓
    @RequestMapping(value = "adminF/sellHoldStockEntrust")
    Boolean sellHoldStockEntrust(@RequestBody StockHoldingModel model);

    @RequestMapping(value = "adminF/getUnFinishedEntrust")
    List<StockEntrustModel> getUnFinishedEntrust(@RequestBody ContractModel model);

    @RequestMapping(value = "adminF/entrustCancelOrder")
    ResponseModel entrustCancelOrder(@RequestBody StockEntrustModel model);

}
