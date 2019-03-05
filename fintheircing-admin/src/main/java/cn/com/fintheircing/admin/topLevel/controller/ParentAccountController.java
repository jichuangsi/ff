package cn.com.fintheircing.admin.topLevel.controller;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.feign.IStockPriceFeignService;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.utils.EntityToModel;
import cn.com.fintheircing.admin.topLevel.dao.mapper.IParentAccountMapper;
import cn.com.fintheircing.admin.topLevel.dao.repository.IParentAccountRepository;
import cn.com.fintheircing.admin.topLevel.entity.ParentAccount;
import cn.com.fintheircing.admin.topLevel.model.HodingStockModel;
import cn.com.fintheircing.admin.topLevel.model.ParentAccountModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private IStockPriceFeignService iStockPriceFeignService;
    @ApiOperation(value = "添加", notes = "")
    @ApiResponse(code = 200, message = "返回添加成功的信息")
    @PostMapping("/saveParentAccount")
    public ResponseModel<ParentAccountModel> saveParentAccount( @RequestBody ParentAccountModel model){
        ParentAccount p =new ParentAccount();
        p.setTradeAccount(model.getTradeAccount());
        p.setPassWord(model.getPassWord());
        p.setJyPassword(model.getJyPassword());
        p.setTxPassword(model.getTxPassword());
        p.setAccountType(model.getAccountType());
        p.setTradeName(model.getTradeName());
        p.setBeginAmount(model.getBeginAmount());
        p.setPromiseAmount(model.getPromiseAmount());
        p.setLoanAmount(model.getLoanAmount());
        p.setCreateTime(model.getCreateTime());
        p.setAmount(model.getAmount());
        p.setTradeCode(model.getTradeCode());
        p.setQsId(model.getQsId());
        p.setIp(model.getIp());
        p.setPort(model.getPort());
        p.setVersion(model.getVersion());
        p.setYybId(model.getYybId());
        p.setAccountNo(model.getAccountNo());
        p.setSzAccout(model.getSzAccout());
        p.setShAccout(model.getShAccout());
        p.setSecurities(model.getSecurities());
        if (!StringUtils.isEmpty(iParentAccountRepository.save(p))){
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
        List<ParentAccountModel> models =new ArrayList<>();
        for (ParentAccount p:all
             ) {

            models.add( EntityToModel.CoverParentAccount(p));
        }
        return ResponseModel.sucess("",models);
    }
    @ApiOperation(value = "开启账号状态", notes = "")
    @ApiResponse(code = 200, message = "返回是否修改成功")
    @PostMapping("/openParentAccount")
    public ResponseModel openParentAccount(String id ){
        if(iParentAccountRepository.findOneByUuid(id).getStatus()==0){
            return ResponseModel.fail("", ResultCode.OPEN_FAILED);
        }
        return ResponseModel.sucess("",iParentAccountMapper.openParentAccount(id));
    }
    @ApiOperation(value = "关闭账号状态", notes = "")
    @ApiResponse(code = 200, message = "返回是否修改成功")
    @PostMapping("/closeParentAccount")
    public ResponseModel closeParentAccount(String id ){
        if(iParentAccountRepository.findOneByUuid(id).getStatus()==1){
            return ResponseModel.fail("", ResultCode.CLOSE_FAILED);
        }
        return ResponseModel.sucess("",iParentAccountMapper.closeParentAccount(id));
    }

    /**
     * 差个持仓数量差额, 差额市值
     * @param id
     * @return
     */
    @ApiOperation(value = "持仓差异表", notes = "")
    @ApiResponse(code = 200, message = "返回是否修改成功")
    @PostMapping("/hodingStock")
    public ResponseModel<List<HodingStockModel>> hodingStock(String id ) {
        ResponseModel<List<QuotesTenModel>> quotesTenListTest = iStockPriceFeignService.getQuotesTenListTest();
        List<QuotesTenModel> data = quotesTenListTest.getData();
        List<HodingStockModel> list=new ArrayList<>();
        for (QuotesTenModel d :data
                ) {
            String stockCode = d.getStockCode();
            HodingStockModel hodingStockModel = iParentAccountMapper.QueryHodingAndAccount(stockCode);
            if (StringUtils.isEmpty(hodingStockModel)){
                continue;
            }
            hodingStockModel.setClosingPrice(d.getClosePrice());

            hodingStockModel.setHodingMargin(0);
            hodingStockModel.setMarginMarketValue(0);
            list.add(hodingStockModel);
        }
        return ResponseModel.sucess("", list);
    }
}
