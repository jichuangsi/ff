package cn.com.fintheircing.admin.account.controller;

import cn.com.fintheircing.admin.account.model.AdminModel;
import cn.com.fintheircing.admin.account.model.PassWordModel;
import cn.com.fintheircing.admin.account.service.AccountService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;

@RestController
@RequestMapping("/account")
@Api("账号controller")
@CrossOrigin
public class AccountController {
    @Resource
    private AccountService accountService;

    @ApiOperation(value = "显示个人资料", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getSelfData")
    public ResponseModel<AdminModel> getSelfData(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",accountService.getSelfData(userInfo));
    }

    @ApiOperation(value = "修改个人资料", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateSelfData")
    public ResponseModel updateSelfData(@ModelAttribute UserTokenInfo userInfo,@Validated @RequestBody AdminModel model){
        accountService.updateSelfData(userInfo,model);
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "修改密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updatePass")
    public ResponseModel updatePass(@ModelAttribute UserTokenInfo userInfo, @RequestBody PassWordModel model){
        try {
            accountService.updatePass(userInfo,model);
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

}
