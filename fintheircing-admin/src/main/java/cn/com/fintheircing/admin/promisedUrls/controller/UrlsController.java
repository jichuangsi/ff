package cn.com.fintheircing.admin.promisedUrls.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.RoleCodes;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.promisedUrls.exception.UrlException;
import cn.com.fintheircing.admin.promisedUrls.model.UrlsModel;
import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/urls")
@Api("关于URl的crud，管理员操作")
public class UrlsController {

    @Resource
    private UrlService urlService;

    @ApiOperation(value = "保存新访问路径", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveNewUrl")
    public ResponseModel saveUrls(@ModelAttribute UserTokenInfo userInfo,@RequestBody UrlsModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        urlService.savePermisedUrl(userInfo,model);
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "修改访问路径", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateUrls")
    public ResponseModel updateUrls(@ModelAttribute UserTokenInfo userInfo,@RequestBody UrlsModel urlsModel){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if(StringUtils.isEmpty(urlsModel.getId())){
            return ResponseModel.fail("",ResultCode.PARAM_MISS_MSG);
        }
        try {
            urlService.updatePermisedUrl(userInfo,urlsModel);
        } catch (UrlException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "删除访问路径", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/deleteUrls")
    public ResponseModel deleteUrls(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel ids){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if(!(ids.getIds().size()>0)){
            return ResponseModel.fail("",ResultCode.PARAM_MISS_MSG);
        }
        if(!urlService.deleteUrls(userInfo,ids)){
            return ResponseModel.fail("",ResultCode.DELETE_FAIL_MSG);
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "添加访问路径关联", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveRalationUrl")
    public ResponseModel saveRalationUrl(@ModelAttribute UserTokenInfo userInfo, @RequestBody UrlsModel model) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        urlService.saveRelationUrls(userInfo,model);
        return ResponseModel.sucessWithEmptyData("");
    }

    /**
     * 给个意思，查出所有url。
     * position和role就不查了，
     * 我都不知道这两个放在数据库干嘛用了。。。我都在类里写死了。。
     * @param userInfo
     * @return
     */
    @ApiOperation(value = "找出所有的url的id", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getAllUrls")
    public ResponseModel<List<UrlsModel>> getAllUrls(@ModelAttribute UserTokenInfo userInfo) {
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }

        return ResponseModel.sucess("",urlService.getAllUrls());
    }


    //还没写
    @ApiOperation(value = "找出所有的角色position和角色", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getAllRoles")
    public ResponseModel<List<RoleModel>> getAllRoles(){

        return ResponseModel.sucessWithEmptyData("");
    }



    @ApiOperation(value = "查看所有访问路径关联", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getAllRalationUrl")
    public ResponseModel<List<UrlsModel>> getAllRalationUrl(@ModelAttribute UserTokenInfo userInfo){
        if (!IsManage(userInfo)) {
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }

        return ResponseModel.sucess("",urlService.getAllRelations());
    }



    @ApiOperation(value = "删除访问路径关联", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/deleteRelations")
    public ResponseModel deleteRelations(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel ids){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if(!(ids.getIds().size()>0)){
            return ResponseModel.fail("",ResultCode.PARAM_MISS_MSG);
        }
        if(!urlService.deleteRelations(userInfo,ids)){
            return ResponseModel.fail("",ResultCode.DELETE_FAIL_MSG);
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    private Boolean IsManage(UserTokenInfo userInfo){
        if (RoleCodes.ROLE_KEY_STRING.get("M").equals(userInfo.getRoleGrade())){
            return true;
        }
        return false;
    }
}
