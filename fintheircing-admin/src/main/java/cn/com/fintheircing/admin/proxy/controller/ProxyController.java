package cn.com.fintheircing.admin.proxy.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.proxy.exception.ProxyException;
import cn.com.fintheircing.admin.proxy.model.IdModel;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import cn.com.fintheircing.admin.proxy.service.ProxyService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/proxy")
@Api("代理商的controller")
public class ProxyController {

    @Resource
    private ProxyService proxyService;

    @ApiOperation(value = "查询代理商列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getProxyList")
    public ResponseModel<PageInfo<ProxyModel>> getProxyList(@ModelAttribute UserTokenInfo admin, @RequestBody ProxyModel proxyModel){
        if(proxyModel.getPageIndex()==null||proxyModel.getPageIndex()==0
                ||proxyModel.getPageSize()==null||proxyModel.getPageSize()==0)
            return ResponseModel.fail("", ResultCode.PARAM_ERR_MSG);
        return ResponseModel.sucess("",proxyService.getProxyList(admin,proxyModel));
    }

    @ApiOperation(value = "添加子代理商", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveChildProxy")
    public ResponseModel saveChildProxy(@ModelAttribute UserTokenInfo admin , @RequestBody ProxyModel proxyModel) throws ProxyException{

        if (StringUtils.isEmpty(proxyModel.getProxyName())
                ||StringUtils.isEmpty(proxyModel.getLinkMan())||StringUtils.isEmpty(proxyModel.getLinkPhone())){
            return ResponseModel.fail("",ResultCode.PARAM_ERR_MSG);
        }
        try {
            proxyService.saveProxy(admin,proxyModel);
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
        return ResponseModel.sucess("",proxyService.getCommissions(model));
    }

    @ApiOperation(value = "修改佣金比例", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/updateCommission")
    public ResponseModel updateCommission(@RequestBody ProxyModel model){
        if(StringUtils.isEmpty(model.getBackCommission())||StringUtils.isEmpty(model.getDayCommission())
                ||StringUtils.isEmpty(model.getMonthCommission())||StringUtils.isEmpty(model.getProxyId())){
            return ResponseModel.fail("",ResultCode.PARAM_ERR_MSG);
        }
        try {
            proxyService.updateCommission(model);
        } catch (ProxyException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查看所属员工", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/getEmployee")
    public ResponseModel getEmployee(@RequestBody ProxyModel model){
        if(model==null||StringUtils.isEmpty(model.getProxyId())
                ||model.getPageIndex()==null||model.getPageSize()==null){
            return ResponseModel.fail("",ResultCode.PARAM_ERR_MSG);
        }
        return ResponseModel.sucess("",proxyService.getEmployee(model));
    }

/*    @ApiOperation(value = "查看所属代理的邀请页面", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/getSpread")
    public ResponseModel*/
}
