package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.PayInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodInfoPayModel;
import cn.com.fintheircing.customer.user.model.promise.AddPromiseMoneyModel;
import cn.com.fintheircing.customer.user.model.promise.PromiseModel;
import cn.com.fintheircing.customer.user.utlis.Entity2Model;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@RestController
@Api("PayController相关的控制层 ")
@RequestMapping("/pay")
public class UserPayController {
    @Resource
    private IRecodInfoPayRepository iRecodInfoPayRepository;
    @Resource
    private IPayMapper iPayMapper;
    @Resource
    private IUserAccountRepository iUserAccountRepository;
    @ApiOperation(value = "生成待确认充值记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/recodPayInfo")
    public RecodInfoPayModel recodPayInfo(@ModelAttribute UserTokenInfo userInfo, @RequestBody PayInfoModel model) {
        RecodInfoPayModel m = new RecodInfoPayModel();
        m.setAddCount(model.getAmount());
        m.setRemark("充值了" + model.getAmount());
        m.setUserId(userInfo.getUuid());
        m.setWay(model.getWay());
        m.setPhone(userInfo.getPhone());
        m.setUserName(userInfo.getUserName());
        RecodInfoPayModel model1 = Entity2Model.CoverRecodInfoPay(iRecodInfoPayRepository.save(Model2Entity.CoverRecodInfoPayModel(m)));
        return model1;
    }

    @ApiOperation(value = "查看自己的充值记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/checkPayInfo")
    public ResponseModel<List<RecodInfoPayModel>> checkPayInfo(@ModelAttribute UserTokenInfo userInfo) {
        List<RecodeInfoPay> allByUserId = iRecodInfoPayRepository.findAllByUserId(userInfo.getUuid());
        allByUserId.forEach(a -> {
            if ("0".equals(a.getStatus())) {
                allByUserId.remove(a);
            }
        });
        return ResponseModel.sucess("", Entity2Model.CoverListRecodInfoPay(allByUserId));

    }

    @ApiOperation(value = "更新充值记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = false, dataType = "String")
    })
    @RequestMapping("/updatePayInfo")
    public ResponseModel updatePayInfo(@ModelAttribute UserTokenInfo userInfo, RecodInfoPayModel model) {
        ;
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
    @GetMapping("/addPromiseMoney")
    public ResponseModel<PromiseModel>  addPromiseMoney(@ModelAttribute UserTokenInfo userInfo, AddPromiseMoneyModel model) {
        if (iUserAccountRepository.findAccountByUserId(userInfo.getUuid())<model.getCash()){
            return ResponseModel.fail("", ResultCode.ACCOUNT_LESS_ERR);
        }
        PromiseModel p =new PromiseModel();
        p.setUserId(userInfo.getUuid());
        p.setCash(model.getCash());
        p.setBusinessContractId(model.getBusinessContractId());
        return ResponseModel.sucess("",p);

    }
}