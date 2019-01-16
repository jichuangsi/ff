package cn.com.fintheircing.admin.business.controller;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
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


    @ApiOperation(value = "修改申请单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateEntrust")
    public ResponseModel updateEntrust(@ModelAttribute UserTokenInfo userInfo,@RequestBody StockEntrustModel model){
        try {
            businessService.dealStockHand(userInfo,model);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改申请单状态，返回母账号", notes = "")
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


}
