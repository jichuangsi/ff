package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.PayConfigModel;
import cn.com.fintheircing.customer.user.model.SpreadModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.AppResultModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.PayInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodInfoPayModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.ResultModel;
import cn.com.fintheircing.customer.user.service.UserService;
import cn.com.fintheircing.customer.user.utlis.Entity2Model;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api("UserController")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private IAdminFeignService adminFeignService;
    @Resource
    private IRecodInfoPayRepository iRecodInfoPayRepository;
    @Resource
    private IPayMapper iPayMapper;

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
    @RequestMapping("/getPayConfig")
    public PayConfigModel payForNet(@ModelAttribute UserTokenInfo userInfo) {
        return userService.payForNet(userInfo);
    }

    @ApiOperation(value = "第三方二维码支付地址", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @RequestMapping("/payForQRCode")
    public ResponseModel<AppResultModel> payForQRCode(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("", adminFeignService.payForQRCode());
    }

    @ApiOperation(value = "第三方网关支付地址", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/getWayToPay")
    public ResponseModel<ResultModel> getWayToPay(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("", adminFeignService.getWayToPay());
    }

}






