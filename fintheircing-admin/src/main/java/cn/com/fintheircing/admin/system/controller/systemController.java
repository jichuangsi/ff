package cn.com.fintheircing.admin.system.controller;

import cn.com.fintheircing.admin.common.constant.PositionCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.system.exception.SystemException;
import cn.com.fintheircing.admin.system.model.brand.BrandModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidayModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidaySearchModel;
import cn.com.fintheircing.admin.system.service.SystemService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system")
@Api("系统管理controller")
public class systemController {

    @Resource
    private SystemService systemService;



    @ApiOperation(value = "添加假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveHoliday")
    public ResponseModel saveHoliday(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody HolidayModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.saveHoliday(userInfo,model);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateHoliday")
    public ResponseModel updateHoliday(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody HolidayModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.updateHoliday(userInfo,model);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "批量删除假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteHolidays")
    public ResponseModel deleteHolidays(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if(!systemService.deleteHolidates(userInfo,model)){
            return ResponseModel.fail("",ResultCode.DELETE_FAIL_MSG);
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "分页查询假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getPageHolidays")
    public ResponseModel<PageInfo<HolidayModel>> getPageHolidays(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody HolidaySearchModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if(model.getPageIndex()==0||model.getPageIndex()==null
                ||model.getPageSize()==null||model.getPageSize()==0){
            return ResponseModel.fail("",ResultCode.PARAM_MISS_MSG);
        }
        try {
            return ResponseModel.sucess("",systemService.getPageHolidays(model));
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "保存轮播图,此处要form表单保存", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveBrand")
    public ResponseModel saveBrand(@RequestParam MultipartFile file, @ModelAttribute UserTokenInfo userInfo, BrandModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.saveBrand(userInfo,model,file);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "删除轮播图", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteBrands")
    public ResponseModel deleteBrands(@ModelAttribute UserTokenInfo userInfo,IdModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if(!systemService.deleteBrands(userInfo,model)){
            return ResponseModel.fail("",ResultCode.DELETE_FAIL_MSG);
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "修改轮播图", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateBrand")
    public ResponseModel updateBrand(@RequestParam MultipartFile file,@ModelAttribute UserTokenInfo userInfo, BrandModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.updateBrand(file,model,userInfo);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "获取轮播图列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getBrands")
    public ResponseModel<PageInfo<BrandModel>> getBrands(@ModelAttribute UserTokenInfo userInfo,@RequestBody BrandModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        return ResponseModel.sucess("",systemService.getPageBrands(model));
    }

    private Boolean IsManage(UserTokenInfo userInfo){
        if (PositionCode.POSITION_MANAGE.getIndex().equals(userInfo.getPosition())){
            return true;
        }
        return false;
    }

}
