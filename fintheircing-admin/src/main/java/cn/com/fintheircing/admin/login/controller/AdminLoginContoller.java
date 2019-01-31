package cn.com.fintheircing.admin.login.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.WebCommonUtils;
import cn.com.fintheircing.admin.login.dao.mapper.IAdminClientLoginInfoMapper;
import cn.com.fintheircing.admin.login.exception.AdminLoginException;
import cn.com.fintheircing.admin.login.service.AdminLoginService;
import cn.com.fintheircing.admin.usermanag.entity.pay.RecodeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Api("管理员登录controller")
public class AdminLoginContoller {

    @Resource
    private AdminLoginService adminLoginService;
    @Resource
    private IAdminClientLoginInfoMapper iAdminClientLoginInfoMapper;
    @ApiOperation(value = "管理员登录", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/adminLogin")
    @CrossOrigin
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
        Map<String,Object> parms =new HashMap<>();
        Date d =new Date();
        parms.put("userId", model.getUuid());
        parms.put("Date", d);
        iAdminClientLoginInfoMapper.updateLoginTime(parms);

        return ResponseModel.sucess("",map);
    }

    @ApiOperation(value = "管理员退出", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/adminloginout")
    @CrossOrigin
    public ResponseModel loginout(@Validated @RequestBody UserTokenInfo model , HttpServletRequest request) throws AdminLoginException {
      return   ResponseModel.sucess("",adminLoginService.loginout(model.getUuid() )) ;

    }
}
