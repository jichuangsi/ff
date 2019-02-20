package cn.com.fintheircing.admin.taxation.controller;

import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.taxation.exception.TaxationException;
import cn.com.fintheircing.admin.taxation.model.TaxationModel;
import cn.com.fintheircing.admin.taxation.service.TaxationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("关于税收相关操作")
@CrossOrigin
@RequestMapping("/taxation")
public class TaxationController {
    @Resource
    private TaxationService taxationService;

    @ApiOperation(value = "添加税收", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/createdNewTaxation")
    public ResponseModel createdNewTaxation(@ModelAttribute UserTokenInfo userInfo, @RequestBody TaxationModel model) {
        try {
            taxationService.saveNewTaxation(userInfo, model);
        } catch (TaxationException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "删除税收", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteTaxation")
    public ResponseModel deleteTaxation(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel ids) {
        try {
            taxationService.deleteTaxation(userInfo, ids);
        } catch (TaxationException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查看税收", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getTaxations")
    public ResponseModel<List<TaxationModel>> getTaxations(@ModelAttribute UserTokenInfo userInfo) {
        return  ResponseModel.sucess("",taxationService.getTaxations());
    }

    @ApiOperation(value = "修改除税率外的数据", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateTaxation")
    public ResponseModel updateTaxation(@ModelAttribute UserTokenInfo userInfo,@RequestBody TaxationModel model){
        try {
            taxationService.updateTaxation(userInfo,model);
        } catch (TaxationException e) {
            return  ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }
}
