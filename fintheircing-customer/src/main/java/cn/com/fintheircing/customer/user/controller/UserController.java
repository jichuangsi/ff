package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.SpreadModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api("UserController")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private IAdminFeignService adminFeignService;


    @ApiOperation(value = "获取指定用户信息", notes = "")
    @GetMapping("/getUserClientInfoByPhone")
    public UserClientInfo getUserClientInfoByPhone(@RequestParam(value = "userName") String userName){
        return  userService.getUserClientInfoByPhone(userName);
    }

    @ApiOperation(value = "获取自己的分享页面", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getOwnSpread")
    public ResponseModel<SpreadModel> getOwnSpread(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",adminFeignService.getOwnSpread(userInfo.getUuid()));
    }
}
