package cn.com.fintheircing.customer.business.controller;

import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.service.feign.IBusinessFeignService;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/business")
@Api("用户关于商业上操作")
public class BusinessController {
    @Resource
    private IBusinessFeignService businessFeignService;


    @ApiOperation(value = "获取产品列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getProducts")
    public ResponseModel getProducts(@ModelAttribute UserTokenInfo userInfo, @RequestBody ProductModel model){
        model = businessFeignService.getProductModel(model);
        if (model==null){
            return ResponseModel.fail("", ResultCode.PRODUCT_GET_ERR);
        }
        return ResponseModel.sucess("",model);
    }

    @ApiOperation(value = "验证并保存合约", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveContract")
    public ResponseModel saveContract(){
        return ResponseModel.sucessWithEmptyData("");
    }

}
