package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.pay.RecodInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillQueryModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillResponseModel;
import cn.com.fintheircing.admin.usermanag.service.IPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("支付相关的PayController")
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private IPayService ipayService;
    @Resource
    private ICustomerFeignService iCustomerFeignService;
    @PostMapping("/getBill")
    @ApiOperation(value = "支付结果查询", notes = "")
    public ResponseModel<BillResponseModel> getBill(@RequestBody BillQueryModel model) throws UserServiceException {
       return ResponseModel.sucess("",ipayService.queryPayResult(model));

    }

    @GetMapping("/findAllPayInfo")
    @ApiOperation(value = "返回所有用户的支付信息", notes = "")
    public ResponseModel<List<RecodInfoPayModel>> findAllPayInfo() throws UserServiceException {
        List<RecodInfoPayModel> allPayInfo = iCustomerFeignService.findAllPayInfo();
        return ResponseModel.sucess("",allPayInfo);
    }

    @GetMapping("/updatePayInfo")
    @ApiOperation(value = "更新用户充值记录", notes = "")
    public RecodInfoPayModel updatePayInfo(@RequestBody RecodInfoPayModel model) throws UserServiceException {
             return model;
    }

}
