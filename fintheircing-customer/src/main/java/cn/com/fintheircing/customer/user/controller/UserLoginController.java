package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.common.utils.WebCommonUtils;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Api("配资客户登录controller")
public class UserLoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "用户登录", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/userLogin")
    public ResponseModel userLogin(@Validated @RequestBody UserTokenInfo model, HttpServletRequest request) throws LoginException{
        if(request==null){
            return ResponseModel.fail("",ResultCode.IP_VALIDATE_ERR);
        }
        if(loginService.isExistBlack(WebCommonUtils.getClientIp(request))){
            return ResponseModel.fail("",ResultCode.IP_BLACK_VISIT);
        }
        String token =  "";
        try {
            token = loginService.userLogin(model,token);
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("token",token);
        return ResponseModel.sucess("",map);
    }
}
