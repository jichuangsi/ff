package cn.com.fintheircing.admin.business.controller;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.record.StockEquityModel;
import cn.com.fintheircing.admin.business.service.ContractService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
@Api("管理员对合约管理的controller")
@RequestMapping("/admin/contract")
public class ContractController {
    @Resource
    private ContractService contractService;

    @ApiOperation(value = "查看所有合约记录和合约详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/findAllContact")
    public ResponseModel findAllContact(@ModelAttribute UserTokenInfo userInfo, @RequestParam("productStr") String productStr) {
        return ResponseModel.sucess("", contractService.findAllContact(productStr));
    }

    @ApiOperation(value = "合约详情-提交申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/applyList")
    public ResponseModel applyList(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractControlModel model) {
        return ResponseModel.sucess("", contractService.applyList(userInfo, model));
    }

    @ApiOperation(value = "合约权益调整", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/updateContact")
    public ResponseModel updateContact(@ModelAttribute UserTokenInfo userInfo, @RequestParam("contractId") String contractId) {
        return ResponseModel.sucess("", contractService.updateContact(contractId));
    }

    @ApiOperation(value = "合约权益调整-调整股份申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveContractEquity")
    public ResponseModel deleteStockInContact(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEquityModel model){
        try {
            contractService.saveStockRecode(userInfo,model);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "合约权益调整-调整股份相关信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getEquityHolding")
    public ResponseModel<PageInfo<StockHoldingModel>> getEquityHolding(@ModelAttribute UserTokenInfo userInfo,@RequestParam("contractId") String contractId,
                                                                       @RequestParam("index") int index,@RequestParam("size") int size){
        return ResponseModel.sucess("",contractService.getEquityHolding(contractId,index,size));
    }

    @ApiOperation(value = "获取调整股份时查询条件", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getEquityCondition")
    public ResponseModel<Map<String,Object>> getEquityCondition(@ModelAttribute UserTokenInfo userInfo, @RequestParam("contractId") String contractId){
        return ResponseModel.sucess("",contractService.getContractCondition(contractId));
    }

    @ApiOperation(value = "获取待审核合约权益列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getEquityValidateList")
    public ResponseModel<PageInfo<StockEquityModel>> getEquityValidateList(@ModelAttribute UserTokenInfo userInfo,@RequestParam("index") int index,@RequestParam("size") int size){
        return ResponseModel.sucess("",contractService.getEquityValidateList(index,size));
    }

    @ApiOperation(value = "审核合约股份权益", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/validateContractStock")
    public ResponseModel validateContractStock(@ModelAttribute UserTokenInfo userInfo,@RequestParam("equityId") String equityId,@RequestParam("result") Integer result){
        try {
            contractService.validateContractStock(userInfo,equityId,result);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }
}