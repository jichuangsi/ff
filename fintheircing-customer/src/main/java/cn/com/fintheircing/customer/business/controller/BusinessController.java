package cn.com.fintheircing.customer.business.controller;

import cn.com.fintheircing.customer.business.exception.BusinessException;
import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.model.StockHoldingModel;
import cn.com.fintheircing.customer.business.model.tranfer.TranferProductModel;
import cn.com.fintheircing.customer.business.service.BusinessService;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.model.StockEntrustModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/business")
@Api("用户关于商业上操作")
@CrossOrigin
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @ApiOperation(value = "获取产品列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getProducts")
    public ResponseModel<TranferProductModel> getProducts(@ModelAttribute UserTokenInfo userInfo, @RequestBody ProductModel model) {
        try {
            return ResponseModel.sucess("", businessService.getTranferProductModel(userInfo, model));
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "验证并保存合约", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveContract")
    public ResponseModel saveContract(@ModelAttribute UserTokenInfo userInfo, @Validated @RequestBody ContractModel model) {
        if (model.getPromisedMoney() < 2000 || model.getPromisedMoney() > 3000000) {
            return ResponseModel.fail("", ResultCode.ACCOUNT_NUM_ERR);
        }
        try {
            businessService.saveContract(userInfo, model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "判断是否金额充足", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/isRich")
    public ResponseModel isRich(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractModel model) {
        if (model.getPromisedMoney() < 2000 || model.getPromisedMoney() > 3000000) {
            return ResponseModel.fail("", ResultCode.ACCOUNT_NUM_ERR);
        }
        if (!businessService.isRich(userInfo, model)) {
            return ResponseModel.fail("", ResultCode.ACCOUNT_LESS_ERR);
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "获取该用户的合约详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getCurrentContract")
    public ResponseModel<List<ContractModel>> getCurrentContract(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("", businessService.getCurrentContract(userInfo));
    }

    @ApiOperation(value = "提交购买股票申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveStockEntrust")
    public ResponseModel saveStockEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) {
        try {
            businessService.saveEntrust(userInfo, model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "根据股票代码查询当前持仓", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getCurrentHolding")
    public ResponseModel<List<StockHoldingModel>> getCurrentHolding(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockHoldingModel model) {
        try {
            return ResponseModel.sucess("", businessService.getCurrentStockHolding(userInfo, model));
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "卖出持有股申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/sellHoldStockEntrust")
    public ResponseModel sellHoldStockEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockHoldingModel model) {
        try {
            businessService.sellHoldStockEntrust(userInfo, model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查询未完成的申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getUnfinishedEtrust")
    public ResponseModel<List<StockEntrustModel>> getUnfinishedEtrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractModel model) {
        return ResponseModel.sucess("", businessService.getUnfinishedEntrust(userInfo, model));
    }

    @ApiOperation(value = "撤单(仅可撤未处理和已报，待处理不让撤)", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateEntrustCancelOrder")
    public ResponseModel updateEntrustCancelOrder(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) {
        try {
            businessService.cancelOrder(userInfo, model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "获取最大买入值", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getMaxBuyAmount")
    public ResponseModel<StockHoldingModel> getMaxBuyAmount(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockHoldingModel model) {
        try {
            return ResponseModel.sucess("", businessService.getMaxBuyAmount(userInfo, model));
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "获取当前用户的交易详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getBusinessInfo")
    public ResponseModel<ContractModel> getBusinessInfo(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("", businessService.getBusinessInfo(userInfo));
    }

    @ApiOperation(value = "结算合约", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/endContract")
    public ResponseModel endContract(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractModel model) {
        try {
            return ResponseModel.sucess("","当前结余："+businessService.endContract(userInfo, model));
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "查委托，成交", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getContractEntrusts")
    public ResponseModel<PageInfo<StockEntrustModel>> getContractEntrusts(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model){
        try {
            return ResponseModel.sucess("",
                    businessService.getContractEntrusts(userInfo,model));
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }
}
