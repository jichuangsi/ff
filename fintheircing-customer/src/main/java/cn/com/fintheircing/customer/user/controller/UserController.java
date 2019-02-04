package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IUserMesInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IUserMesInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserMesInfo;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.*;
import cn.com.fintheircing.customer.user.model.mes.UserMesInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.AppResultModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.ResultModel;
import cn.com.fintheircing.customer.user.model.queryModel.AppQueryModel;
import cn.com.fintheircing.customer.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api("UserController")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private IAdminFeignService adminFeignService;
    @Resource
    private IUserMesInfoRepository iUserMesInfoRepository;
    @Resource
    private IUserMesInfoMapper iUserMesInfoMapper;

    @ApiOperation(value = "获取指定用户信息", notes = "")
    @GetMapping("/getUserClientInfoByPhone")
    public UserClientInfo findOneByUserName(@RequestParam(value = "userName") String userName) {
        return userService.findOneByUserName(userName);
    }

    @ApiOperation(value = "获取自己的分享页面", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getOwnSpread")
    public ResponseModel<SpreadModel> getOwnSpread(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("", adminFeignService.getOwnSpread(userInfo.getUuid()));
    }

    @ApiOperation(value = "返回第三方配置信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @RequestMapping("/payForNet")
    public PayConfigModel payForNet(@ModelAttribute UserTokenInfo userInfo) {
        return userService.payForNet(userInfo);
    }

    @ApiOperation(value = "第三方二维码支付地址", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/payForQRCode")
    public ResponseModel<AppResultModel> payForQRCode(@ModelAttribute UserTokenInfo userInfo, @RequestBody AppQueryModel model) {
        return ResponseModel.sucess("", adminFeignService.payForQRCode(model));
    }

    @ApiOperation(value = "第三方网关支付地址", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/getWayToPay")
    public ResponseModel<ResultModel> getWayToPay(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("", adminFeignService.getWayToPay());
    }
    @ApiOperation(value = "我的页面信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/userInfo")
    @CrossOrigin
    public ResponseModel getUserInfo(@ModelAttribute UserTokenInfo userInfo) {

        return ResponseModel.sucess("", userService.getUserInfo(userInfo));
    }

    @ApiOperation(value = "修改密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/updatePassWord")
    public ResponseModel updatePassWord(@ModelAttribute UserTokenInfo userInfo, @RequestBody PassWordModel model){
        try {
            userService.updatePass(userInfo,model);
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "验证密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/validatePassWord")
    public ResponseModel validatePassWord(@ModelAttribute UserTokenInfo userInfo,@RequestBody PassWordModel model){
        try {
            userService.validatePass(userInfo,model);
        } catch (LoginException e) {
            return ResponseModel.fail("");
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "获取官方消息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/getMesInfo")
    public ResponseModel<List<UserMesInfoModel>> getMesInfo(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("",iUserMesInfoMapper.findAllUserMesInfo());
    }

    @ApiOperation(value = "统计未读的消息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/countMes")
    public ResponseModel countMes(@ModelAttribute UserTokenInfo userInfo) {
        List<UserMesInfo> allByIsRead = iUserMesInfoRepository.findAllByIsRead(0);
        return ResponseModel.sucess("",allByIsRead.size());
    }

    @ApiOperation(value = "阅读消息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/readMes")
    public ResponseModel readMes(@ModelAttribute UserTokenInfo userInfo,String mesId) {
        int i = iUserMesInfoMapper.updateRead(mesId);
        if (i>0){
            return ResponseModel.sucessWithEmptyData("");
        }
        return ResponseModel.fail("", ResultCode.SYS_ERROR_MSG);
    }


    @ApiOperation(value = "实名认证", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/userCer")
    public ResponseModel userCer(@ModelAttribute UserTokenInfo userInfo,@RequestBody UserCerModel model){
        try {
            userService.userCer(userInfo,model);
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

}


