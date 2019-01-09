package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRiskRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessControlContractRepository;
import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.entity.BusinessContractRisk;
import cn.com.fintheircing.admin.business.entity.BusinessControlContract;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.utils.MappingModel2EntityConverter;
import cn.com.fintheircing.admin.system.exception.SystemException;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessService {
    @Resource
    private IBusinessContractMapper businessContractMapper;
    @Resource
    private IBusinessContractRepository businessContractRepository;
    @Resource
    private IBusinessControlContractRepository businessControlContractRepository;
    @Resource
    private IBusinessContractRiskRepository businessContractRiskRepository;

    public Boolean canBuy(Integer productNo,String userId) {
        Map<String,Object> params = new HashMap<String,Object>();
        Integer productName = null;
        if (ProductStatus.SPECIAL.getIndex()!=productNo){
            productName = productNo;
        }
        params.put("productNo", productNo);
        params.put("userId",userId);
        return !(businessContractMapper.countSameContract(params)>0);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveContract(ContractModel model) throws SystemException{
        BusinessContract contract = MappingModel2EntityConverter.CONVERTERFORCONTRACT(model);
        contract = businessContractRepository.save(contract);
        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setContractId(contract.getUuid());
        controlContract.setControlType(ControlCode.CONTROL_CRETE.getIndex());
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setLessMoney(model.getBorrowMoney()+model.getPromisedMoney());
        controlContract = businessControlContractRepository.save(controlContract);
        BusinessContractRisk businessContractRisk = new BusinessContractRisk();
        businessContractRisk.setContractId(contract.getUuid());
        businessContractRisk.setAbortLine(model.getProductModel().getLiquidation());
        businessContractRisk.setWarningLine(model.getProductModel().getWornLine());
        businessContractRisk = businessContractRiskRepository.save(businessContractRisk);
        contract.setRiskId(businessContractRisk.getUuid());
        if (StringUtils.isEmpty(contract.getUuid())||StringUtils.isEmpty(controlContract.getUuid())
                ||StringUtils.isEmpty(businessContractRisk.getUuid())||StringUtils.isEmpty(contract.getRiskId())){
            throw new SystemException("保存合约失败");
        }
        return true;
    }

    public List<ContractModel> getCurrentContract(String userId){
        List<ContractModel> contractModels = businessContractMapper.selectCurrentContract(userId);
        contractModels.forEach(c->{
            c.setChoseStr(ProductStatus.getName(c.getChoseWay()));
        });
        return contractModels;
    }
}
