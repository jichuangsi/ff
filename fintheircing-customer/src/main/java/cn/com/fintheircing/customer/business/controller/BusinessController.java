package cn.com.fintheircing.customer.business.controller;

import cn.com.fintheircing.customer.business.exception.BusinessException;
import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.model.tranfer.TranferProductModel;
import cn.com.fintheircing.customer.business.service.BusinessService;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.model.StockEntrustModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
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
public class BusinessController {

    @Resource
    private BusinessService businessService;


    @ApiOperation(value = "获取产品列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getProducts")
    public ResponseModel<TranferProductModel> getProducts(@ModelAttribute UserTokenInfo userInfo, @RequestBody ProductModel model){
        try {
            return ResponseModel.sucess("", businessService.getTranferProductModel(userInfo,model));
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "验证并保存合约", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveContract")
    public ResponseModel saveContract(@ModelAttribute UserTokenInfo userInfo, @Validated @RequestBody ContractModel model){
        if (model.getPromisedMoney()<2000||model.getPromisedMoney()>3000000){
            return ResponseModel.fail("",ResultCode.ACCOUNT_NUM_ERR);
        }
        try {
            businessService.saveContract(userInfo,model);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "判断是否金额充足", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/isRich")
    public ResponseModel isRich(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractModel model){
        if (model.getPromisedMoney()<2000||model.getPromisedMoney()>3000000){
            return ResponseModel.fail("",ResultCode.ACCOUNT_NUM_ERR);
        }
        if (!businessService.isRich(userInfo,model)){
            return ResponseModel.fail("",ResultCode.ACCOUNT_LESS_ERR);
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "获取该用户的合约详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getCurrentContract")
    public ResponseModel<List<ContractModel>> getCurrentContract(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",businessService.getCurrentContract(userInfo));
    }


    @ApiOperation(value = "提交购买股票申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveStockEntrust")
    public ResponseModel saveStockEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model){
        try {
            businessService.saveEntrust(userInfo,model);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }


}
