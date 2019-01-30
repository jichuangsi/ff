package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.EntityToModel;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IPayInfoRepository;
import cn.com.fintheircing.admin.usermanag.entity.pay.RecodeInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillQueryModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillResponseModel;
import cn.com.fintheircing.admin.usermanag.service.IPayService;
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
@CrossOrigin
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
        return ResponseModel.sucess("", ipayService.queryPayResult(model));

    }

    @GetMapping("/recodePayInfo")
    @ApiOperation(value = "保存待确认信息", notes = "")
    public ResponseModel recodePayInfo(@ModelAttribute UserTokenInfo userInfo) throws UserServiceException {
        RecodeInfoPayModel recodeInfoPayModel = iCustomerFeignService.recodPayInfo();
        RecodeInfo save = iPayInfoRepository.save(ModelToEntity.CoverPayInfo(recodeInfoPayModel));
        RecodeInfoPayModel model = EntityToModel.CoverPayInfo(save);
        return ResponseModel.sucess("", model);
    }

    @GetMapping("/updatePayInfo")
    @ApiOperation(value = "更新用户充值记录", notes = "")
    public RecodeInfoPayModel updatePayInfo(@ModelAttribute UserTokenInfo userInfo, @RequestBody RecodeInfoPayModel model) throws UserServiceException {
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

    @GetMapping("/AgreePromiseMoney")
    @ApiOperation(value = "同意申请追加保证金", notes = "")
    public ResponseModel AgreePromiseMoney(@ModelAttribute UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        try {
            if (ipayService.agreePromiseMoney(userInfo, model)) {
                return ResponseModel.sucessWithEmptyData("");
            }
        }catch (Exception e){
            return ResponseModel.fail("", e.getMessage());
        }
        return null;
    }

    @GetMapping("/expendMoney")
    @ApiOperation(value = "同意扩大融资", notes = "")
    public ResponseModel expendMoney(@ModelAttribute UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        return ResponseModel.sucess("",ipayService.expendMoney(userInfo,model));
    }

    @GetMapping("/passPromiseMoney")
    @ApiOperation(value = "驳回申请并记录", notes = "")
    public ResponseModel passPromiseMoney(@ModelAttribute UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        return ResponseModel.sucess("", ipayService.passPromiseMoney(userInfo, model));

    }

    @GetMapping("/withdrawCash")
    @ApiOperation(value = "同意提现申请并且记录", notes = "")
    public ResponseModel agreewithdrawCash(@ModelAttribute UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        try {
            if (ipayService.agreewithdrawCash(userInfo, model)) {
                return ResponseModel.sucessWithEmptyData("");
            }
        }catch (Exception e){
            return ResponseModel.fail("", e.getMessage());
        }
        return null;
    }

    @GetMapping("/passwithdrawCash")
    @ApiOperation(value = "驳回提现申请并且记录", notes = "")
    public ResponseModel passwithdrawCash(@ModelAttribute UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        return ResponseModel.sucess("", ipayService.passwithdrawCash(userInfo, model));
    }


}