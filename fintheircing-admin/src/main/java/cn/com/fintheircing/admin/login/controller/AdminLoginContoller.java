package cn.com.fintheircing.admin.login.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.WebCommonUtils;
import cn.com.fintheircing.admin.login.exception.AdminLoginException;
import cn.com.fintheircing.admin.login.service.AdminLoginService;
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
@Api("管理员登录controller")
public class AdminLoginContoller {

    @Resource
    private AdminLoginService adminLoginService;

    @ApiOperation(value = "管理员登录", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/adminLogin")
    public ResponseModel login(@Validated @RequestBody UserTokenInfo model , HttpServletRequest request) throws AdminLoginException {
        if(request==null||adminLoginService.isExistBlackList(WebCommonUtils.getClientIp(request))){
            return  ResponseModel.fail("",ResultCode.IP_VISIT_ERR);
        }
        String token = null;
        try {
            token = adminLoginService.adminLogin(model);
        } catch (AdminLoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("token",token);
        return ResponseModel.sucess("",map);
    }


}
