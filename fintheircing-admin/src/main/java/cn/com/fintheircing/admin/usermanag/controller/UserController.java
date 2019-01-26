package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.feign.IMsgFeignService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IAdminRecodingMapper;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.BankCardModel;
import cn.com.fintheircing.admin.usermanag.model.MesModel;
import cn.com.fintheircing.admin.usermanag.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@RestController
@Api("userController相关控制层")
@CrossOrigin
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
     private IMsgFeignService imesgService;
    @Resource
    private IAdminRecodingMapper iAdminRecodingMapper;
    @ApiOperation(value = "用户列表-查询所有用户信息", notes = "")
    @PostMapping("/findAll")
    public ResponseModel<PageInfo<AdminClientInfoModel>> findAllUserInfo(@RequestBody AdminClientInfoModel queryModel) throws UserServiceException {
        PageHelper.startPage(queryModel.getPageNum(),queryModel.getPageSize());
        List<AdminClientInfoModel> allUserInfo = userService.findAllUserInfo(queryModel);
        PageInfo<AdminClientInfoModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "用户列表-根据Id冻结账户", notes = "")
    @PostMapping("/changeStatus")
    public ResponseModel changeStatus(String id)throws UserServiceException {
        return ResponseModel.sucess("",userService.changeStatus(id));
    }
    @ApiOperation(value = "冻结账户-根据Id恢复账户", notes = "")
    @GetMapping("/returnStatus")
    public ResponseModel returnStatus(String id)throws UserServiceException{
        return ResponseModel.sucess("",userService.returnStatus(id));
    }
    @ApiOperation(value = "冻结账户-查询冻结账户", notes = "")
    @PostMapping("/findByOption")
    public ResponseModel<PageInfo<AdminClientInfoModel>> findByOption(@RequestBody AdminClientInfoModel queryModel)throws UserServiceException{
        PageHelper.startPage(queryModel.getPageNum(),queryModel.getPageSize());
        List<AdminClientInfoModel> allUserInfo = userService.findByOption(queryModel);
        PageInfo<AdminClientInfoModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }

    @ApiOperation(value = "用户列表-查看详情-修改所属代理", notes = "")
    @PostMapping("/changeProxyNum")
    public ResponseModel changeProxyNum(@ModelAttribute UserTokenInfo userInfo,String userId, String proxyId )throws UserServiceException{
        return ResponseModel.sucess("", userService.changeProxyNum(userId,proxyId));
    }

    @ApiOperation(value = "用户列表-基本信息-查看详情", notes = "")
    @PostMapping("/findAllDetails")
    public ResponseModel findAllDetails( String id)throws UserServiceException{
        return ResponseModel.sucess("", userService.findAllDetails(id));
    }

    @ApiOperation(value = "用户列表-查看详情-修改余额", notes = "")
    @PostMapping("/changeAmount")
    public ResponseModel changeAmount( String id,double amount)throws UserServiceException{
        return ResponseModel.sucess("", userService.changeAmount(id,amount));
    }
    @ApiOperation(value = "银行卡管理-银行卡全部信息", notes = "")
    @PostMapping("/bankCard")
    public ResponseModel<PageInfo<BankCardModel>> bankCard(BankCardModel Model , int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<BankCardModel> askMoneyInfoModels = userService.findAllBankCard(Model);
        PageInfo<BankCardModel> personPageInfo = new PageInfo<>(askMoneyInfoModels);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "银行卡管理-银行卡信息-银行卡解绑", notes = "")
    @PostMapping("/updatebankCard")
    public ResponseModel updatebankCard(String id )throws UserServiceException{
        return ResponseModel.sucess("",userService.updatebankCard(id));
    }

    @ApiOperation(value = "管理员-短信记录", notes = "")
    @PostMapping("/findAllMessage")
    public ResponseModel<PageInfo<MesModel>> findAllMessage(int pageNum, int pageSize)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<MesModel> allMessage = imesgService.findAllMessage();
        PageInfo<MesModel> personPageInfo = new PageInfo<>(allMessage);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "个人用户-短信记录", notes = "")
    @PostMapping ("/findAllMesByUserId")
    public ResponseModel<PageInfo<MesModel>> findAllMesByUserId(int pageNum, int pageSize, String id)throws UserServiceException{
        PageHelper.startPage(pageNum,pageSize);
        List<MesModel> allMessage = iAdminRecodingMapper.findAllMesByUserId(id);
        PageInfo<MesModel> personPageInfo = new PageInfo<>(allMessage);
        return ResponseModel.sucess("",personPageInfo);
    }

}
