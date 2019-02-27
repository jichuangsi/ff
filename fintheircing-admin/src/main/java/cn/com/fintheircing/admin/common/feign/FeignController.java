package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.business.synchronize.SynchronizeComponent;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.RoleModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.login.service.AdminLoginService;
import cn.com.fintheircing.admin.promisedUrls.model.TranferUrlModel;
import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import cn.com.fintheircing.admin.proxy.exception.ProxyException;
import cn.com.fintheircing.admin.proxy.model.SpreadModel;
import cn.com.fintheircing.admin.proxy.service.ProxyService;
import cn.com.fintheircing.admin.system.service.SystemService;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.pay.AppQueryModel;
import cn.com.fintheircing.admin.usermanag.model.pay.NetQueryModel;
import cn.com.fintheircing.admin.usermanag.model.pay.PayConfigModel;
import cn.com.fintheircing.admin.usermanag.model.pay.ResultModel;
import cn.com.fintheircing.admin.usermanag.model.result.AppResultModel;
import cn.com.fintheircing.admin.usermanag.service.IPayService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("开放feign")
@RequestMapping("/adminF")
public class FeignController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UrlService urlService;
    @Resource
    private AdminLoginService adminLoginService;
    @Resource
    private SystemService systemService;
    @Resource
    private BusinessService businessService;
    @Resource
    private ProxyService proxyService;
    @Resource
    private IDistributService distributService;
    @Resource
    private ItemService temService;
    @Resource
    private ICustomerFeignService iCustomerFeignService;
    @Resource
    private IPayService iPayService;
    @Resource
    private SynchronizeComponent synchronizeComponent;

    @ApiOperation(value = "判断是否是允许的url", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/IsPromisedUrls")
    public Boolean IsPromisedUrls(@RequestBody TranferUrlModel model) {
        if (StringUtils.isEmpty(model.getUrl())) {
            return false;
        }
        return urlService.checkUrl(model);
    }

    @ApiOperation(value = "判断是否IP存在黑名单", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/isExistBlackList")
    public Boolean isExistBlackList(@RequestParam("ip") String ip) {
        return systemService.isExistBlackList(ip);
    }

    @ApiOperation(value = "传递role角色", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getRoles")
    public List<RoleModel> getRoles() {
        return systemService.getRoles();
    }

    @ApiOperation(value = "传递product信息", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getProducts")
    public List<ProductModel> getProducts(@RequestParam("productNo") Integer productNo) {
        return distributService.getProducts(productNo);
    }

    @ApiOperation(value = "获取邀请人id", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getInviteId")
    public String getInvitId(@RequestParam("inviteCode") String inviteCode) {
        return proxyService.getInviteId(inviteCode);
    }

    @ApiOperation(value = "判断是否可购买", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/canBuy")
    public Boolean canBuy(@RequestParam("productNo") Integer productNo, @RequestParam("userId") String userId) {
        return businessService.canBuy(productNo, userId);
    }

    @ApiOperation(value = "保存合约，和创立合约操作", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/saveContract")
    public ResponseModel<String> saveContract(@RequestBody ContractModel model) {
        String response = "";
        try {
            response = synchronizeComponent.synchronizedSaveContract(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucess("", response);
    }

    @ApiOperation(value = "新建用户的推广", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/saveUserSpread")
    public ResponseModel<String> saveUserSpread(@RequestBody UserTokenInfo userInfo) throws ProxyException {
        try {
            return ResponseModel.sucess("",proxyService.saveUserSpread(userInfo));
        } catch (ProxyException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "获取当前用户的推广", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getOwnSpread")
    public SpreadModel getOwnSpread(@RequestParam("userId") String userId) {
        return proxyService.getOwnSpread(userId);
    }

    @ApiOperation(value = "获取当前用户的当前合约", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getCurrentContract")
    public List<ContractModel> getCurrentContract(@RequestParam("userId") String userId) {
        return businessService.getCurrentContract(userId);
    }

    @ApiOperation(value = "获取挑选的产品", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getProduct")
    public ProductModel getProduct(@RequestParam("productId") String productId) {
        return distributService.getProduct(productId);
    }

    @ApiOperation(value = "判断是否存在白名单内", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/isExistWhiteList")
    public Boolean isExistWhiteList(@RequestParam("stockNum") String stockNum) {
        return temService.isExistWhiteList(stockNum);
    }

    @ApiOperation(value = "保存申请单，合约扣款（冻结资金）", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/saveStockEntrust")
    public ResponseModel<String> saveStockEntrust(@RequestBody StockEntrustModel model) {
        try {
            synchronizeComponent.synchronizedCostColdContract(model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @RequestMapping("/showPayInfo")
    @ApiOperation(value = "展示第三方支付网关信息", notes = "")
    public ResultModel getWayToPay(@RequestBody NetQueryModel model) throws UserServiceException {
        PayConfigModel payConfig = iCustomerFeignService.getPayConfig();
        return iPayService.getWayToPay(model, payConfig);
    }

    @RequestMapping("/payForQRCode")
    @ApiOperation(value = "展示第三方支付二维码信息", notes = "")
    public AppResultModel payForQRCode(@RequestBody AppQueryModel model) throws UserServiceException {
        PayConfigModel payConfig = iCustomerFeignService.getPayConfig();
        return iPayService.payForQRCode(model, payConfig);
    }

    @ApiOperation(value = "获取当前持仓", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getCurrentHolding")
    public List<StockHoldingModel> getCurrentHolding(@RequestBody StockHoldingModel model) {
        return businessService.getCurrentHolding(model);
    }

    @ApiOperation(value = "卖出持仓股份", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/sellHoldStockEntrust")
    public ResponseModel<String> sellHoldStockEntrust(@RequestBody StockHoldingModel model) {
        try {
            synchronizeComponent.synchronizedSellHoldStock(model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "获取未完成订单", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getUnFinishedEntrust")
    public List<StockEntrustModel> getUnFinishedEntrust(@RequestBody ContractModel model) {
        return businessService.getUnFinishedEntrust(model);
    }

    @ApiOperation(value = "申请表撤单", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/entrustCancelOrder")
    public ResponseModel<String> entrustCancelOrder(@RequestBody StockEntrustModel model) {
        try {
            synchronizeComponent.synchronizedCancelOrder(model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "获取最大的购买量", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getMaxBuyAmount")
    public StockHoldingModel getMaxBuyAmount(@RequestBody StockHoldingModel model) {
        return businessService.getMaxBuyAmount(model);
    }


  /*  @ApiOperation(value = "获取最大的购买量", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/testFeign")
    public void testFeign() throws Exception{
        Thread.sleep(5000);
        System.out.println(1111);
    }*/

    @ApiOperation(value = "获取当前用户的交易信息", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getBusinessInfo")
    public ContractModel getBusinessInfo(@RequestBody UserTokenInfo userInfo) {
        return businessService.getBusinessInfo(userInfo);
    }

    @ApiOperation(value = "结束选中的合约", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/endContract")
    public ResponseModel<ContractModel> endContract(@RequestBody ContractModel model) {
        try {
            return ResponseModel.sucess("", synchronizeComponent.synchronizedEndContract(model));
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "获取分页委托", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getContractEntrusts")
    public ResponseModel<PageInfo<StockEntrustModel>> getContractEntrusts(@RequestBody StockEntrustModel model){
        try {
            return ResponseModel.sucess("",businessService.getContractEntrusts(model));
        } catch (Exception e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }
}
