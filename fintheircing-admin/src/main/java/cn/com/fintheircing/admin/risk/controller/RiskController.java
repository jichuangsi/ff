package cn.com.fintheircing.admin.risk.controller;

import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.risk.exception.RiskException;
import cn.com.fintheircing.admin.risk.model.DangerousStockModel;
import cn.com.fintheircing.admin.risk.model.RiskContractModel;
import cn.com.fintheircing.admin.risk.model.RiskControlModel;
import cn.com.fintheircing.admin.risk.model.RiskModel;
import cn.com.fintheircing.admin.risk.service.RiskService;
import cn.com.fintheircing.admin.taxation.exception.TaxationException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/risk")
@RestController
@Api("控制风险")
@CrossOrigin
public class RiskController {

    @Resource
    private RiskService riskService;

    @ApiOperation(value = "一键平仓", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/oneKeyPush")
    public ResponseModel oneKeyPush(@ModelAttribute UserTokenInfo userInfo, @RequestBody IdModel ids) {
        try {
            riskService.oneKeyAbort(ids);
        } catch (TaxationException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "风险控制分页", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getPageRiskControl")
    public ResponseModel<PageInfo<RiskContractModel>> getPageRiskControl(@ModelAttribute UserTokenInfo userInfo, @RequestBody RiskControlModel model){
        return ResponseModel.sucess("",riskService.getPageRiskContract(model));
    }

    @ApiOperation(value = "高危股票列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getDangerousStockList/{orderBy}")
    public ResponseModel<PageInfo<DangerousStockModel>> getDangourStockList(@ModelAttribute UserTokenInfo userInfo,
                                                                            @RequestParam(value = "keyWord",required = false) String keyWord,@PathVariable(value = "orderBy") Integer orderBy,
                                                                            @RequestParam("index") Integer index,@RequestParam("size") Integer size){
        try {
            return ResponseModel.sucess("",riskService.getPageDangerousStock(keyWord,index,size,orderBy));
        } catch (RiskException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "移入黑名单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/toBlackOrder")
    public ResponseModel toBlackOrder(@ModelAttribute UserTokenInfo userInfo,@RequestBody IdModel model){
        try {
            riskService.updateBlackOrder(model);
        } catch (RiskException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return  ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "高危合约清单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getDangerousContract/{warnRate1}/{warnRate2}/{orderBy}")
    public ResponseModel<PageInfo<RiskContractModel>> getDangerousContract(@ModelAttribute UserTokenInfo userInfo,@RequestParam(value = "keyWord",required = false) String keyWord,
                                              @PathVariable(value = "warnRate1",required = false) Double warnRate1,@PathVariable(value = "warnRate2",required = false) Double warnRate2,
                                              @RequestParam("index") Integer index,@RequestParam("size") Integer size,@PathVariable("orderBy") Integer orderBy){
        try {
            return ResponseModel.sucess("",riskService.getDangerousContract(keyWord,index,size,orderBy,warnRate1,warnRate2));
        } catch (RiskException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "获取风险策略", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getSystemRisk")
    public ResponseModel<RiskModel> getSystemRisk(@ModelAttribute UserTokenInfo userInfo){
        return  ResponseModel.sucess("",riskService.getRiskModel());
    }

    @ApiOperation(value = "修改风险策略", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/updateSystemRisk")
    public ResponseModel updateSystemRisk(@ModelAttribute UserTokenInfo userInfo,@RequestBody RiskModel model){
        riskService.updateSystemRisk(model);
        return ResponseModel.sucessWithEmptyData("");
    }

}
