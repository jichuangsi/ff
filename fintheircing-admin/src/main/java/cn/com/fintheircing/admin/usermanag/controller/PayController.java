package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.pay.NetQueryModel;
import cn.com.fintheircing.admin.usermanag.model.pay.ResultModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillQueryModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillResponseModel;
import cn.com.fintheircing.admin.usermanag.service.IPayService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Api("支付相关的PayController")
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private IPayService ipayService;
    @PostMapping("/checkBill/{orderId}")
    @ApiImplicitParams({
    })
    @ApiOperation(value = "校检并且更新字段", notes = "")
    public ResponseModel checkBill(@RequestBody BillQueryModel model, @PathVariable String orderId) throws UserServiceException {
        if (ipayService.queryReconTrans(model, orderId)) {
            return ResponseModel.sucess("", ResultCode.SUCESS_MSG);
        } else {
            return ResponseModel.sucess("", ResultCode.PARAM_MISS_MSG);
        }
    }
    @PostMapping("/getpayforNet")
    @ApiOperation(value = "网关支付", notes = "")
    public ResponseModel<ResultModel> getWayToPay(@RequestBody NetQueryModel model) throws UserServiceException {
        return ResponseModel.sucess("",ipayService.getWayToPay(model));
    }

    @PostMapping("/getBill")
    @ApiOperation(value = "支付结果查询", notes = "")
    public ResponseModel<BillResponseModel> getBill(@RequestBody BillQueryModel model) throws UserServiceException {
       return ResponseModel.sucess("",ipayService.queryPayResult(model));

    }

}
