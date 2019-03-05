package cn.com.fintheircing.admin.system.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.RoleCodes;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.system.exception.SystemException;
import cn.com.fintheircing.admin.system.model.agreement.AgreementModel;
import cn.com.fintheircing.admin.system.model.bank.BankCardModel;
import cn.com.fintheircing.admin.system.model.black.BlackModel;
import cn.com.fintheircing.admin.system.model.brand.BrandModel;
import cn.com.fintheircing.admin.system.model.company.CompanyModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidayModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidaySearchModel;
import cn.com.fintheircing.admin.system.model.photo.PhotoModel;
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
import java.util.List;

@RestController
@RequestMapping("/system")
@Api("系统管理controller")
@CrossOrigin
public class SystemController {

    @Resource
    private SystemService systemService;

    @ApiOperation(value = "添加假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveHoliday")
    public ResponseModel saveHoliday(@ModelAttribute UserTokenInfo userInfo, @Validated @RequestBody HolidayModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.saveHoliday(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateHoliday")
    public ResponseModel updateHoliday(@ModelAttribute UserTokenInfo userInfo, @Validated @RequestBody HolidayModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.updateHoliday(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "批量删除假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteHolidays")
    public ResponseModel deleteHolidays(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if (!systemService.deleteHolidates(userInfo, model)) {
            return ResponseModel.fail("", ResultCode.DELETE_FAIL_MSG);
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "分页查询假期", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getPageHolidays")
    public ResponseModel<PageInfo<HolidayModel>> getPageHolidays(@ModelAttribute UserTokenInfo userInfo, @Validated @RequestBody HolidaySearchModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if (model.getPage() == 0 || model.getPage() == null
                || model.getLimit() == null || model.getLimit() == 0) {
            return ResponseModel.fail("", ResultCode.PARAM_MISS_MSG);
        }
        try {
            return ResponseModel.sucess("", systemService.getPageHolidays(model));
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "保存轮播图,此处要form表单保存", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveBrand")
    public ResponseModel saveBrand(@RequestParam MultipartFile file, @ModelAttribute UserTokenInfo userInfo, BrandModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.saveBrand(userInfo, model, file);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "删除轮播图", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteBrands")
    public ResponseModel deleteBrands(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if (!systemService.deleteBrands(userInfo, model)) {
            return ResponseModel.fail("", ResultCode.DELETE_FAIL_MSG);
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改轮播图form表单提交", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateBrand")
    public ResponseModel updateBrand(@RequestParam MultipartFile file, @ModelAttribute UserTokenInfo userInfo, BrandModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            systemService.updateBrand(file, model, userInfo);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "获取轮播图列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getBrands")
    public ResponseModel<List<BrandModel>> getBrands(@ModelAttribute UserTokenInfo userInfo) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        return ResponseModel.sucess("", systemService.getBrands());
    }

    @ApiOperation(value = "添加黑名单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveBlack")
    public ResponseModel saveBlack(@ModelAttribute UserTokenInfo userInfo, @Validated @RequestBody BlackModel model) {
        try {
            systemService.saveBlack(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查看黑名单列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getBlacks/{index}/{size}")
    public ResponseModel<PageInfo<BlackModel>> getBlacks(@ModelAttribute UserTokenInfo userInfo
            , @PathVariable("index") int index, @PathVariable("size") int size) {
        return ResponseModel.sucess("", systemService.getPageBlack(index, size));
    }

    @ApiOperation(value = "移除黑名单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteBlackIp/{id}")
    public ResponseModel deleteBlackIp(@ModelAttribute UserTokenInfo userInfo, @PathVariable("id") String id) {
        try {
            systemService.deleteBlack(userInfo, id);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    private Boolean IsManage(UserTokenInfo userInfo) {
        if (RoleCodes.ROLE_KEY_STRING.get("M").equals(userInfo.getRoleGrade())) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "获取图片列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getPhotos")
    public ResponseModel<List<PhotoModel>> getPhotos(@ModelAttribute UserTokenInfo userInfo, @RequestParam("on") String on) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        try {
            return ResponseModel.sucess("", systemService.getPhotos(on));
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "修改图片", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updatePhoto")
    public ResponseModel updatePhoto(@RequestParam MultipartFile file, @ModelAttribute UserTokenInfo userInfo, PhotoModel model) {
        try {
            systemService.updatePhoto(file, model, userInfo);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "保存图片", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/savePhoto")
    public ResponseModel savePhoto(@RequestParam MultipartFile file, @ModelAttribute UserTokenInfo userInfo, PhotoModel model) {
        try {
            systemService.savePhoto(userInfo, model, file);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "删除图片", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deletePhotos")
    public ResponseModel deletePhotos(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel model) {
        systemService.deletePhoto(userInfo, model);
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "保存线下", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveBankCard")
    public ResponseModel saveBankCard(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody BankCardModel model) {
        try {
            systemService.saveBankCard(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "删除线下", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteBankCard")
    public ResponseModel deleteBankCard(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel model) {
        try {
            systemService.deleteBankCard(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改线下", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateBankCard")
    public ResponseModel updateBankCard(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody BankCardModel model){
        try {
            systemService.updateBankCard(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查看线下", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getBankCard")
    public ResponseModel<List<BankCardModel>> getBankCard(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",systemService.getBankCard());
    }

    @ApiOperation(value = "查看公司信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getCompanyDetails")
    public ResponseModel<List<CompanyModel>> getCompanyDetails(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",systemService.getCompanyDetails());
    }

    @ApiOperation(value = "修改公司信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateCompanyDetail")
    public ResponseModel updateCompanyDetail(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody CompanyModel model){
        try {
            systemService.updateCompanyDetail(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "保存公司信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveCompanyDetail")
    public ResponseModel saveCompanyDetail(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody CompanyModel model){
        systemService.saveCompanyDetail(userInfo, model);
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "删除公司信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteCompanyDetail")
    public ResponseModel deleteCompanyDetail(@ModelAttribute UserTokenInfo userInfo,@RequestBody IdModel model){
        try {
            systemService.deleteCompanyDetail(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "保存协议内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveAgreement")
    public ResponseModel saveAgreement(@ModelAttribute UserTokenInfo userInfo, @RequestBody AgreementModel model){
        systemService.saveAgreement(userInfo, model);
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "删除协议内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @DeleteMapping("/deleteAgreement")
    public ResponseModel deleteAgreement(@ModelAttribute UserTokenInfo userInfo,@RequestBody IdModel model){
        try {
            systemService.deleteAgreement(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改协议内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateAgreement")
    public ResponseModel updateAgreement(@ModelAttribute UserTokenInfo userInfo,@RequestBody AgreementModel model){
        try {
            systemService.updateAgreement(userInfo, model);
        } catch (SystemException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return  ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查看协议内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getAgreements")
    public ResponseModel<List<AgreementModel>> getAgreements(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",systemService.getAgreements());
    }
}
