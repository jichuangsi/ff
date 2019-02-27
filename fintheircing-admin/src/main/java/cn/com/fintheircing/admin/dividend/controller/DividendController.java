package cn.com.fintheircing.admin.dividend.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.model.ValidateModel;
import cn.com.fintheircing.admin.dividend.exception.DividendException;
import cn.com.fintheircing.admin.dividend.model.DividendControlModel;
import cn.com.fintheircing.admin.dividend.model.DividendHoldingModel;
import cn.com.fintheircing.admin.dividend.model.DividendModel;
import cn.com.fintheircing.admin.dividend.service.DividendService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dividend")
@Api("除权除息控制")
@CrossOrigin
public class DividendController {
    @Resource
    private DividendService dividendService;

    @ApiOperation(value = "添加除权除息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveDividend")
    public ResponseModel saveDividend(@ModelAttribute UserTokenInfo userInfo,@ApiParam("yyyyMMdd") @RequestBody DividendModel model){
        try {
            dividendService.saveDividend(userInfo,model);
        } catch (DividendException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "审核除权除息列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getDividendList")
    public ResponseModel<PageInfo<DividendModel>> getDividendList(@ModelAttribute UserTokenInfo userInfo,@RequestParam("index") int index,@RequestParam("size") int size){
        return ResponseModel.sucess("",dividendService.getDividendList(index,size));
    }

    @ApiOperation(value = "获取详细除权除息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getDividendDetail")
    public ResponseModel<DividendModel> getDividendDetail(@ModelAttribute UserTokenInfo userInfo,@RequestParam("id") String id){
        return ResponseModel.sucess("",dividendService.getDividendDetail(id));
    }

    @ApiOperation(value = "审核除权除息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/validateDividend")
    public ResponseModel validateDividend(@ModelAttribute UserTokenInfo userInfo, @RequestBody ValidateModel model){
        try {
            dividendService.validateDividend(userInfo,model);
        } catch (DividendException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }

    @ApiOperation(value = "查看审核操作记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getValidateRecord")
    public ResponseModel<PageInfo<DividendControlModel>> getValidateRecord(@ModelAttribute UserTokenInfo userInfo,@RequestBody DividendControlModel model){
        try {
            return ResponseModel.sucess("",dividendService.getValidateRecord(userInfo,model));
        } catch (DividendException e) {
            return ResponseModel.fail("",e.getMessage());
        }
    }

    @ApiOperation(value = "通过股票代码搜索合约持有", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getContractHolding")
    public ResponseModel<PageInfo<DividendHoldingModel>> getContractHolding(@ModelAttribute UserTokenInfo userInfo, @RequestParam("index") int index,
                                                                            @RequestParam("size") int size, @RequestParam("keyWord") String keyWord){
        return ResponseModel.sucess("",dividendService.getContractHolding(index,size,keyWord));
    }
}
