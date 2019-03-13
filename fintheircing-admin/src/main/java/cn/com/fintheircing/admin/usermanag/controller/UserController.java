package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.tranfer.TranferEntrustModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.feign.IMsgFeignService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IAdminRecodingMapper;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IMesInfoRepository;
import cn.com.fintheircing.admin.usermanag.entity.MesInfo;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.BankCardModel;
import cn.com.fintheircing.admin.usermanag.model.UserStockHoldingModel;
import cn.com.fintheircing.admin.usermanag.model.ｍes.MesInfoModel;
import cn.com.fintheircing.admin.usermanag.model.ｍes.MesModel;
import cn.com.fintheircing.admin.usermanag.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    private IMesInfoRepository iMesInfoRepository;
    @Resource
    private ICustomerFeignService iCustomerFeignService;

    @ApiOperation(value = "用户列表-查询所有用户信息", notes = "")
    @PostMapping("/findAll")
    public ResponseModel<PageInfo<AdminClientInfoModel>> findAllUserInfo(@RequestBody AdminClientInfoModel queryModel) throws UserServiceException {
        PageHelper.startPage(queryModel.getPageNum(), queryModel.getPageSize());
        List<AdminClientInfoModel> allUserInfo = userService.findAllUserInfo(queryModel);
        PageInfo<AdminClientInfoModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("", personPageInfo);
    }

    @ApiOperation(value = "用户列表-根据Id冻结账户", notes = "")
    @PostMapping("/changeStatus")
    public ResponseModel changeStatus(String id) throws UserServiceException {
        return ResponseModel.sucess("", userService.changeStatus(id));
    }

    @ApiOperation(value = "冻结账户-根据Id恢复账户", notes = "")
    @GetMapping("/returnStatus")
    public ResponseModel returnStatus(String id) throws UserServiceException {
        return ResponseModel.sucess("", userService.returnStatus(id));
    }

    @ApiOperation(value = "冻结账户-查询冻结账户", notes = "")
    @PostMapping("/findByOption")
    public ResponseModel<PageInfo<AdminClientInfoModel>> findByOption(@RequestBody AdminClientInfoModel queryModel) throws UserServiceException {
        PageHelper.startPage(queryModel.getPageNum(), queryModel.getPageSize());
        List<AdminClientInfoModel> allUserInfo = userService.findByOption(queryModel);
        PageInfo<AdminClientInfoModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("", personPageInfo);
    }

    @ApiOperation(value = "用户列表-查看详情-修改所属代理", notes = "")
    @PostMapping("/changeProxyNum")
    public ResponseModel changeProxyNum(@ModelAttribute UserTokenInfo userInfo, @RequestParam("userId") String userId, @RequestParam("proxyId") String proxyId) throws UserServiceException {
        return ResponseModel.sucess("", userService.changeProxyNum(userId, proxyId));
    }

    @ApiOperation(value = "用户列表-基本信息-查看详情", notes = "")
    @PostMapping("/findAllDetails")
    public ResponseModel findAllDetails(String id) throws UserServiceException {
        return ResponseModel.sucess("", userService.findAllDetails(id));
    }

    @ApiOperation(value = "用户列表-查看详情-修改余额", notes = "")
    @PostMapping("/changeAmount")
    public ResponseModel changeAmount(@RequestParam("id") String id, @RequestParam("amount") double amount) throws UserServiceException {
        return ResponseModel.sucess("", userService.changeAmount(id, amount));
    }

    @ApiOperation(value = "银行卡管理-银行卡全部信息", notes = "")
    @PostMapping("/bankCard")
    public ResponseModel<PageInfo<BankCardModel>> bankCard(@RequestBody BankCardModel Model) throws UserServiceException {
        PageHelper.startPage(Model.getPageNum(), Model.getPageSize());
        List<BankCardModel> askMoneyInfoModels = userService.findAllBankCard(Model);
        PageInfo<BankCardModel> personPageInfo = new PageInfo<>(askMoneyInfoModels);
        return ResponseModel.sucess("", personPageInfo);
    }

    @ApiOperation(value = "银行卡管理-银行卡信息-银行卡解绑", notes = "")
    @PostMapping("/updatebankCard")
    public ResponseModel updatebankCard(String id) throws UserServiceException {
        return ResponseModel.sucess("", userService.updatebankCard(id));
    }

    @ApiOperation(value = "管理员-短信记录", notes = "")
    @PostMapping("/findAllMessage")
    public ResponseModel<PageInfo<MesModel>> findAllMessage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) throws UserServiceException {
        PageHelper.startPage(pageNum, pageSize);
        List<MesModel> allMessage = imesgService.findAllMessage();
        PageInfo<MesModel> personPageInfo = new PageInfo<>(allMessage);
        return ResponseModel.sucess("", personPageInfo);
    }

    @ApiOperation(value = "个人用户-短信记录", notes = "")
    @PostMapping("/findAllMesByUserId")
    public ResponseModel<PageInfo<MesModel>> findAllMesByUserId(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam("id") String id) throws UserServiceException {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isEmpty(id)) {
            return ResponseModel.fail("", ResultCode.PARAM_MISS_MSG);
        }
        List<MesModel> allMessage = iAdminRecodingMapper.findAllMesByUserId(id);
        PageInfo<MesModel> personPageInfo = new PageInfo<>(allMessage);
        return ResponseModel.sucess("", personPageInfo);
    }

    @ApiOperation(value = "系统管理-信息发布-查询信息", notes = "")
    @PostMapping("/findMesBymark")
    public ResponseModel<PageInfo<MesInfoModel>> findMesBymark(@RequestBody MesInfoModel mesInfoModel) throws UserServiceException {
        PageHelper.startPage(mesInfoModel.getPageNum(), mesInfoModel.getPageSize());
        Map<String, Object> parms = new HashMap<>();
        if (mesInfoModel.getTitle() == null) {
            mesInfoModel.setTitle("");
        }
        parms.put("title", mesInfoModel.getTitle());
        List<MesInfoModel> allMessage = iAdminRecodingMapper.findMesBymark(parms);
        PageInfo<MesInfoModel> personPageInfo = new PageInfo<>(allMessage);
        return ResponseModel.sucess("", personPageInfo);
    }

    @ApiOperation(value = "系统管理-信息发布-增加信息", notes = "")
    @PostMapping("/addMesInfo")
    public ResponseModel addMesInfo(@RequestBody MesInfoModel mesInfoModel) throws UserServiceException {
        MesInfo m = new MesInfo();
        m.setContent(mesInfoModel.getContent());
        m.setTitle(mesInfoModel.getTitle());
        MesInfo save = iMesInfoRepository.save(m);
        return ResponseModel.sucess("", StringUtils.isEmpty(save));
    }

    @ApiOperation(value = "系统管理-信息发布-删除信息", notes = "")
    @PostMapping("/deleteMesInfo")
    public ResponseModel deleteMesInfo(String mesId) throws UserServiceException {
        int i = iAdminRecodingMapper.deleteMesInfo(mesId);
        if (i > 0) {
            return ResponseModel.sucess("", i);
        }
        return ResponseModel.fail("", ResultCode.DELETE_FAIL_MSG);
    }

    @ApiOperation(value = "系统管理-信息发布-发布信息", notes = "")
    @PostMapping("/updateMesInfo")
    public ResponseModel updateMesInfo(@RequestBody MesInfoModel model) throws UserServiceException {
        model.setSendTime(new Date());
        model.setStatus("1");
        if (iAdminRecodingMapper.updateMesInfo(model) > 0) {
            return iCustomerFeignService.sendMesg(model);
        }
        return ResponseModel.fail("", ResultCode.MESSAGE_SEND_FAILED);
    }

    @ApiOperation(value = "系统管理-信息发布-修改信息", notes = "")
    @PostMapping("/resetMes")
    public ResponseModel resetMes(@RequestBody MesInfoModel mesInfoModel) throws UserServiceException {
        int i = iAdminRecodingMapper.resetMes(mesInfoModel);
        if (i > 0) {
            return ResponseModel.sucess("", i);
        }
        return ResponseModel.fail("", ResultCode.SEND_MES_FAILED);
    }

    @ApiOperation(value = "查看用户委托", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/getUserEntrusts")
    public ResponseModel<PageInfo<TranferEntrustModel>> getUserEntrusts(@ModelAttribute UserTokenInfo userInfo, @RequestBody TranferEntrustModel model) {
        try {
            return ResponseModel.sucess("", userService.getPageEntrusts(model));
        } catch (BusinessException e) {
            return ResponseModel.fail("", e.getMessage());
        }
    }

    @ApiOperation(value = "查看用户持仓", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @GetMapping("/getUserHolding")
    public ResponseModel<PageInfo<UserStockHoldingModel>> getUserHolding(@ModelAttribute UserTokenInfo userInfo, @RequestParam("userId") String userId, @RequestParam("index") int index, @RequestParam("size") int size) {
        return ResponseModel.sucess("",userService.getPageHolding(userId, index, size));
    }
}