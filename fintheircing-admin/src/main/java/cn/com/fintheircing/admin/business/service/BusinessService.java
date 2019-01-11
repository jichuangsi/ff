package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRiskRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessControlContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockEntrustRepository;
import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.entity.BusinessContractRisk;
import cn.com.fintheircing.admin.business.entity.BusinessControlContract;
import cn.com.fintheircing.admin.business.entity.BusinessStockEntrust;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.utils.MappingModel2EntityConverter;
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
    @Resource
    private IBusinessStockEntrustRepository businessStockEntrustRepository;

    public Boolean canBuy(Integer productNo,String userId) {
        Map<String,Object> params = new HashMap<String,Object>();
        Integer productName = null;
        if (ProductStatus.SPECIAL.getIndex()!=productNo){
            productName = productNo;
        }
        params.put("productNo", productName);
        params.put("userId",userId);
        return !(businessContractMapper.countSameContract(params)>0);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveContract(ContractModel model) throws BusinessException{
        BusinessContract contract = MappingModel2EntityConverter.CONVERTERFORCONTRACT(model);
        contract.setContractStatus(BusinessStatus.CONTRACT_NEW.getNum());  //给予新建合约状态
        contract.setRudeStatus(BusinessStatus.BUSINESS_NOT.getNum());       //给予未平仓状态
        contract = businessContractRepository.save(contract);

        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setContractId(contract.getUuid());
        controlContract.setControlType(ControlCode.CONTROL_CRETE.getIndex());   //记录新建合约
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());    //无需审核
         //合约剩余多少
        controlContract = businessControlContractRepository.save(controlContract);
        BusinessContractRisk businessContractRisk = new BusinessContractRisk();
        businessContractRisk.setContractId(contract.getUuid());
        businessContractRisk.setAbortLine(model.getProductModel().getLiquidation());
        businessContractRisk.setWarningLine(model.getProductModel().getWornLine());
        businessContractRisk = businessContractRiskRepository.save(businessContractRisk);
        contract.setRiskId(businessContractRisk.getUuid());

        contract.setAvailableMoney(model.getBorrowMoney()+model.getPromisedMoney());
        contract.setContractStatus(BusinessStatus.CONTRACT_BUSINESS.getNum());
        businessContractRepository.save(contract);  //给合约添加可用资金
        BusinessControlContract control = new BusinessControlContract();
        control.setContractId(contract.getUuid());
        control.setControlType(ControlCode.CONTROL_GETLEVERMONEY.getIndex());   //记录发放杠杆资金
        control.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        control.setAddMoney(contract.getAvailableMoney()); //往合约加了多少钱
        control.setLessMoney(contract.getAvailableMoney()); //合约剩余金额
        businessControlContractRepository.save(control);
        return true;
    }

    public List<ContractModel> getCurrentContract(String userId){
        List<ContractModel> contractModels = businessContractMapper.selectCurrentContract(userId);
        contractModels.forEach(c->{
            c.setChoseStr(ProductStatus.getName(c.getChoseWay()));
        });
        return contractModels;
    }


    //交易过程中冻结资金
    @Transactional(rollbackFor = Exception.class)
    public void costColdContract(StockEntrustModel model) throws BusinessException{
        Double coldMoney = model.getAmount()*model.getPrice();
        String contractId = model.getContractId();
        ContractModel contract = businessContractMapper.selectContract(contractId);//获取相关合约
        if (contract==null){
            throw new BusinessException(ResultCode.SELECT_NULL_MSG);
        }
        Double totalMoney = contract.getCanUseMoney()+contract.getColdCash()+contract.getWorth();//总资产
        Double warningMoney =(contract.getBorrowMoney()+
                contract.getPromisedMoney())*contract.getWarningLine();//警戒线
        if (totalMoney<=warningMoney){
            throw new BusinessException(ResultCode.ACCOUNT_WARN_ERR);
        }
        if (contract.getCanUseMoney()<coldMoney){
            throw new BusinessException(ResultCode.ACCOUNT_LESS_ERR);
        }
        if (!(businessContractRepository.
                updateColdMoney(model.getContractId(),coldMoney,contract.getVersion())>0)){
            throw new BusinessException(ResultCode.ACCOUNT_COST_ERR);
        }
        BusinessStockEntrust stockEntrust = new BusinessStockEntrust();
        stockEntrust.setBusinessTo(BusinessStockEntrust.STOCK_BUY);
        stockEntrust.setBuyAccount(model.getAmount()*model.getPrice());
        stockEntrust.setBuyAmount(model.getAmount());
        stockEntrust.setUserId(model.getUserId());
        stockEntrust = businessStockEntrustRepository.save(stockEntrust);
        if (StringUtils.isEmpty(stockEntrust.getUuid())){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_SAVE_ERR);
        }
    }


}
