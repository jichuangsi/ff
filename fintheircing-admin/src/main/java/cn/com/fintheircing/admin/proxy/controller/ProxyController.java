package cn.com.fintheircing.admin.proxy.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.proxy.exception.ProxyException;
import cn.com.fintheircing.admin.proxy.model.*;
import cn.com.fintheircing.admin.proxy.service.ProxyService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/proxy")
@Api("代理商的controller")
@CrossOrigin
public class ProxyController {

    @Resource
    private ProxyService proxyService;

    @ApiOperation(value = "查询代理商列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getProxyList")
    public ResponseModel<PageInfo<ProxyModel>> getProxyList(@ModelAttribute UserTokenInfo admin, @RequestBody ProxyModel proxyModel){
        if(proxyModel.getPage()==null||proxyModel.getPage()==0
                ||proxyModel.getLimit()==null||proxyModel.getLimit()==0)
            return ResponseModel.fail("", ResultCode.PARAM_ERR_MSG);
        /*admin.setUuid("123");
        admin.setPosition(RoleCode.POSITION_MANAGE.getIndex());*/
        try {
            return ResponseModel.sucess("",proxyService.getProxyList(admin,proxyModel));
        } catch (ProxyException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "添加子代理商", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveChildProxy")
    public ResponseModel saveChildProxy(@ModelAttribute UserTokenInfo admin , @Validated @RequestBody ProxyModel proxyModel) throws ProxyException{

        if (StringUtils.isEmpty(proxyModel.getProxyName())
                ||StringUtils.isEmpty(proxyModel.getLinkMan())||StringUtils.isEmpty(proxyModel.getLinkPhone())){
            return ResponseModel.fail("",ResultCode.PARAM_ERR_MSG);
        }
        try {
            String result = proxyService.saveProxy(admin,proxyModel);
            if(!"success".equals(result)){
                return ResponseModel.sucess("",result);
            }
        } catch (ProxyException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "查看该佣金比例", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/getCommission")
    public ResponseModel<ProxyModel> getCommission(@RequestBody IdModel model){
        if(model==null||model.getIds()==null||!(model.getIds().size()>0)){
            return ResponseModel.fail("",ResultCode.PARAM_ERR_MSG);
        }
        try {
            return ResponseModel.sucess("",proxyService.getCommissions(model));
        } catch (ProxyException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "修改佣金比例", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateCommission")
    public ResponseModel updateCommission(@ModelAttribute UserTokenInfo userInfo,@RequestBody ProxyModel model){
        if(StringUtils.isEmpty(model.getBackCommission())||StringUtils.isEmpty(model.getDayCommission())
                ||StringUtils.isEmpty(model.getMonthCommission())||StringUtils.isEmpty(model.getProxyId())){
            return ResponseModel.fail("",ResultCode.PARAM_ERR_MSG);
        }
        try {
            proxyService.updateCommission(userInfo,model);
        } catch (ProxyException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查看下属员工", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getEmployee")
    public ResponseModel getEmployee(@ModelAttribute UserTokenInfo userInfo,@RequestBody EmployeeModel model){
        if(model==null||StringUtils.isEmpty(model.getId())
                ||model.getPage()==null||model.getLimit()==null){
            return ResponseModel.fail("",ResultCode.PARAM_ERR_MSG);
        }
        return ResponseModel.sucess("",proxyService.getEmployee(userInfo,model));
    }

    @ApiOperation(value = "查看下属代理和员工的邀请页面", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getSpread")
    public ResponseModel<PageInfo<SpreadModel>> getSpread(@ModelAttribute UserTokenInfo userInfo,@RequestBody SpreadModel spreadModel){
        if (spreadModel.getPageIndex()==0||spreadModel.getPageIndex()==null
                ||spreadModel.getPageSize()==0||spreadModel.getPageSize()==null){
            return ResponseModel.fail("",ResultCode.PARAM_MISS_MSG);
        }
        return ResponseModel.sucess("",proxyService.getSpreads(userInfo,spreadModel));
    }


    //没合同数据，没写
    @ApiOperation(value = "查看下属代理的佣金明细页面", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getCommissionDetail")
    public ResponseModel getCommissionDetail(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractDetailModel contract){

        return ResponseModel.sucess("",null);
    }

    //没合同数据，没写
    @ApiOperation(value = "查看下属代理的管理费明细页面", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getManageDetail")
    public ResponseModel getManageDetail(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractDetailModel contract){

        return ResponseModel.sucess("",null);
    }
}
