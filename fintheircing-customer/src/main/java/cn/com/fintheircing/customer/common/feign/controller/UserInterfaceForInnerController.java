
package cn.com.fintheircing.customer.common.feign.controller;

import cn.com.fintheircing.customer.business.exception.BusinessException;
import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.service.BusinessService;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.repository.IUserMesInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserMesInfo;
import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.mes.MesInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.service.LoginService;
import cn.com.fintheircing.customer.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Resource
    private IUserMesInfoRepository iUserMesInfoRepository;
    @Resource
    private UserService userService;

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
    @RequestMapping("/updateMesInfo")
    public ResponseModel sendMesg(@RequestBody MesInfoModel model) {
        UserMesInfo u =new UserMesInfo();
        u.setContent(model.getContent());
        u.setTitle(model.getTitle());
        UserMesInfo save = iUserMesInfoRepository.save(u);
        if (StringUtils.isEmpty(save)){
            return ResponseModel.fail("", "发送失败");
        }
        return ResponseModel.sucessWithEmptyData("");
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

    @ApiOperation(value = "根据id获取user", notes = "")
    @ApiImplicitParams({})
    @RequestMapping(value = "/findUserByUserId")
    UserTokenInfo findUserByUserId(@RequestParam("userId") String userId){
        return  userService.findUserByUserId(userId);
    }
}
