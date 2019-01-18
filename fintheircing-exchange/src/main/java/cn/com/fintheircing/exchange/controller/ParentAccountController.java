package cn.com.fintheircing.exchange.controller;

import cn.com.fintheircing.exchange.constant.ResultCode;
import cn.com.fintheircing.exchange.dao.mapper.IParentAccountMapper;
import cn.com.fintheircing.exchange.dao.repository.IParentAccountRepository;
import cn.com.fintheircing.exchange.entity.ParentAccount;
import cn.com.fintheircing.exchange.model.ResponseModel;
import cn.com.fintheircing.exchange.model.parent.ParentAccountModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ParentAccountController
 *
 * @author yaoxiong
 * @date 2019/1/18
 */
@RestController
@Api("ParentAccountController 母账户的相关Controller")
public class ParentAccountController {
    @Resource
    private IParentAccountRepository iParentAccountRepository;
    @Resource
    private IParentAccountMapper  iParentAccountMapper;

    @ApiOperation(value = "添加", notes = "")
    @ApiResponse(code = 200, message = "返回添加成功的信息")
    @PostMapping("/saveParentAccount")
    public ResponseModel<ParentAccountModel> saveParentAccount(@Validated @RequestBody ParentAccountModel model){
        ParentAccount p =new ParentAccount();
        p.setTradeAccount(model.getTradeAccount());
        p.setAmount(model.getAmount());
        p.setBeginAmount(model.getBeginAmount());
        p.setMarketValue(model.getMarketValue());
        p.setSecurities(model.getSecurities());
        p.setTradeName(model.getTradeName());
        if (StringUtils.isEmpty(iParentAccountRepository.save(p))){
            return ResponseModel.sucess("", model);
        }else {
            return ResponseModel.fail("", ResultCode.KEEP_FAILED);
        }
    }

    @ApiOperation(value = "查看所有母账户", notes = "")
    @ApiResponse(code = 200, message = "返回所有母账户的信息")
    @PostMapping("/findAllParentAccount")
    public ResponseModel<List<ParentAccountModel>> findAllParentAccount(){
        List<ParentAccount> all = iParentAccountRepository.findAll();

        return ResponseModel.sucess("",iParentAccountMapper.findAllParentAccount());
    }
    @ApiOperation(value = "开启账号状态", notes = "")
    @ApiResponse(code = 200, message = "返回是否修改成功")
    @PostMapping("/findAllParentAccount")
    public ResponseModel openParentAccount(String id ){
        return ResponseModel.sucess("",iParentAccountMapper.openParentAccount(id));
    }
    @ApiOperation(value = "关闭账号状态", notes = "")
    @ApiResponse(code = 200, message = "返回是否修改成功")
    @PostMapping("/findAllParentAccount")
    public ResponseModel closeParentAccount(String id ){
        return ResponseModel.sucess("",iParentAccountMapper.closeParentAccount(id));
    }

}
