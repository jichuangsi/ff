package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IPayInfoRepository;
import cn.com.fintheircing.admin.usermanag.entity.pay.PayInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.promise.PromiseModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillQueryModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillResponseModel;
import cn.com.fintheircing.admin.usermanag.service.IPayService;
import cn.com.fintheircing.admin.usermanag.uilts.EntityToModel;
import cn.com.fintheircing.admin.usermanag.uilts.ModelToEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("支付相关的AdminPayController")
@RequestMapping("/pay")
public class AdminPayController {
    @Autowired
    private IPayService ipayService;

    @Resource
    private ICustomerFeignService iCustomerFeignService;

    @Resource
    private IPayInfoRepository iPayInfoRepository;
    @Resource
    private IBusinessContractMapper iBusinessContractMapper;
    @PostMapping("/getBill")
    @ApiOperation(value = "支付结果查询", notes = "")
    public ResponseModel<BillResponseModel> getBill(@RequestBody BillQueryModel model) throws UserServiceException {
       return ResponseModel.sucess("",ipayService.queryPayResult(model));

    }

    @GetMapping("/recodePayInfo")
    @ApiOperation(value = "保存待确认信息", notes = "")
    public ResponseModel recodePayInfo(@ModelAttribute UserTokenInfo userInfo) throws UserServiceException {
        RecodeInfoPayModel recodeInfoPayModel = iCustomerFeignService.recodPayInfo();
        PayInfo save = iPayInfoRepository.save(ModelToEntity.CoverPayInfo(recodeInfoPayModel));
        RecodeInfoPayModel model = EntityToModel.CoverPayInfo(save);
        return ResponseModel.sucess("",model);
    }

    @GetMapping("/updatePayInfo")
    @ApiOperation(value = "更新用户充值记录", notes = "")
    public RecodeInfoPayModel updatePayInfo(@ModelAttribute UserTokenInfo userInfo,@RequestBody RecodeInfoPayModel model) throws UserServiceException {
        model.setOperator(userInfo.getUserName());//操作人的名字
        model.setOperatorId(userInfo.getUuid());//操作人的Id
        ipayService.updatePayInfo(model);
        return model;
    }

    @GetMapping("/findAllPayInfo")
    @ApiOperation(value = "查询所有未审核的", notes = "")
    public ResponseModel<List<RecodeInfoPayModel>> findAllPayInfo(@ModelAttribute UserTokenInfo userInfo) throws UserServiceException {
        List<RecodeInfoPayModel> allPayInfo = ipayService.findAllPayInfo();
        return ResponseModel.sucess("", allPayInfo);
    }

    @GetMapping("/addPromiseMoney")
    @ApiOperation(value = "管理查询所有申请追加保证金", notes = "")
    public ResponseModel<List<PromiseModel>> addPromiseMoney(@ModelAttribute UserTokenInfo userInfo) throws UserServiceException {
        return ResponseModel.sucess("",  ipayService.findAllApply());

    }

    @GetMapping("/AgreePromiseMoney")
    @ApiOperation(value = "同意申请追加保证金", notes = "")
    public ResponseModel AgreePromiseMoney(@ModelAttribute UserTokenInfo userInfo,PromiseModel model) throws UserServiceException {
        if (iBusinessContractMapper.addPromiseMoney(model.getCash())<0){
            return ResponseModel.fail("", ResultCode.ADD_PROMISE_MONEY_ERR);
        }
        return ResponseModel.sucess("",  ipayService.agreePromiseMoney(userInfo,model));

    }
    @GetMapping("/passPromiseMoney")
    @ApiOperation(value = "同意申请追加保证金", notes = "")
    public ResponseModel passPromiseMoney(@ModelAttribute UserTokenInfo userInfo,PromiseModel model) throws UserServiceException {
        return ResponseModel.sucess("",  ipayService.passPromiseMoney(userInfo,model));

    }

}
