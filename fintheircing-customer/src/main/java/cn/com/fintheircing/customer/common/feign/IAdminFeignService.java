package cn.com.fintheircing.customer.common.feign;

import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.common.feign.impl.FeignServiceFallBack;
import cn.com.fintheircing.customer.common.feign.model.CreateTodoTaskModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.common.model.RoleModel;
import cn.com.fintheircing.customer.user.model.SpreadModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
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
    Boolean canBuy(@RequestParam("productNo") Integer productNo,@RequestParam("userId") String userId);


    //创建合约
    @RequestMapping(value = "/adminF/saveContract")
    Boolean saveContract(@RequestBody ContractModel model);


    //创建用户的推广
    @RequestMapping(value = "/adminF/saveUserSpread")
    Boolean saveUserSpread(@RequestBody UserTokenInfo userInfo);

    /*//判断是否存在相同合约编号
    @RequestMapping(value = "/adminF/existContractNum")
    Boolean existContractNum(@RequestParam("contractNum") String contractNum);*/

    @RequestMapping(value = "/adminF/getOwnSpread")
    SpreadModel getOwnSpread(@RequestParam("userId") String userId);

    @RequestMapping(value = "/adminF/getCurrentContract")
    List<ContractModel> getCurrentContract(@RequestParam("userId") String userId);

    @RequestMapping(value = "/adminF/getProduct")
    ProductModel getProduct(@RequestParam("productId") String productId);

    @RequestMapping(value = "adminF/isExistWhiteList")
    Boolean isExistWhiteList(@RequestParam("stockNum") String stockNum);
}
