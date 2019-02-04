
package cn.com.fintheircing.customer.common.feign.controller;

import cn.com.fintheircing.customer.business.exception.BusinessException;
import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.service.BusinessService;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 */
@RestController
@Api("/对外开放的user")
@RequestMapping("/Cu")
public class UserInterfaceForInnerController {
    @Resource
    private IPayMapper iPayMapper;
    @Resource
    private LoginService loginService;
    @Resource
    private BusinessService businessService;

    @RequestMapping("/findAllPayInfo")
    public List<RecodeInfoPayModel> findAllPayInfo() {
        return iPayMapper.findAllPayInfo();
    }

    @RequestMapping("/viewOnline")
    public List<OnlineUserInfo> viewOnline() {
        return loginService.viewOnline();
    }

    @RequestMapping("/isOut")
    public ResponseModel isOut(String id) {
        try {
            if (loginService.outOnline(id)) {
                return ResponseModel.sucessWithEmptyData("");
            }
        } catch (Exception e) {
            return ResponseModel.fail("", e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "强平剩余折现", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/rudeEndContract")
    public ResponseModel<String> rudeEndContract(@RequestBody ContractModel model){
        try {
            businessService.rudeEndContract(model);
        } catch (BusinessException e) {
            return ResponseModel.fail("",e.getMessage());
        }
        return ResponseModel.sucessWithEmptyData("");
    }
}
