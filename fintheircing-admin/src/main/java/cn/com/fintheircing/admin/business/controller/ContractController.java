package cn.com.fintheircing.admin.business.controller;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.service.ContractService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Api("管理员对合约管理的controller")
@RequestMapping("/admin/contract")
/**
 * ContractController class
 *
 * @author yaoxiong
 * @date 2019/2/20
 */
public class ContractController {
    @Resource
    private ContractService contractService;
    @ApiOperation(value = "查看所有合约记录和合约详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/findAllContact")
    public ResponseModel findAllContact(@ModelAttribute UserTokenInfo userInfo,String productStr){
        return ResponseModel.sucess("",contractService.findAllContact(productStr));
    }
    @ApiOperation(value = "合约详情-提交申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/applyList")
    public ResponseModel applyList(@ModelAttribute UserTokenInfo userInfo, ContractControlModel model){
        return ResponseModel.sucess("",contractService.applyList(userInfo,model));
    }
    @ApiOperation(value = "合约权益调整", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateContact")
    public ResponseModel updateContact(@ModelAttribute UserTokenInfo userInfo,String contractId){
        return ResponseModel.sucess("",contractService.updateContact(contractId));
    }
    @ApiOperation(value = "合约权益调整-添加申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveStockInContact")
    public ResponseModel saveStockInContact(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) throws BusinessException {
        model.setApplyType("增加");
        return ResponseModel.sucess("",contractService.oparteStockInContact(userInfo,model));
    }
    @ApiOperation(value = "合约权益调整-删除申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/deleteStockInContact")
    public ResponseModel deleteStockInContact(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) throws BusinessException {
        model.setApplyType("删除");
        return ResponseModel.sucess("",contractService.oparteStockInContact(userInfo,model));
    }
    @ApiOperation(value = "合约权益调整-删除申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateStockInContact")
    public ResponseModel updateStockInContact(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEntrustModel model) throws BusinessException {
        model.setApplyType("修改");
        return ResponseModel.sucess("",contractService.oparteStockInContact(userInfo,model));
    }
}