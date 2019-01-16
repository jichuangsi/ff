package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.constant.EntrustStatus;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessStockHoldingMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessTaxationMapper;
import cn.com.fintheircing.admin.business.dao.repository.*;
import cn.com.fintheircing.admin.business.entity.*;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.*;
import cn.com.fintheircing.admin.business.utils.BusinessUtils;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.model.BuyOrderRequestModel;
import cn.com.fintheircing.admin.common.model.MotherAccount;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.common.utils.MappingModel2EntityConverter;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private IBusinessStockHoldingMapper businessStockHoldingMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private IExchangeFeignService exchangeFeignService;
    @Resource
    private IBusinessTaxationRepository businessTaxationRepository;
    @Resource
    private IBusinessTaxationRelationRepository businessTaxationRelationRepository;
    @Resource
    private IBusinessTaxationMapper businessTaxationMapper;

    @Value("${custom.risk.maxBuyOne}")
    private Double maxBuyOne;
    @Value("${custom.risk.venturEditionMax}")
    private Double venturEditionMax;
    @Value("${custom.risk.holdOverFiveAvg}")
    private Double holdOverFiveAvg;
    @Value("${custom.risk.holdOverCurrency}")
    private Double holdOverCurrency;
    @Value("${custom.risk.shockShutDown}")
    private Integer shockShutDown;
    @Value("${custom.system.autoBuy}")
    private String autoBuy;
    @Value("${custom.system.shangRegex}")
    private String shangRegex;
    @Value("${custom.system.shenRegex}")
    private String shenRegex;

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
        //新建合约
        BusinessContract contract = MappingModel2EntityConverter.CONVERTERFORCONTRACT(model);
        contract.setContractStatus(BusinessStatus.CONTRACT_NEW.getNum());  //给予新建合约状态
        contract.setRudeStatus(BusinessStatus.BUSINESS_NOT.getNum());       //给予未平仓状态
        contract = businessContractRepository.save(contract);
        //绑定风控
        BusinessContractRisk businessContractRisk = new BusinessContractRisk();
        businessContractRisk.setContractId(contract.getUuid());
        businessContractRisk.setAbortLine(model.getProductModel().getLiquidation());
        businessContractRisk.setWarningLine(model.getProductModel().getWornLine());
        businessContractRisk.setCustomerMaxAccount(maxBuyOne);
        businessContractRisk.setHoldOverCurrency(holdOverCurrency);
        businessContractRisk.setHoldOverFiveAvg(holdOverFiveAvg);
        businessContractRisk.setShockShutDown(shockShutDown);
        businessContractRisk.setVenturEditionMaxAccount(venturEditionMax);
        businessContractRisk = businessContractRiskRepository.save(businessContractRisk);

        contract.setRiskId(businessContractRisk.getUuid());
        //创建新建合约操作表
        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setContractId(contract.getUuid());
        controlContract.setControlType(ControlCode.CONTROL_CRETE.getIndex());   //记录新建合约
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());    //无需审核
        //合约剩余多少
        businessControlContractRepository.save(controlContract);
        //修改合约金额
        contract.setAvailableMoney(model.getBorrowMoney()+model.getPromisedMoney());
        contract.setContractStatus(BusinessStatus.CONTRACT_BUSINESS.getNum());
        businessContractRepository.save(contract);  //给合约添加可用资金
        //创建填入合约金额操作表
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
        StockModel stockModel = new StockModel();//根据stockNum获取当前股票实时数据
        StockHoldingModel stockHoldingModel = businessStockHoldingMapper.selectStockNum(model.getContractId(),model.getStockNum());
        BusinessContractRisk risk = businessContractRiskRepository.findBusinessContractRiskByContractId(model.getContractId());
        ContractModel contract = businessContractMapper.selectContract(contractId);//获取相关合约
        BusinessUtils.throughRisk(stockHoldingModel,model,contract,stockModel);//验证是否能交易，不能直接抛出异常
        /**
         * 需要计算税费
         */
        Double taxation = 1.0;
        List<BusinessTaxation> businessTaxations = businessTaxationRepository.findByDeleteFlagAndBsuinessTo("0",BusinessTaxation.BUSINESS_BUY);
        for (BusinessTaxation businessTaxation:businessTaxations){
            taxation = taxation + businessTaxation.getTaxRate();
        }
        coldMoney = taxation*coldMoney+contract.getBusinessRate()*coldMoney;

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
                updateAvailableMoney(model.getContractId(),coldMoney,contract.getVersion())>0)){
            throw new BusinessException(ResultCode.ACCOUNT_COST_ERR);
        }
        BusinessStockEntrust stockEntrust = new BusinessStockEntrust();
        stockEntrust.setBusinessTo(BusinessStockEntrust.STOCK_BUY);
        stockEntrust.setBuyPrice(model.getPrice());
        stockEntrust.setBuyAmount(model.getAmount());
        stockEntrust.setUserId(model.getUserId());
        stockEntrust.setStockId(stockHoldingModel.getStockId());
        stockEntrust.setEntrustStatus(EntrustStatus.ENTRUST_NOT_REPORT.getIndex());
        stockEntrust = businessStockEntrustRepository.save(stockEntrust);
        if (StringUtils.isEmpty(stockEntrust.getUuid())){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_SAVE_ERR);
        }
        for (BusinessTaxation businessTaxation:businessTaxations){
            BusinessTaxationRelation relation = new BusinessTaxationRelation();
            relation.setEntrustId(stockEntrust.getUuid());
            relation.setTaxationId(businessTaxation.getUuid());
            businessTaxationRelationRepository.save(relation);
        }

        ContractModel contractModel = new ContractModel();
        contractModel.setId(model.getContractId());
        contractModel.setColdCash(coldMoney);
        if (dealStockAuto(stockEntrust,model.getStockNum(),contractModel)){
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
    }

    /*@Async*/
    //失败回滚
    public Boolean dealStockAuto(BusinessStockEntrust model,String stockNo,ContractModel contractModel){
        Boolean auto = Boolean.parseBoolean(redisTemplate.opsForValue().get(autoBuy));
        if (auto){
            String account = getMotherAccount(contractModel.getId(),stockNo);
            if (StringUtils.isEmpty(account)){
                List<MotherAccount> motherAccounts = new ArrayList<>();
                Random random = new Random(UUID.fromString(model.getUserId()).getMostSignificantBits());
                account = motherAccounts.get(random.nextInt(motherAccounts.size())).getAccountNo();
            }
            String exchangeId = "";
            if (CommonUtil.regexString(shenRegex,stockNo)){
                exchangeId = "0";
            }else if (CommonUtil.regexString(shangRegex,stockNo)){
                exchangeId = "1";
            }
            BuyOrderRequestModel buyOrderRequestModel = new BuyOrderRequestModel();
            buyOrderRequestModel.setExchangeId(exchangeId);
            buyOrderRequestModel.setMotnerAccount(account);
            buyOrderRequestModel.setPrice(Float.valueOf(model.getBuyPrice().toString()));
            buyOrderRequestModel.setPszStockCode(stockNo);
            buyOrderRequestModel.setQuantity(model.getBuyAmount());
            ResponseModel<String> responseModel = exchangeFeignService.sendBuyOrder(buyOrderRequestModel);
            if (ResultCode.SUCESS.equals(responseModel.getCode())){
                model.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
                model.setDealNo(responseModel.getData());
                model.setMontherAccount(account);
                businessStockEntrustRepository.save(model);
                ContractModel contract = businessContractMapper.selectContract(contractModel.getId());//获取相关合约
                if(businessContractRepository.updateColdMoney(contractModel.getId(),
                        contractModel.getColdCash(),contract.getVersion())>0){
                    return true;
                }
            }
        }
        return false;
    }


    @Transactional(rollbackFor = Exception.class)
    public void dealStockHand(UserTokenInfo userInfo,StockEntrustModel model) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0",model.getId());
        if (entrust==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        ContractModel contract = businessContractMapper.selectContract(model.getContractId());//获取相关合约
        if (contract==null){
            throw new BusinessException(ResultCode.CONTRACT_NULL_FIND);
        }
        entrust.setMontherAccount(model.getMotherAccount());
        entrust.setDealNo(model.getDealNo());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
        entrust.setUpdatedTime(new Date());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdateUserName(userInfo.getUserName());
        businessStockEntrustRepository.save(entrust);
        Double coldMoney = 0.0;
        Double taxation = 1.0;
        List<TaxationModel> taxationModels = businessTaxationMapper.selectEntrustTax(model.getId());
        for (TaxationModel taxationModel:taxationModels){
            taxation = taxation + taxationModel.getTaxationRate();
        }
        coldMoney = taxation*coldMoney+contract.getBusinessRate()*coldMoney;
        if(!(businessContractRepository.updateColdMoney(model.getContractId(),
                coldMoney,contract.getVersion())>0)){
           throw new BusinessException(ResultCode.UPDATE_ERR_MSG);
        }

    }


    public Map<String,String> checkEntrust(UserTokenInfo userInfo,StockEntrustModel model) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0",model.getId());
        if (entrust==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        if (!EntrustStatus.ENTRUST_NOT_REPORT.getIndex().
                equals(entrust.getEntrustStatus())){
            throw new BusinessException(ResultCode.UPDATE_ALREAD_MSG);
        }
        entrust.setUpdateUserName(userInfo.getUserName());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdatedTime(new Date());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_WAIT.getIndex());
        businessStockEntrustRepository.save(entrust);
        String account = getMotherAccount(model.getContractId(),model.getStockNum());
        if (StringUtils.isEmpty(account)){
            List<MotherAccount> motherAccounts = new ArrayList<>();
            Random random = new Random(UUID.fromString(model.getUserId()).getMostSignificantBits());
            account = motherAccounts.get(random.nextInt(motherAccounts.size())).getAccountNo();
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("motherAccount",account);
        return map;
    }

    //查询是否已购买相同的股票
    private String getMotherAccount(String stockNo,String contractId){
        String account = "";
        StockHoldingModel holding = businessStockHoldingMapper.selectStockNum(contractId,stockNo);
        if (holding!=null){
            account = holding.getMotherAccount();
        }
        return account;
    }


}
