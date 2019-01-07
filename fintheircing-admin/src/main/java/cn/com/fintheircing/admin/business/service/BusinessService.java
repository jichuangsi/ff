package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessControlContractRepository;
import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.entity.BusinessControlContract;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.utils.MappingModel2EntityConverter;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessService {
    @Resource
    private IBusinessContractMapper businessContractMapper;
    @Resource
    private IBusinessContractRepository businessContractRepository;
    @Resource
    private IBusinessControlContractRepository businessControlContractRepository;

    public Boolean canBuy(Integer productNo,String userId) {
        Map<String,Object> params = new HashMap<String,Object>();
        String productName = "";
        if (ProductStatus.SPECIAL.getIndex()!=productNo){
            productName = ProductStatus.getName(productNo);
        }
        params.put("productName", productName);
        params.put("userId",userId);
        return !(businessContractMapper.countSameContract(params)>0);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveContract(ContractModel model){
        BusinessContract contract = MappingModel2EntityConverter.CONVERTERFORCONTRACT(model);
        contract = businessContractRepository.save(contract);
        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setContractId(contract.getUuid());
        controlContract.setControlType(ControlCode.CONTROL_CRETE.getIndex());
        controlContract.setVerifyStatus(VerifyCode.VERIFY_W.getIndex());
        controlContract.setLessMoney(model.getBorrowMoney()+model.getPromisedMoney());
        controlContract = businessControlContractRepository.save(controlContract);
        if (!(StringUtils.isEmpty(contract.getUuid())||StringUtils.isEmpty(controlContract.getUuid()))){
            return true;
        }
        return false;
    }
}
