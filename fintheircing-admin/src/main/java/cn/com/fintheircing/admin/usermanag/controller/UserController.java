package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.AskMoneyInfoModel;
import cn.com.fintheircing.admin.usermanag.model.ContactInfoModel;
import cn.com.fintheircing.admin.usermanag.model.MesModel;
import cn.com.fintheircing.admin.feign.IMsgFeignService;
import cn.com.fintheircing.admin.usermanag.service.IUserService;
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
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    IMsgFeignService imesgService;
    @ApiOperation(value = "用户列表-查询所有用户信息", notes = "")
    @PostMapping("/findAll")
    public ResponseModel<PageInfo<AdminClientInfoModel>> findAllUserInfo(@RequestBody AdminClientInfoModel queryModel, int pageNum, int pageSize) throws UserServiceException {
        PageHelper.startPage(pageNum,pageSize);
        List<AdminClientInfoModel> allUserInfo = userService.findAllUserInfo(queryModel);
        PageInfo<AdminClientInfoModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }

    @ApiOperation(value = "用户列表-根据Id冻结账户", notes = "")
    @PostMapping("/changeStatus")
    public ResponseModel<AdminClientInfoModel> changeStatus(String id)throws UserServiceException {
        return ResponseModel.sucess("",userService.changeStatus(id));
    }
    @ApiOperation(value = "冻结账户-根据Id恢复账户", notes = "")
    @GetMapping("/returnStatus")
    public ResponseModel<AdminClientInfoModel> returnStatus(String id)throws UserServiceException{
        return ResponseModel.sucess("",userService.returnStatus(id));
    }
    @ApiOperation(value = "冻结账户-查询冻结账户", notes = "")
    @PostMapping("/findByOption")
    public ResponseModel<PageInfo<AdminClientInfoModel>> findByOption(AdminClientInfoModel queryModel, int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<AdminClientInfoModel> allUserInfo = userService.findByOption(queryModel);
        PageInfo<AdminClientInfoModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "用户列表-基本信息-修改所属代理", notes = "")
    @PostMapping("/changeProxyNum")
    public ResponseModel<AdminClientInfoModel> changeProxyNum(AdminClientInfoModel Model )throws UserServiceException{
        AdminClientInfoModel adminClientInfoModel = userService.changeProxyNum(Model);
        return ResponseModel.sucess("", adminClientInfoModel);
    }
    @ApiOperation(value = "用户列表-合约信息-合约记录", notes = "")
    @PostMapping("/contactRecode")
    public ResponseModel<PageInfo<ContactInfoModel>> contactRecode(String goodsType , int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<ContactInfoModel> contactInfoModels = userService.contactRecode(goodsType);
        PageInfo<ContactInfoModel> personPageInfo = new PageInfo<>(contactInfoModels);
        return ResponseModel.sucess("",personPageInfo);
    }
//    @ApiOperation(value = "资金申请-入金申请查询", notes = "")
//    @PostMapping("/insideMoney")
//    public ResponseModel<PageInfo<AskMoneyInfoModel>> insideMoney(AskMoneyInfoModel Model ,int pageNum, int pageSize)throws UserServiceException{
//        PageHelper.startPage(pageNum,pageSize);
//        List<AskMoneyInfoModel> askMoneyInfoModels = userService.insideMoney(Model);
//        PageInfo<AskMoneyInfoModel> personPageInfo = new PageInfo<>(askMoneyInfoModels);
//        return ResponseModel.sucess("",personPageInfo);
//    }
    @ApiOperation(value = "资金申请-手动修改金额", notes = "")
    @PostMapping("/changeMoneyBySelf")
    public ResponseModel<AskMoneyInfoModel> changeMoneyBySelf(AskMoneyInfoModel Model )throws UserServiceException{
        AskMoneyInfoModel askMoneyInfoModels = userService.changeMoneyBySelf(Model);
        return ResponseModel.sucess("",askMoneyInfoModels);
    }
    @ApiOperation(value = "资金申请-入金申请-入金处理", notes = "")
    @PostMapping("/insideMoneyHandel")
    public ResponseModel<AskMoneyInfoModel> insideMoneyHandel(AskMoneyInfoModel Model )throws UserServiceException{
        AskMoneyInfoModel askMoneyInfoModels = userService.insideMoneyHandel(Model);
        return ResponseModel.sucess("",askMoneyInfoModels);
    }
//    @ApiOperation(value = "银行卡管理-银行卡全部信息", notes = "")
//    @PostMapping("/changeProxyNum")
//    public ResponseModel<PageInfo<AskMoneyInfoModel>> bankCard(AskMoneyInfoModel Model ,int pageNum, int pageSize)throws UserServiceException{
//        PageHelper.startPage(pageNum,pageSize);
//        List<AskMoneyInfoModel> askMoneyInfoModels = userService.bankCard(Model);
//        PageInfo<AskMoneyInfoModel> personPageInfo = new PageInfo<>(askMoneyInfoModels);
//        return ResponseModel.sucess("",personPageInfo);
//    }
    @ApiOperation(value = "银行卡管理-银行卡信息-银行卡解绑", notes = "")
    @PostMapping("/updatebankCard")
    public ResponseModel<AskMoneyInfoModel> updatebankCard(AskMoneyInfoModel Model )throws UserServiceException{
        AskMoneyInfoModel askMoneyInfoModels = userService.updatebankCard(Model);
        return ResponseModel.sucess("",askMoneyInfoModels);
    }

    @ApiOperation(value = "用户列表-短信记录", notes = "")
    @PostMapping("/findAllMessage")
    public ResponseModel<PageInfo<MesModel>> findAllMessage(int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<MesModel> allMessage = imesgService.findAllMessage();
        PageInfo<MesModel> personPageInfo = new PageInfo<>(allMessage);
        return ResponseModel.sucess("",personPageInfo);
    }

}
