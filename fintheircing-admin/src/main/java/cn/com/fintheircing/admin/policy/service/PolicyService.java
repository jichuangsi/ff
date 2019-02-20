package cn.com.fintheircing.admin.policy.service;

import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractControlMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.utils.BusinessUtils;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.model.EnumTypeModel;
import cn.com.fintheircing.admin.policy.model.TranferContractModel;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PolicyService {

    @Resource
    private IBusinessContractMapper businessContractMapper;
    @Resource
    private IBusinessContractControlMapper businessContractControlMapper;
    @Resource
    private IDistributService distributService;

    //查看合约
    public PageInfo<TranferContractModel> getPageContractInfo(TranferContractModel model){
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<TranferContractModel> contracts = businessContractMapper.selectPageContracts(model);
        for (TranferContractModel contract:contracts){
            contract.setStatus(BusinessStatus.getName(contract.getContractStatus()));
            contract.setRudeStr(BusinessStatus.getName(contract.getRudeEnd()));
            contract.setProductName(distributService.getProductName(contract.getProductId()));
            contract.setContractSum(BusinessUtils.addMethod(contract.getCanUseMoney(),contract.getColdCash(),contract.getWorth()));
            contract.setAbortLine(BusinessUtils.addMethod(contract.getPromisedMoney(),contract.getBorrowMoney())*contract.getDownRate());
            contract.setWarningLine(BusinessUtils.addMethod(contract.getPromisedMoney(),contract.getBorrowMoney())*contract.getWarnRate());
            contract.setLeftWorth(BusinessUtils.addMethod(contract.getCanUseMoney(),contract.getWorth()));
            if (0 != contract.getContractSum()) {
                contract.setLeftWorth(contract.getContractSum() - contract.getBorrowMoney());
            }
        }
        PageInfo<TranferContractModel> pageInfo = new PageInfo<TranferContractModel>(contracts);
        return pageInfo;
    }

    //分页合约操作
    public PageInfo<ContractControlModel> getPageContractControl(ContractControlModel model){
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<ContractControlModel> contracts = businessContractControlMapper.getContractControls(model);
        for (ContractControlModel control:contracts){
            control.setControlStr(ControlCode.getName(control.getControlNum()));
            control.setProductStr(distributService.getProductName(control.getProductId()));
            control.setVerifyStr(VerifyCode.getName(control.getVerifyStatus()));
        }
        PageInfo<ContractControlModel> pageInfo = new PageInfo<ContractControlModel>(contracts);
        return pageInfo;
    }

    //获取合约操作
    public List<EnumTypeModel> getAllControlType(){
        List<EnumTypeModel> typeModels = new ArrayList<EnumTypeModel>();
        ControlCode[] values = ControlCode.values();
        for (ControlCode code:values){
            EnumTypeModel typeModel = new EnumTypeModel();
            typeModel.setType(code.getIndex());
            typeModel.setTypeStr(code.getName());
            typeModels.add(typeModel);
        }
        return typeModels;
    }

    //获取合约状态和产品
    public Map<String,Object> getContractStatusAndProduct(){
        Map<String,Object> map = new HashMap<String, Object>();
        List<EnumTypeModel> businessStatus = new ArrayList<EnumTypeModel>();
        List<EnumTypeModel> products = new ArrayList<EnumTypeModel>();
        BusinessStatus[] statuses = BusinessStatus.values();
        ProductStatus[] productStatuses = ProductStatus.values();
        for (BusinessStatus status:statuses){
            EnumTypeModel enumTypeModel = new EnumTypeModel();
            enumTypeModel.setType(status.getNum());
            enumTypeModel.setTypeStr(status.getName());
            businessStatus.add(enumTypeModel);
        }
        for (ProductStatus status:productStatuses){
            EnumTypeModel enumTypeModel = new EnumTypeModel();
            enumTypeModel.setType(status.getIndex());
            enumTypeModel.setTypeStr(status.getName());
            products.add(enumTypeModel);
        }
        map.put("businessStatus", JSONObject.toJSONString(businessStatus));
        map.put("productStatus",JSONObject.toJSONString(products));
        return map;
    }
}
