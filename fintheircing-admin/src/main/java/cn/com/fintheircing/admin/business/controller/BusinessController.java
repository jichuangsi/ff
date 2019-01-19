package cn.com.fintheircing.admin.business.controller;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferStockEntrustModel;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin/business")
@Api("管理员对business的controller")
public class BusinessController {
    @Resource
    private BusinessService businessService;


    @ApiOperation(value = "修改买入申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateBuyEntrust")
    public ResponseModel updateBuyEntrust(@ModelAttribute UserTokenInfo userInfo,@RequestBody StockEntrustModel model){
        try {
            businessService.dealStockHand(userInfo,model);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改申请单状态，返回母账号，买卖都用", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/checkEntrust")
    public ResponseModel<Map<String,String>> checkEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model){
        try {
           return ResponseModel.sucess("",businessService.checkEntrust(userInfo,model));
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }


    @ApiOperation(value = "修改卖出申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateSellEntrust")
    public ResponseModel updateSellEntrust(@ModelAttribute UserTokenInfo userInfo,
                                           @RequestBody StockEntrustModel model){
        try {
            businessService.dealSellHand(userInfo,model);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "展示所有申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getPageEntrust")
    public ResponseModel<TranferStockEntrustModel> getPageEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model){
        return ResponseModel.sucess("",businessService.getPageEntrust(model));
    }

    @ApiOperation(value = "修改自动或手动", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/setAutoSystem")
    public ResponseModel setAutoSystem(@ModelAttribute UserTokenInfo userInfo){
        businessService.setAutoSystem(userInfo);
        return ResponseModel.sucessWithEmptyData("");
    }


}
