package cn.com.fintheircing.admin.UserManag.controller;


import cn.com.fintheircing.admin.UserManag.Excption.UserServiceException;
import cn.com.fintheircing.admin.UserManag.model.AdminClientInfModel;
import cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel;
import cn.com.fintheircing.admin.UserManag.model.ContactInfoModel;
import cn.com.fintheircing.admin.UserManag.model.mesModel;
import cn.com.fintheircing.admin.UserManag.service.ImesgService;
import cn.com.fintheircing.admin.UserManag.service.UserService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
@RestController
@Api("userController相关控制层")
public class userController {

    @Resource
    private UserService userService;
    @Resource
    ImesgService imesgService;
    @ApiOperation(value = "用户列表-查询所有用户信息", notes = "")
    @PostMapping("/findAll")
    public ResponseModel<PageInfo<AdminClientInfModel>> findAllUserInfo(@RequestBody AdminClientInfModel queryModel, int pageNum, int pageSize) throws UserServiceException {
        PageHelper.startPage(pageNum,pageSize);
        List<AdminClientInfModel> allUserInfo = userService.findAllUserInfo(queryModel);
        PageInfo<AdminClientInfModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }

    @ApiOperation(value = "用户列表-根据Id冻结账户", notes = "")
    @PostMapping("/changeStatus")
    public ResponseModel<AdminClientInfModel> changeStatus(String id)throws UserServiceException{
        return ResponseModel.sucess("",userService.changeStatus(id));
    }
    @ApiOperation(value = "冻结账户-根据Id恢复账户", notes = "")
    @GetMapping("/returnStatus")
    public ResponseModel<AdminClientInfModel> returnStatus(String id)throws UserServiceException{
        return ResponseModel.sucess("",userService.returnStatus(id));
    }
    @ApiOperation(value = "冻结账户-查询冻结账户", notes = "")
    @PostMapping("/findByOption")
    public ResponseModel<PageInfo<AdminClientInfModel>> findByOption(AdminClientInfModel queryModel,int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<AdminClientInfModel> allUserInfo = userService.findByOption(queryModel);
        PageInfo<AdminClientInfModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "用户列表-基本信息-修改所属代理", notes = "")
    @PostMapping("/changeProxyNum")
    public ResponseModel<AdminClientInfModel> changeProxyNum(AdminClientInfModel Model )throws UserServiceException{
        AdminClientInfModel adminClientInfModel = userService.changeProxyNum(Model);
        return ResponseModel.sucess("",adminClientInfModel);
    }
    @ApiOperation(value = "用户列表-合约信息-合约记录", notes = "")
    @PostMapping("/contactRecode")
    public ResponseModel<PageInfo<ContactInfoModel>> contactRecode(String goodsType ,int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<ContactInfoModel> contactInfoModels = userService.contactRecode(goodsType);
        PageInfo<ContactInfoModel> personPageInfo = new PageInfo<>(contactInfoModels);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "资金申请-入金申请查询", notes = "")
    @PostMapping("/insideMoney")
    public ResponseModel<PageInfo<AskMoneyInfoModel>> insideMoney(AskMoneyInfoModel Model ,int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<AskMoneyInfoModel> askMoneyInfoModels = userService.insideMoney(Model);
        PageInfo<AskMoneyInfoModel> personPageInfo = new PageInfo<>(askMoneyInfoModels);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "资金申请-手动修改金额", notes = "")
    @PostMapping("/changeMoneyBySelf")
    public ResponseModel<AskMoneyInfoModel> changeMoneyBySelf(AskMoneyInfoModel Model )throws UserServiceException{
        AskMoneyInfoModel askMoneyInfoModels = userService.changeMoneyBySelf(Model);
        return ResponseModel.sucess("",askMoneyInfoModels);
    }
    @ApiOperation(value = "资金申请-入金申请-入金处理", notes = "")
    @PostMapping("/changeMoneyBySelf")
    public ResponseModel<AskMoneyInfoModel> insideMoneyHandel(AskMoneyInfoModel Model )throws UserServiceException{
        AskMoneyInfoModel askMoneyInfoModels = userService.insideMoneyHandel(Model);
        return ResponseModel.sucess("",askMoneyInfoModels);
    }
    @ApiOperation(value = "银行卡管理-银行卡全部信息", notes = "")
    @PostMapping("/changeProxyNum")
    public ResponseModel<PageInfo<AskMoneyInfoModel>> bankCard(AskMoneyInfoModel Model ,int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<AskMoneyInfoModel> askMoneyInfoModels = userService.bankCard(Model);
        PageInfo<AskMoneyInfoModel> personPageInfo = new PageInfo<>(askMoneyInfoModels);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "银行卡管理-银行卡信息-银行卡解绑", notes = "")
    @PostMapping("/changeProxyNum")
    public ResponseModel<AskMoneyInfoModel> updatebankCard(AskMoneyInfoModel Model )throws UserServiceException{
        AskMoneyInfoModel askMoneyInfoModels = userService.updatebankCard(Model);
        return ResponseModel.sucess("",askMoneyInfoModels);
    }

    @ApiOperation(value = "用户列表-短信记录", notes = "")
    @PostMapping("/findAllMessage")
    public ResponseModel<PageInfo<mesModel>> findAllMessage(int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<mesModel> allMessage = imesgService.findAllMessage();
        PageInfo<mesModel> personPageInfo = new PageInfo<>(allMessage);
        return ResponseModel.sucess("",personPageInfo);
    }

}
