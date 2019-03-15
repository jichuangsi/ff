package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.common.constant.ProductStatus;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.exception.AccountPayException;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.PayInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.promise.AddPromiseMoneyModel;
import cn.com.fintheircing.customer.user.model.withdraw.WithdrawModel;
import cn.com.fintheircing.customer.user.service.UserPayService;
import cn.com.fintheircing.customer.user.service.UserService;
import cn.com.fintheircing.customer.user.utlis.Entity2Model;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("PayController相关的控制层 ")
@RequestMapping("/pay")
@CrossOrigin
public class UserPayController {
    @Resource
    private IRecodInfoPayRepository iRecodInfoPayRepository;
    @Resource
    private IPayMapper iPayMapper;
    @Resource
    private IUserAccountRepository iUserAccountRepository;
    @Resource
    private UserService userService;
    @Resource
    private IAdminFeignService iAdminFeignService;


    @ApiOperation(value = "进入第三方充值页面", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/recodPayInfo")
    public ResponseModel recodPayInfo(@ModelAttribute UserTokenInfo userInfo) {
      return  ResponseModel.sucess("",userService.recodPayInfo());
    }


    @ApiOperation(value = "更新充值记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/updatePayInfo")
    public ResponseModel updatePayInfo(@ModelAttribute UserTokenInfo userInfo, RecodeInfoPayModel model) {
        if (iPayMapper.updatePayInfo(model) > 0) {
            return ResponseModel.sucessWithEmptyData("");
        } else {
            return ResponseModel.fail("");
        }

    }

    @ApiOperation(value = "追加保证金", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/addPromiseMoney")
    @CrossOrigin
    public ResponseModel savePromiseMoney(@ModelAttribute UserTokenInfo userInfo, @RequestBody AddPromiseMoneyModel model) {
        if (iUserAccountRepository.findAccountByUserId(userInfo.getUuid()) < model.getCash()) {
            return ResponseModel.fail("", ResultCode.ACCOUNT_LESS_ERR);
        }
        RecodeInfoPay p = new RecodeInfoPay();
        p.setUserId("1");
        p.setWay(model.getWay());
        p.setRemark(model.getRemark());
        p.setCostCount(model.getCash());
        p.setBusinessContractId(model.getBusinessContractId());
        p.setTaskType("追加保证金");
        p.setTaskId("0");
        if (StringUtils.isEmpty(iRecodInfoPayRepository.save(p))) {
            return ResponseModel.fail("", ResultCode.APPLY_FAIL);
        } else {
            return ResponseModel.sucessWithEmptyData("");
        }
    }


    @ApiOperation(value = "提现申请并且生成待确认记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/withdrawCash")
    public ResponseModel withdrawCash(@ModelAttribute UserTokenInfo userInfo, @RequestBody WithdrawModel model) throws AccountPayException {
        try {
            return ResponseModel.sucess("", userService.withdrawCash(userInfo.getUuid(), model));
        } catch (Exception e) {
            throw new AccountPayException(e.getMessage());
        }
    }

    @ApiOperation(value = "用户所持有的合同", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @GetMapping("/checkContact")
    public ResponseModel<List<ContractModel>> checkContact(@ModelAttribute UserTokenInfo userInfo) throws AccountPayException {
        return ResponseModel.sucess("", iAdminFeignService.getCurrentContract(userInfo.getUuid()));
    }

    @ApiOperation(value = "消费或者增加用户资金记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/addOrUseMoney")
    public ResponseModel addOrUseMoney(@ModelAttribute UserTokenInfo userInfo, @RequestBody RecodeInfoPayModel model) {
        if (userService.addOrUseMoney(model)) {
            return ResponseModel.sucessWithEmptyData("");
        }
        return ResponseModel.fail("", ResultCode.ACCOUNT_PAY_ERR);
    }

    @ApiOperation(value = "扩大融资", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/expandMoney")
    public ResponseModel expandMoney(@ModelAttribute UserTokenInfo userInfo, @RequestBody AddPromiseMoneyModel model) {
        RecodeInfoPay p = new RecodeInfoPay();
        p.setUserId(userInfo.getUuid());
        p.setWay(model.getWay());
        p.setRemark(model.getRemark());
        p.setCostCount(model.getCash());
        p.setTaskType("扩大融资");
        p.setTaskId("0");
        if (StringUtils.isEmpty(iRecodInfoPayRepository.save(p))) {
            return ResponseModel.fail("", ResultCode.APPLY_FAIL);
        } else {
            return ResponseModel.sucessWithEmptyData("");
        }
    }
    @ApiOperation(value = "减少融资", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/releaseMoney")
    public ResponseModel releaseMoney(@ModelAttribute UserTokenInfo userInfo, @RequestBody AddPromiseMoneyModel model) {
        RecodeInfoPay p = new RecodeInfoPay();
        p.setUserId(userInfo.getUuid());
        p.setWay(model.getWay());
        p.setRemark(model.getRemark());
        p.setCostCount(model.getCash());
        p.setTaskType("减少融资");
        p.setTaskId("1");
        if (StringUtils.isEmpty(iRecodInfoPayRepository.save(p))) {
            return ResponseModel.fail("", ResultCode.APPLY_FAIL);
        } else {
            return ResponseModel.sucessWithEmptyData("");
        }
    }
    @ApiOperation(value = "结算申请-我要提盈", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/earnMoney")
    public ResponseModel earnMoney(@ModelAttribute UserTokenInfo userInfo, @RequestBody AddPromiseMoneyModel model) {
        RecodeInfoPay p = new RecodeInfoPay();
        p.setUserId(userInfo.getUuid());
        p.setRemark(model.getRemark());
        p.setAddCount(model.getCash());
        p.setBusinessContractId(model.getBusinessContractId());
        p.setTaskType("提盈");
        p.setTaskId("2");
        if (StringUtils.isEmpty(iRecodInfoPayRepository.save(p))) {
            return ResponseModel.fail("", ResultCode.APPLY_FAIL);
        } else {
            return ResponseModel.sucessWithEmptyData("");
        }
    }
    @ApiOperation(value = "结算申请-提盈记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/QueryRecodeFor")
    public ResponseModel QueryRecodeForInfo(@ModelAttribute UserTokenInfo userInfo) {
        List<RecodeInfoPayModel> recodeInfoPayModels = iPayMapper.QueryRecodeForInfo(userInfo.getUuid());
        for (RecodeInfoPayModel model:recodeInfoPayModels){
            model.setAllotStr(ProductStatus.getName(model.getAllot()));
        }
        return ResponseModel.sucess("", recodeInfoPayModels);
    }

    @ApiOperation(value = "修改或增加支付密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/addOrChangePassword")
    public ResponseModel addOrChangePassword(@ModelAttribute UserTokenInfo userInfo, String txPassword) throws AccountPayException {
        try {
            if (userService.addOrChangePassword(userInfo.getUuid(), txPassword)) {
                return ResponseModel.sucessWithEmptyData("");
            }
        } catch (Exception e) {
            throw new AccountPayException(e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "检查是否设置支付密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @PostMapping("/checkTxPassword")
    public ResponseModel checkTxPassword(@ModelAttribute UserTokenInfo userInfo) throws AccountPayException {
        return ResponseModel.sucess("", userService.checkTxPassword(userInfo.getUuid()));
    }
}