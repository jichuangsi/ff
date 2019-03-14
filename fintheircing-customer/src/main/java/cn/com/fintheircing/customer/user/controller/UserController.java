package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.constant.MesRead;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.mapper.IUserMesInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IBankRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserClientLoginInfoRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserMesInfoRepository;
import cn.com.fintheircing.customer.user.entity.BankCard;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserMesInfo;
import cn.com.fintheircing.customer.user.exception.AccountPayException;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.*;
import cn.com.fintheircing.customer.user.model.contract.AccountInfoModel;
import cn.com.fintheircing.customer.user.model.mes.MesInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.AppResultModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.PayInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.ResultModel;
import cn.com.fintheircing.customer.user.model.queryModel.AppQueryModel;
import cn.com.fintheircing.customer.user.model.queryModel.NetQueryModel;
import cn.com.fintheircing.customer.user.service.UserPayService;
import cn.com.fintheircing.customer.user.service.UserService;
import cn.com.fintheircing.customer.user.utlis.GsonUtil;
import cn.com.fintheircing.customer.user.utlis.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api("UserController")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserPayService userPayService;
    @Resource
    private IAdminFeignService adminFeignService;
    @Resource
    private IUserMesInfoRepository iUserMesInfoRepository;
    @Resource
    private IUserMesInfoMapper iUserMesInfoMapper;
    @Resource
    private IBankRepository iBankRepository;
    @Resource
    private IPayMapper iPayMapper;
    @ApiOperation(value = "获取第三方指定支付信息返回支付地址", notes = "")
    @GetMapping("/recodPayInfo")
    public ResponseModel recodPayInfo(@ModelAttribute UserTokenInfo userInfo ,@RequestBody NetQueryModel model)throws  AccountPayException {
        PayInfoModel payInfoModel = adminFeignService.recodPayInfo();
        ResultModel resultModel = userPayService.recodPayInfo(userInfo, model, payInfoModel);
        return ResponseModel.sucess("",resultModel);

    }
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



    @ApiOperation(value = "第三方二维码支付地址", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/payForQRCode")
    public ResponseModel<AppResultModel> payForQRCode(@ModelAttribute UserTokenInfo userInfo, @RequestBody AppQueryModel model) {
        return ResponseModel.sucess("", adminFeignService.payForQRCode(model));
    }

    @ApiOperation(value = "我的页面信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/userInfo")
    public ResponseModel getUserInfo(@ModelAttribute UserTokenInfo userInfo) {

        return ResponseModel.sucess("", userService.getUserInfo(userInfo));
    }

    @ApiOperation(value = "修改密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/updatePassWord")
    public ResponseModel updatePassWord(@ModelAttribute UserTokenInfo userInfo, @RequestBody PassWordModel model) {
        try {
            userService.updatePass(userInfo, model);
        } catch (LoginException e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }


    @ApiOperation(value = "验证密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/validatePassWord")
    public ResponseModel validatePassWord(@ModelAttribute UserTokenInfo userInfo, @RequestBody PassWordModel model) {
        try {
            userService.validatePass(userInfo, model);
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
    public ResponseModel<List<MesInfoModel>> getMesInfo(@ModelAttribute UserTokenInfo userInfo) {
        List<MesInfoModel> allUserMesInfo = iUserMesInfoMapper.findAllUserMesInfo();
        for (MesInfoModel model:allUserMesInfo
             ) {
            if (model.getStatus().equalsIgnoreCase("0")) {
                model.setStatus(MesRead.getName(0));
            } else {
                model.setStatus(MesRead.getName(1));
            }
        }
        return ResponseModel.sucess("", allUserMesInfo);
    }

    @ApiOperation(value = "统计未读的消息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/countMes")
    public ResponseModel countMes(@ModelAttribute UserTokenInfo userInfo) {
        List<UserMesInfo> allByIsRead = iUserMesInfoRepository.findAllByIsReadAndDeleteFlag(0,0);
        return ResponseModel.sucess("", allByIsRead.size());
    }

    @ApiOperation(value = "阅读消息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/readMes")
    public ResponseModel readMes(@ModelAttribute UserTokenInfo userInfo, String mesId) {
        return ResponseModel.sucess("", iUserMesInfoMapper.updateRead(mesId));

    }

    @ApiOperation(value = "绑定银行卡", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")

    })
    @PostMapping("/setBank")
    public ResponseModel setBank(@ModelAttribute UserTokenInfo userInfo, @RequestBody BankCardModel model) {
        BankCard b = new BankCard();
        b.setBankId(model.getBankId());
        b.setBankName(model.getBankName());
        b.setPhoneNo(model.getPhoneNo());
        b.setUserId(model.getUserId());
        b.setUserName(model.getUserName());
        if (StringUtils.isEmpty(iBankRepository.save(b))) {
            return ResponseModel.fail("", "绑定失败");
        }
        return ResponseModel.sucess("", model);
    }

    @ApiOperation(value = "个人信息-修改头像", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/setAvatar")
    public ResponseModel setAvatar(@ModelAttribute UserTokenInfo userInfo, @RequestBody Encode64 base64) throws IOException {
        if (userService.setAvatar(base64, userInfo.getUuid())) {
            return ResponseModel.sucessWithEmptyData("");
        }
        return ResponseModel.fail("", ResultCode.SYS_ERROR);
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
    @ApiOperation(value = "配置订单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/assignOrder")
    public ResponseModel assignOrder(@ModelAttribute UserTokenInfo userInfo)throws LoginException{
        try {
            return ResponseModel.sucess("",userService.assignOrder(userInfo.getUuid()));
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
//        return ResponseModel.sucessWithEmptyData("");
    }
    @ApiOperation(value = "账号管理", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/accountManagement")
    public ResponseModel accountManagement(@ModelAttribute UserTokenInfo userInfo)throws LoginException{
        try {
            return ResponseModel.sucess("",userService.accountManagement(userInfo.getUuid()));
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
//        return ResponseModel.sucessWithEmptyData("");
    }
    @ApiOperation(value = "资金明细", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/checkPayInfo")
    public ResponseModel<List<AccountInfoModel>> checkPayInfo(@ModelAttribute UserTokenInfo userInfo) {

        return  ResponseModel.sucess("",iPayMapper.QueryListForAccountInfo(userInfo.getUuid()));

    }
    @ApiOperation(value = "找回密码code", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/getPassBackCode")
    public ResponseModel<String> getPassBackCode(@RequestParam("phone") String phone) {
        try {
            return ResponseModel.sucess("",userService.getPassBackCode(phone));
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "找回密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/getPassBack")
    public ResponseModel<String> getPassBack(@RequestParam("phone") String phone,@RequestParam("pass")String pass,@RequestParam("code")String code) {
        try {
            userService.updateNewPass(code,pass,phone);
        } catch (LoginException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }
}
