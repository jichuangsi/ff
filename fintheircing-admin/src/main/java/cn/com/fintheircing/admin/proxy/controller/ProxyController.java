package cn.com.fintheircing.admin.proxy.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import cn.com.fintheircing.admin.proxy.service.ProxyService;
import cn.com.fintheircing.admin.common.model.AdminLoginModel;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    })
    @PostMapping("/getProxyList")
    public ResponseModel<PageInfo<ProxyModel>> getProxyList(@ModelAttribute AdminLoginModel admin, @RequestBody ProxyModel proxyModel){
        if(proxyModel.getPageIndex()==null||proxyModel.getPageIndex()==0
                ||proxyModel.getPageSize()==null||proxyModel.getPageSize()==0)
            return ResponseModel.fail("", ResultCode.PARAM_ERR_MSG);
        return ResponseModel.sucess("",proxyService.getProxyList(admin,proxyModel));
    }

    @ApiOperation(value = "添加子代理商", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/saveChildProxy")
    public ResponseModel saveChildProxy(@ModelAttribute AdminLoginModel admin , @RequestBody ProxyModel proxyModel){


        return ResponseModel.sucessWithEmptyData("");
    }

}
