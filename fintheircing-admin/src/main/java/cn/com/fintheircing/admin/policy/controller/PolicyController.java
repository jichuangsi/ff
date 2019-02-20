package cn.com.fintheircing.admin.policy.controller;

import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.RoleCodes;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.model.EnumTypeModel;
import cn.com.fintheircing.admin.policy.model.TranferContractModel;
import cn.com.fintheircing.admin.policy.service.PolicyService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/policy")
@Api("策略controller")
@CrossOrigin
public class PolicyController {
    @Resource
    private PolicyService policyService;


    @ApiOperation(value = "查看合约记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getPolicyInfo")
    public ResponseModel<PageInfo<TranferContractModel>> getPolicyInfo(@ModelAttribute UserTokenInfo userInfo, @RequestBody TranferContractModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if (model.getPageIndex()==null||model.getPageIndex()==0
                ||model.getPageSize()==null||model.getPageSize()==0){
            return ResponseModel.fail("",ResultCode.PARAM_MISS_MSG);
        }
        return ResponseModel.sucess("",policyService.getPageContractInfo(model));
    }

    @ApiOperation(value = "查看合约操作记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getPolicyControlInfo")
    public ResponseModel<PageInfo<ContractControlModel>> getPolicyControlInfo(@ModelAttribute UserTokenInfo userInfo, @RequestBody ContractControlModel model){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        if (model.getPageIndex()==null||model.getPageIndex()==0
                ||model.getPageSize()==null||model.getPageSize()==0){
            return ResponseModel.fail("",ResultCode.PARAM_MISS_MSG);
        }
        return ResponseModel.sucess("",policyService.getPageContractControl(model));
    }

    @ApiOperation(value = "获取操作", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getControlTypes")
    public ResponseModel<List<EnumTypeModel>> getControlTypes(@ModelAttribute UserTokenInfo userInfo){
        if(!IsManage(userInfo)){
            return ResponseModel.fail("", ResultCode.POWER_VISIT_ERR);
        }
        return ResponseModel.sucess("",policyService.getAllControlType());
    }


    @ApiOperation(value = "获取合约状态", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getContractStatusAndProduct")
    public ResponseModel<Map<String,Object>> getContractStatusAndProduct(@ModelAttribute UserTokenInfo userInfo){
        return ResponseModel.sucess("",policyService.getContractStatusAndProduct());
    }

    private Boolean IsManage(UserTokenInfo userInfo){
        if (RoleCodes.ROLE_KEY_STRING.get("M").equals(userInfo.getRoleGrade())){
            return true;
        }
        return false;
    }
}
