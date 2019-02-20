package cn.com.fintheircing.admin.business.controller;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferEntrustModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferHoldingModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferStockEntrustModel;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.business.synchronize.SynchronizeComponent;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import com.github.pagehelper.PageInfo;
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
@CrossOrigin
public class BusinessController {
    @Resource
    private BusinessService businessService;
    @Resource
    private SynchronizeComponent synchronizeComponent;

    @ApiOperation(value = "修改买入申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateBuyEntrust")
    public ResponseModel updateBuyEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) {
        try {
            synchronizeComponent.synchronizedDealStockHand(userInfo, model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改申请单状态，返回母账号，买卖都用", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/checkEntrust")
    public ResponseModel<Map<String, String>> checkEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) {
        try {
            return ResponseModel.sucess("", synchronizeComponent.synchronizedCheckEntrust(userInfo, model));
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "修改卖出申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateSellEntrust")
    public ResponseModel updateSellEntrust(@ModelAttribute UserTokenInfo userInfo,
                                           @RequestBody StockEntrustModel model) {
        try {
            synchronizeComponent.synchronizedDealSellHand(userInfo, model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "展示所有申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getPageEntrust")
    public ResponseModel<TranferStockEntrustModel> getPageEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) {
        return ResponseModel.sucess("", businessService.getPageEntrust(model));
    }

    @ApiOperation(value = "修改自动或手动", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/setAutoSystem")
    public ResponseModel<Map<String,String>> setAutoSystem(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("",businessService.setAutoSystem(userInfo));
    }

    @ApiOperation(value = "修改撤单申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateBackEntrust")
    public ResponseModel updateBackEntrust(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) {
        try {
            synchronizeComponent.synchronizedDealBackHand(userInfo, model);
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "返回当前操作状态", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getAuto")
    public ResponseModel<Map<String,String>> getAuto(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",businessService.getAuto());
    }

    @ApiOperation(value = "查询委托相关信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getSystemEntrusts")
    public ResponseModel<PageInfo<TranferEntrustModel>> getSystemEntrusts(@ModelAttribute UserTokenInfo userInfo,@RequestBody TranferEntrustModel model){
        try {
            return ResponseModel.sucess("",businessService.getSystemEntrusts(model));
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "获取委托相关查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getEntrustCondition")
    public ResponseModel<Map<String,Object>> getEntrustCondition(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",businessService.getEntrustCondition());
    }

    @ApiOperation(value = "获取持仓数据", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getHoldings")
    public ResponseModel<PageInfo<TranferHoldingModel>> getHoldings(@ModelAttribute UserTokenInfo userInfo,@RequestBody TranferHoldingModel model){
        return ResponseModel.sucess("",businessService.getPageHolding(model));
    }
}
