package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
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
    private UserPayService userPayService;


    @ApiOperation(value = "生成待确认充值记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/recodPayInfo")
    public RecodeInfoPayModel recodPayInfo(@ModelAttribute UserTokenInfo userInfo, @RequestBody PayInfoModel model) {
        RecodeInfoPayModel m = new RecodeInfoPayModel();
        m.setAddCount(model.getAmount());
        m.setRemark("充值了" + model.getAmount());
        m.setUserId(userInfo.getUuid());
        m.setWay(model.getWay());
        m.setPhone(userInfo.getPhone());
        m.setUserName(userInfo.getUserName());
        RecodeInfoPayModel model1 = Entity2Model.CoverRecodInfoPay(iRecodInfoPayRepository.save(Model2Entity.CoverRecodInfoPayModel(m)));
        return model1;
    }

    @ApiOperation(value = "查看自己的充值记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/checkPayInfo")
    public ResponseModel<List<RecodeInfoPayModel>> checkPayInfo(@ModelAttribute UserTokenInfo userInfo) {

        return ResponseModel.sucess("", iPayMapper.findAllRecodeInfo(userInfo.getUuid()));

    }

    @ApiOperation(value = "更新充值记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/updatePayInfo")
    public ResponseModel updatePayInfo(@ModelAttribute UserTokenInfo userInfo, RecodeInfoPayModel model) {
        ;
        if (iPayMapper.updatePayInfo(model) > 0) {
            return ResponseModel.sucessWithEmptyData("");
        } else {
            return ResponseModel.fail("");
        }

    }

    @ApiOperation(value = "追加保证金", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/addPromiseMoney")
    @CrossOrigin
    public ResponseModel savePromiseMoney(@ModelAttribute UserTokenInfo userInfo, @RequestBody AddPromiseMoneyModel model) {
        if (iUserAccountRepository.findAccountByUserId("1") < model.getCash()) {
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
    public ResponseModel withdrawCash(@ModelAttribute UserTokenInfo userInfo, @RequestBody WithdrawModel model) {
        if (iUserAccountRepository.findAccountByUserId(userInfo.getUuid()) < model.getAmount()) {
            return ResponseModel.fail("", ResultCode.ACCOUNT_LESS_ERR);
        }
        RecodeInfoPay r = new RecodeInfoPay();
        r.setRemark(model.getRemark());
        r.setWay(model.getAim());
        r.setUserId(userInfo.getUuid());
        r.setCostCount(model.getAmount());
        r.setTaskId("2");
        r.setTaskType("提现申请");
        RecodeInfoPay save = iRecodInfoPayRepository.save(r);
        RecodeInfoPayModel model1 = Entity2Model.CoverRecodInfoPay(save);
        return ResponseModel.sucess("", model1);

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
        p.setAddCount(model.getCash());
        p.setTaskType("扩大融资");
        p.setTaskId("1");
        if (StringUtils.isEmpty(iRecodInfoPayRepository.save(p))) {
            return ResponseModel.fail("", ResultCode.APPLY_FAIL);
        } else {
            return ResponseModel.sucessWithEmptyData("");
        }
    }
}