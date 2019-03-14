package cn.com.fintheircing.admin.business.controller;

import cn.com.fintheircing.admin.business.constant.ApplyStatus;
import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractControlMapper;
import cn.com.fintheircing.admin.business.entity.record.StockEquityRecord;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.record.ContractEquityModel;
import cn.com.fintheircing.admin.business.model.record.StockEquityModel;
import cn.com.fintheircing.admin.business.service.ContractService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/contract")
@Api("管理员对Contract的controller")
@CrossOrigin
public class ContractController {
    @Resource
    private ContractService contractService;
    @Resource
    private IBusinessContractControlMapper iBusinessContractControlMapper;
    @ApiOperation(value = "合约信息-查看所有合约", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping(value = "/QueryAllContact")
    public ResponseModel QueryAllContact(@ModelAttribute UserTokenInfo userInfo,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                         @RequestParam(value = "productStr",required = false)String productStr) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContractControlModel> allContact = iBusinessContractControlMapper.findAllContact(productStr);
        for (ContractControlModel model :allContact){
            model.setNetAssets(model.getPromisedMoney());
            model.setTotalAssets(model.getLessMoney()+model.getPromisedMoney()+model.getStockAmount()*model.getCurrentWorth());
            model.setVerifyStr(BusinessStatus.getName(model.getVerifyStatus()));
            model.setLessTime((model.getExpiredTime() - model.getBorrowTime().getTime()) / 1000 / 60 / 60 / 24);

        }
        PageInfo<ContractControlModel> pageInfo=new PageInfo<ContractControlModel>(allContact);
        return ResponseModel.sucess("",pageInfo);
    }
    @ApiOperation(value = "合约信息-合约详情-修改提交", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/ContactDetails")
    public ResponseModel ContactDetails(@ModelAttribute UserTokenInfo userInfo,
                                        @RequestBody ContractControlModel model) {

       if (contractService.ContactDetails(userInfo,model)){
           return ResponseModel.sucessWithEmptyData("");
       }
       return ResponseModel.fail("");
    }
    @ApiOperation(value = "合约信息-合约权益调整-查看合约的股票", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/deleteStock")
    public ResponseModel deleteStock(@ModelAttribute UserTokenInfo userInfo,@Param("contractId") String contractId) {
        return ResponseModel.sucess("",iBusinessContractControlMapper.findAllStock(contractId));

    }
    @ApiOperation(value = "合约信息-合约权益调整-申请增加股票", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/applyCreateStock")
    public ResponseModel applyCreateStock(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEquityModel model) {
        model.setFalg(ApplyStatus.APPLY_INSERT.getNum());
        return ResponseModel.sucess("", contractService.applyUpdateStock(userInfo, model));
    }
    @ApiOperation(value = "合约信息-合约权益调整-申请减少股票", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/applyDeleteStock")
    public ResponseModel applyDeleteStock(@ModelAttribute UserTokenInfo userInfo, @RequestBody StockEquityModel model) {
        model.setFalg(ApplyStatus.APPLY_REMOVE.getNum());
        return ResponseModel.sucess("", contractService.applyUpdateStock(userInfo, model));
    }
    @ApiOperation(value = "审核申请-合约操作-查看所有申请合约", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/QueryApplyList")
    public ResponseModel QueryApplyList(@ModelAttribute UserTokenInfo userInfo) {
        return ResponseModel.sucess("",contractService.QueryApplyList());

    }
    @ApiOperation(value = "审核申请-合约操作-同意申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/agreeApply")
    public ResponseModel agreeApply(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractEquityModel model)throws BusinessException  {
        return ResponseModel.sucess("",contractService.agreeApply(model));

    }
    @ApiOperation(value = "审核申请-合约操作-驳回申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/disagreeApply")
    public ResponseModel disagreeApply(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractEquityModel model)throws BusinessException  {
        return ResponseModel.sucess("",contractService.disagreeApply(model));
    }
    @ApiOperation(value = "审核申请-合约权益调整-查看合约申请列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/queryContractList")
    public ResponseModel queryContractList(@ModelAttribute UserTokenInfo userInfo)throws BusinessException  {
        return ResponseModel.sucess("",contractService.queryContractList());
    }
    @ApiOperation(value = "审核申请-合约权益调整-同意添加或删除股票", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/agereeCreateStock")
    public ResponseModel agereeCreateStock(@ModelAttribute UserTokenInfo userInfo,@RequestBody StockEquityModel model )throws BusinessException  {
        return ResponseModel.sucess("",contractService.agereeCreateStock(model));
    }
    @ApiOperation(value = "审核申请-合约权益调整-驳回添加或删除股票", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/disAgereeCreateStock")
    public ResponseModel disAgereeCreateStock(@ModelAttribute UserTokenInfo userInfo,@RequestBody StockEquityModel model )throws BusinessException  {
        return ResponseModel.sucess("",contractService.disAgereeCreateStock(model));
    }

}