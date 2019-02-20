package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.constant.EntrustStatus;
import cn.com.fintheircing.admin.business.constant.RudeStatus;
import cn.com.fintheircing.admin.business.dao.mapper.*;
import cn.com.fintheircing.admin.business.dao.repository.*;
import cn.com.fintheircing.admin.business.entity.*;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.risk.dao.repository.IBusinessContractRiskRepository;
import cn.com.fintheircing.admin.risk.entity.BusinessContractRisk;
import cn.com.fintheircing.admin.risk.model.DangerousStockModel;
import cn.com.fintheircing.admin.risk.model.RiskContractModel;
import cn.com.fintheircing.admin.risk.model.RiskControlModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import cn.com.fintheircing.admin.taxation.model.TaxationModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferEntrustModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferHoldingModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferStockEntrustModel;
import cn.com.fintheircing.admin.business.synchronize.SynchronizeComponent;
import cn.com.fintheircing.admin.business.utils.BusinessUtils;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.IStockPriceFeignService;
import cn.com.fintheircing.admin.common.feign.model.*;
import cn.com.fintheircing.admin.common.model.EnumTypeModel;
import cn.com.fintheircing.admin.common.model.MotherAccount;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.common.utils.MappingModel2EntityConverter;
import cn.com.fintheircing.admin.scheduling.model.ContractHoldingJsonModel;
import cn.com.fintheircing.admin.scheduling.model.ContractHoldingModel;
import cn.com.fintheircing.admin.scheduling.model.ContractJsonModel;
import cn.com.fintheircing.admin.system.service.SystemService;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import cn.com.fintheircing.admin.taxation.dao.mapper.IBusinessTaxationMapper;
import cn.com.fintheircing.admin.taxation.dao.repository.IBusinessTaxationRelationRepository;
import cn.com.fintheircing.admin.taxation.dao.repository.IBusinessTaxationRepository;
import cn.com.fintheircing.admin.taxation.entity.BusinessTaxation;
import cn.com.fintheircing.admin.taxation.entity.BusinessTaxationRelation;
import cn.com.fintheircing.admin.useritem.common.TransactionSummaryStatus;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BusinessService {

    private Logger logger = LoggerFactory.getLogger(getClass());

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
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private IExchangeFeignService exchangeFeignService;
    @Resource
    private IBusinessTaxationRepository businessTaxationRepository;
    @Resource
    private IBusinessTaxationRelationRepository businessTaxationRelationRepository;
    @Resource
    private IBusinessStockHoldingRepository businessStockHoldingRepository;
    @Resource
    private IBusinessStockEntrustMapper businessStockEntrustMapper;
    @Resource
    private TransactionSummaryRepository transactionSummaryRepository;
    @Resource
    private MotherAccountQueryService motherAccountQueryService;
    @Resource
    private IStockPriceFeignService stockPriceFeignService;
    @Resource
    private SystemService systemService;
    @Resource
    private ItemService itemService;
    @Resource
    private ICustomerFeignService customerFeignService;
    @Resource
    private SynchronizeComponent synchronizeComponent;
    @Resource
    private IBusinessTaxationMapper businessTaxationMapper;
    @Resource
    private IDistributService distributService;

    @Value("${custom.risk.maxBuyOne}")
    private double maxBuyOne;
    @Value("${custom.risk.venturEditionMax}")
    private double venturEditionMax;
    @Value("${custom.risk.holdOverFiveAvg}")
    private double holdOverFiveAvg;
    @Value("${custom.risk.holdOverCurrency}")
    private double holdOverCurrency;
    @Value("${custom.risk.stockShutDown}")
    private Integer stockShutDown;
    @Value("${custom.system.autoBuy}")
    private String autoBuy;
    @Value("${custom.system.shangRegex}")
    private String shangRegex;
    @Value("${custom.system.shenRegex}")
    private String shenRegex;
    @Value("${custom.system.chuangRegex}")
    private String chuangRegex;
    @Value("${custom.highFrequent.controlContract}")
    private double highFrequency;
    @Value("${custom.highFrequent.downStockRate}")
    private double downStockRate;
    @Value("${custom.highFrequent.contractKey}")
    private String controlContractKey;
    @Value("${custom.highFrequent.rudeEndContractKey}")
    private String rudeEndContractKey;
    @Value("${custom.highFrequent.stockContractKey}")
    private String stockContractKey;
    @Value("${custom.highFrequent.controlPrice}")
    private double controlPrice;
    @Value("${custom.admin.uuid}")
    private String adminUuid;
    @Value("${custom.pattern.entrustPattern}")
    private String entrustPattern;

    //是否可买
    public Boolean canBuy(Integer productNo, String userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        Integer productName = null;
        if (ProductStatus.SPECIAL.getIndex() != productNo) {
            productName = productNo;
        }
        params.put("productNo", productName);
        params.put("userId", userId);
        return !(businessContractMapper.countSameContract(params) > 0);
    }

    //测试，可删
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void testRollBack() throws BusinessException {
        BusinessContract contract = new BusinessContract();
        contract.setColdMoney(10.0);
        businessContractRepository.save(contract);
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new BusinessException("");
        }
    }

    //创建合约
    @Transactional(rollbackFor = Exception.class)
    public String saveContract(ContractModel model) {
        //新建合约
        BusinessContract contract = MappingModel2EntityConverter.CONVERTERFORCONTRACT(model);
        contract.setContractStatus(BusinessStatus.CONTRACT_NEW.getNum());  //给予新建合约状态
        contract.setRudeStatus(RudeStatus.BUSINESS_NOT.getNum());       //给予未平仓状态
        if (ProductStatus.MONTHS.getIndex() == contract.getChoseWay()) {
            contract.setExpiredTime(CommonUtil.getExpiredTime((int) model.getProductModel().getFinancingTime()));
        }
        contract.setCreatorId(adminUuid);
        contract.setCreatedTime(new Date());
        contract.setUpdateUserId(adminUuid);
        contract.setWarnningStatus(RudeStatus.CONTRACT_NOT_WARN.getNum());
        contract.setOnceServerMoney(model.getProductModel().getOneServerMoney());
        contract = businessContractRepository.save(contract);
        //绑定风控
        BusinessContractRisk businessContractRisk = new BusinessContractRisk();
        businessContractRisk.setContractId(contract.getUuid());
        businessContractRisk.setAbortLine(model.getProductModel().getLiquidation());
        businessContractRisk.setWarningLine(model.getProductModel().getWornLine());
        businessContractRisk.setCustomerMaxAccount(maxBuyOne);
        businessContractRisk.setHoldOverCurrency(holdOverCurrency);
        businessContractRisk.setHoldOverFiveAvg(holdOverFiveAvg);
        businessContractRisk.setStockShutDown(stockShutDown);
        businessContractRisk.setVenturEditionMaxAccount(venturEditionMax);
        businessContractRisk.setContractId(adminUuid);
        businessContractRisk.setUpdateUserId(adminUuid);
        businessContractRisk.setCreatedTime(new Date());
        businessContractRisk = businessContractRiskRepository.save(businessContractRisk);
        contract.setRiskId(businessContractRisk.getUuid());
        //创建新建合约操作表
        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setContractId(contract.getUuid());
        controlContract.setControlType(ControlCode.CONTROL_CRETE.getIndex());   //记录新建合约
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());    //无需审核
        //合约剩余多少
        controlContract.setUpdatedTime(new Date());
        controlContract.setCreatedTime(new Date());
        controlContract.setUpdateUserId(contract.getUserId());
        controlContract.setCreatorId(contract.getUserId());
        businessControlContractRepository.save(controlContract);

        //修改合约金额
        if (ProductStatus.SPECIAL.getIndex() != contract.getChoseWay()) {
            contract.setAvailableMoney((model.getBorrowMoney() + model.getPromisedMoney()) * (1 - model.getProductModel().getOneServerMoney()));
        } else {
            contract.setAvailableMoney(model.getBorrowMoney() + model.getPromisedMoney());
        }
        contract.setContractStatus(BusinessStatus.CONTRACT_BUSINESS.getNum());
        businessContractRepository.save(contract);  //给合约添加可用资金
        //创建填入合约金额操作表
        BusinessControlContract control = new BusinessControlContract();
        control.setContractId(contract.getUuid());
        control.setControlType(ControlCode.CONTROL_GETLEVERMONEY.getIndex());   //记录发放杠杆资金
        control.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        control.setAddMoney(contract.getAvailableMoney() + model.getProductModel().getOneServerMoney()); //往合约加了多少钱
        control.setLessMoney(contract.getAvailableMoney() + model.getProductModel().getOneServerMoney()); //合约剩余金额
        control.setCreatedTime(new Date());
        control.setBorrowMoney(model.getBorrowMoney());
        control.setPromisedMoney(model.getPromisedMoney());
        control.setUpdateUserId(contract.getUserId());
        control.setWarnningLine(businessContractRisk.getWarningLine());
        control.setAbortLine(businessContractRisk.getAbortLine());
        control.setBorrowRate(contract.getOnceServerMoney());
        control.setBorrowTime(new Date());
        control.setFirstInterest(contract.getFirstInterest());
        control.setCreatorId(contract.getUserId());
        businessControlContractRepository.save(control);

        if (ProductStatus.SPECIAL.getIndex() != contract.getChoseWay()) {
            BusinessControlContract controlTwo = new BusinessControlContract();
            controlTwo.setContractId(contract.getUuid());
            controlTwo.setControlType(ControlCode.CONTROL_GETLEVERMONEY.getIndex());   //记录发放杠杆资金
            controlTwo.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlTwo.setCostMoney(model.getProductModel().getOneServerMoney());
            controlTwo.setLessMoney(contract.getAvailableMoney()); //合约剩余金额
            controlTwo.setCreatedTime(new Date());
            controlTwo.setUpdateUserId(contract.getUserId());
            controlTwo.setCreatorId(contract.getUserId());
            businessControlContractRepository.save(controlTwo);
        }
        businessContractRepository.save(contract);
        return contract.getUuid();
    }

    //获取当前所有合约
    public List<ContractModel> getCurrentContract(String userId) {
        List<ContractModel> contractModels = businessContractMapper.selectCurrentContract(userId);
        contractModels.forEach(c -> {
            c.setChoseStr(ProductStatus.getName(c.getChoseWay()));
        });
        return contractModels;
    }

    //交易过程中冻结资金
    //买入流程
    @Transactional(rollbackFor = Exception.class)
    public void costColdContract(StockEntrustModel model) throws BusinessException {
        Double payMoney = BusinessUtils.multiplicationMethod(model.getAmount().doubleValue(), model.getPrice());
        String contractId = model.getContractId();
        List<QuotesTenModel> quotesTenModels = getQuotesTenModel(model.getStockNum());//根据stockNum获取当前股票实时数据
        if (!(quotesTenModels.size() > 0)) {
            throw new BusinessException(ResultCode.STOCK_NULL_ERR);
        }
        QuotesTenModel quotesTenModel = quotesTenModels.get(0);
        List<StockHoldingModel> stockHoldingModels = businessStockHoldingMapper.selectStockNum(model.getContractId(), model.getStockNum(), "");
        StockHoldingModel stockHoldingModel = new StockHoldingModel();
        if (stockHoldingModels.size() > 0) {
            stockHoldingModel = stockHoldingModels.get(0);
            if (RudeStatus.BUSINESS_CONTROL.getNum() == stockHoldingModel.getRudeEnd()) {
                throw new BusinessException(ResultCode.STOCK_ENTRUST_DANGER);
            }
        }
        ContractModel contract = businessContractMapper.selectContract(contractId, model.getUserId());//获取相关合约
        if (RudeStatus.BUSINESS_RUDE.getNum() == contract.getRudeEnd()) {
            throw new BusinessException(ResultCode.RUDE_CONTRACT_ERR);
        }
        TransactionSummary transactionSummary = transactionSummaryRepository.findByDeleteFlagAndStockNum("0", model.getStockNum());
        if (transactionSummary == null) {
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
        BusinessUtils.throughRisk(stockHoldingModel, model, contract, quotesTenModel, chuangRegex);//验证是否能交易，不能直接抛出异常
        /**
         * 需要计算税费
         */
        Double taxation = 0.0;
        double fixedTaxation = 0.0;
        List<BusinessTaxation> businessTaxations = businessTaxationRepository.findByDeleteFlagAndBsuinessTo("0", BusinessTaxation.BUSINESS_BUY);
        for (BusinessTaxation businessTaxation : businessTaxations) {
            if (BusinessTaxation.PERCENT_MONEY.equals(businessTaxation.getFixed())) {
                taxation = taxation + businessTaxation.getTaxRate();
            }
            if (BusinessTaxation.FIXED_MONEY.equals(businessTaxation.getFixed())) {
                fixedTaxation += businessTaxation.getTaxRate();
            }
        }
        taxation = taxation * payMoney + fixedTaxation;
        Double businessCash = BusinessUtils.addMethod(contract.getBusinessRate(), contract.getBuyRate()) * payMoney;
        Double worth = contract.getWorth();
        Double coldCash = contract.getColdCash();
        Double canUseMoney = contract.getCanUseMoney();
        Double totalMoney = BusinessUtils.addMethod(canUseMoney, coldCash, worth);//总资产
        Double warningMoney = BusinessUtils.addMethod(contract.getBorrowMoney(), contract.getPromisedMoney()) * contract.getWarningLine();//警戒线
        Double coldMoney = taxation + payMoney + businessCash;
        if (totalMoney <= warningMoney) {
            throw new BusinessException(ResultCode.ACCOUNT_WARN_ERR);
        }
        if (contract.getCanUseMoney() < coldMoney) {
            throw new BusinessException(ResultCode.ACCOUNT_LESS_ERR);
        }
        BusinessContract businessContract = businessContractRepository.findByUuid(contractId);
        businessContract.setAvailableMoney(BusinessUtils.minusMethod(businessContract.getAvailableMoney(), coldMoney));
        businessContract.setColdMoney(BusinessUtils.addMethod(businessContract.getColdMoney(), coldMoney));
        businessContractRepository.save(businessContract);
        /*if (!(businessContractRepository.
                updateAvailableMoney(model.getContractId(), coldMoney, contract.getVersion()) > 0)) {
            throw new BusinessException(ResultCode.ACCOUNT_COST_ERR);
        }*/
        BusinessStockEntrust stockEntrust = new BusinessStockEntrust();
        stockEntrust.setBusinessTo(BusinessStockEntrust.STOCK_BUY);
        stockEntrust.setBusinessPrice(model.getPrice());
        stockEntrust.setBusinessAmount(model.getAmount());
        stockEntrust.setUserId(model.getUserId());
        stockEntrust.setBusinessMoney(businessCash);
        stockEntrust.setTaxationMoney(taxation);
        stockEntrust.setDealFrom(BusinessStockEntrust.DEAL_CUSTOMER);
        stockEntrust.setStockId(transactionSummary.getId());
        stockEntrust.setCreatedTime(new Date());
        stockEntrust.setContractId(contractId);
        stockEntrust.setCreatorId(model.getUserId());
        stockEntrust.setUpdateUserId(model.getUserId());
        stockEntrust.setCancelOrder(BusinessStockEntrust.STOCK_ORDER);
        stockEntrust.setEntrustStatus(EntrustStatus.ENTRUST_NOT_DEAL.getIndex());
        stockEntrust.setColdMoney(payMoney);
        stockEntrust = businessStockEntrustRepository.save(stockEntrust);

        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setCreatorId(stockEntrust.getUserId());
        controlContract.setCreatedTime(new Date());
        controlContract.setUpdateUserId(stockEntrust.getUserId());
        controlContract.setLessMoney(BusinessUtils.addMethod(contract.getCanUseMoney(), contract.getColdCash()));
        controlContract.setCostMoney(stockEntrust.getColdMoney());
        controlContract.setBusinessMoney(stockEntrust.getBusinessMoney());
        controlContract.setTaxationMoney(stockEntrust.getTaxationMoney());
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setContractId(contract.getId());
        controlContract.setControlType(ControlCode.CONTROL_ENTRUSTBUYSTOCK.getIndex());
        businessControlContractRepository.save(controlContract);

        if (StringUtils.isEmpty(stockEntrust.getUuid())) {
            throw new BusinessException(ResultCode.STOCK_ENTRUST_SAVE_ERR);
        }
        for (BusinessTaxation businessTaxation : businessTaxations) {
            BusinessTaxationRelation relation = new BusinessTaxationRelation();
            relation.setEntrustId(stockEntrust.getUuid());
            relation.setTaxationId(businessTaxation.getUuid());
            businessTaxationRelationRepository.save(relation);
        }
        ContractModel contractModel = new ContractModel();
        contractModel.setId(model.getContractId());
        contractModel.setColdCash(coldMoney);
        if (!dealStockAuto(stockEntrust, model.getStockNum(), contractModel)) {
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
    }

    /*@Async*/
    //失败回滚
    //自动买入
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Boolean dealStockAuto(BusinessStockEntrust entrust, String stockNo, ContractModel contractModel) throws BusinessException {
        Boolean auto = Boolean.parseBoolean(redisTemplate.opsForValue().get(autoBuy));
        if (auto) {
            String account = getMotherAccount(contractModel.getId(), stockNo);
            if (StringUtils.isEmpty(account)) {
                List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
                if (motherAccounts.size() > 0) {
                    Random random = new Random(BusinessUtils.fromStringWhitoutHyphens(entrust.getUserId()).getLeastSignificantBits());
                    account = motherAccounts.get(random.nextInt(motherAccounts.size())).getAccountNo();
                } else {
                    throw new BusinessException(ResultCode.MOTHER_NULL_ERR);
                }
            }
            String exchangeId = "";
            if (CommonUtil.regexString(shangRegex, stockNo)) {
                exchangeId = "1";
            } else if (CommonUtil.regexString(shenRegex, stockNo)) {
                exchangeId = "0";
            }
            BuyOrderRequestModel buyOrderRequestModel = new BuyOrderRequestModel();
            buyOrderRequestModel.setExchangeId(exchangeId);
            buyOrderRequestModel.setMotnerAccount(account);
            buyOrderRequestModel.setPrice((float) entrust.getBusinessPrice());
            buyOrderRequestModel.setPszStockCode(stockNo);
            buyOrderRequestModel.setQuantity(entrust.getBusinessAmount());
            ResponseModel<String> responseModel = exchangeFeignService.sendBuyOrder(buyOrderRequestModel);
            if (responseModel == null) {
                throw new BusinessException(ResultCode.STOCK_MSG_ERR);
            }
            //responseModel.setCode(ResultCode.SUCESS);//test
            if (ResultCode.SUCESS.equals(responseModel.getCode())) {
                logger.debug("code:" + responseModel.getCode() + "msg:" + responseModel.getMsg());
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
                entrust.setDealNo(responseModel.getData());
                entrust.setMontherAccount(account);
                businessStockEntrustRepository.save(entrust);
                logger.debug("委托单号：" + entrust.getDealNo());
                //真实买入时才扣除冻结
            } else {
                logger.debug("code:" + responseModel.getCode() + "msg:" + responseModel.getMsg());
                return false;
            }
        }
        return true;
    }

    //手动买入
    @Transactional(rollbackFor = Exception.class)
    public void dealStockHand(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0", model.getId());
        if (entrust == null) {
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        /*ContractModel contract = businessContractMapper.selectContract(model.getContractId());//获取相关合约
        if (contract==null){
            throw new BusinessException(ResultCode.CONTRACT_NULL_FIND);
        }*/
        if (!userInfo.getUuid().equals(entrust.getDealMan())) {
            throw new BusinessException(ResultCode.DEAL_IS_EXIST);
        }
        entrust.setMontherAccount(model.getMotherAccount());
        entrust.setDealNo(model.getDealNo());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
        entrust.setUpdatedTime(new Date());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdateUserName(userInfo.getUserName());
        businessStockEntrustRepository.save(entrust);//暂不涉及冻结资金的扣除
    }

    //修改申报单状态为正在处理
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> checkEntrust(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0", model.getId());
        if (entrust == null) {
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        if (!userInfo.getUuid().equals(entrust.getDealMan())) {
            if (!EntrustStatus.ENTRUST_NOT_DEAL.getIndex().
                    equals(entrust.getEntrustStatus())) {
                throw new BusinessException(ResultCode.UPDATE_ALREAD_MSG);
            }
        }
        entrust.setUpdateUserName(userInfo.getUserName());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdatedTime(new Date());
        entrust.setDealMan(userInfo.getUuid());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_WAIT_DEAL.getIndex());
        businessStockEntrustRepository.save(entrust);
        String account = getMotherAccount(entrust.getContractId(), entrust.getStockId());
        if (StringUtils.isEmpty(account)) {
            List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
            if (motherAccounts.size() > 0) {
                Random random = new Random(BusinessUtils.fromStringWhitoutHyphens(userInfo.getUuid()).getLeastSignificantBits());
                account = motherAccounts.get(random.nextInt(motherAccounts.size())).getAccountNo();
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("motherAccount", account);
        return map;
    }

    //查询是否已购买相同的股票
    private String getMotherAccount(String contractId, String stockId) {
        String account = "";
        List<StockHoldingModel> holdings = businessStockHoldingMapper.selectStockNum(contractId, "", stockId);
        if (holdings.size() > 0) {
            StockHoldingModel holding = holdings.get(0);
            if (holding != null) {
                account = holding.getMotherAccount();
            }
        }
        return account;
    }

    //当前持有股
    public List<StockHoldingModel> getCurrentHolding(StockHoldingModel model) {
        if (businessContractRepository.countByDeleteFlagAndUuidAndUserIdAndContractStatus
                ("0", model.getContractId(), model.getUserId(), BusinessStatus.CONTRACT_BUSINESS.getNum()) > 0) {
            return businessStockHoldingMapper.selectStockNum(model.getContractId(), model.getStockNo(), "");
        }
        return null;
    }

    //卖出申报流程
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void sellHoldStock(StockHoldingModel model) throws BusinessException {
        TransactionSummary summary = transactionSummaryRepository.findByDeleteFlagAndStockNum("0", model.getStockNo());
        if (null == summary) {
            throw new BusinessException(ResultCode.STOCK_ENTRUST_DANGER);
        }
        BusinessStockHolding holding = businessStockHoldingRepository.findBusinessStockHoldingByDeleteFlagAndStockIdAndContractId("0", summary.getId(), model.getContractId());
        if (null == holding) {
            throw new BusinessException(ResultCode.STOCK_HOLDING_ERR);
        }
        if (RudeStatus.BUSINESS_CONTROL.getNum() == holding.getRudeEnd()) {
            throw new BusinessException(ResultCode.SELL_CONTROL_STOCK);
        }
        BusinessContract contract = null;
        if (adminUuid.equals(model.getUserId())) {
            contract = businessContractRepository.findByDeleteFlagAndUuid("0", model.getContractId());
        } else {
            contract = businessContractRepository.findByDeleteFlagAndUuidAndUserId("0", model.getContractId(), model.getUserId());
        }
        if (null == contract) {
            throw new BusinessException(ResultCode.CONTACT_NOT_EXITS);
        }
        if (RudeStatus.BUSINESS_RUDE.getNum() == contract.getRudeStatus()) {
            throw new BusinessException(ResultCode.RUDE_CONTRACT_ERR);
        }
        Integer canSell = holding.getCanSell();
        Integer cold = holding.getColdAmount();
        if (model.getAmount() > canSell) {
            logger.error("userID:" + model.getUserId() + ";msg:" + ResultCode.STOCK_SELL_LESS_ERR);
            throw new BusinessException(ResultCode.STOCK_SELL_LESS_ERR);
        }
        holding.setCanSell(canSell - model.getAmount());
        holding.setColdAmount(cold + model.getAmount());
        try {
            businessStockHoldingRepository.save(holding);
        } catch (Exception e) {
            logger.error("userId:" + model.getUserId() + ",msg:持仓修改失败");
            throw new BusinessException(e.getMessage());
        }
        BusinessStockEntrust entrust = new BusinessStockEntrust();
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_NOT_DEAL.getIndex());
        entrust.setDealFrom(model.getDealFrom());
        entrust.setMontherAccount(holding.getMotherAccount());
        entrust.setStockId(holding.getStockId());
        entrust.setBusinessAmount(model.getAmount());
        entrust.setBusinessPrice(model.getCostPrice());
        entrust.setUserId(model.getUserId());
        entrust.setDealFrom(BusinessStockEntrust.DEAL_CUSTOMER);
        entrust.setContractId(model.getContractId());
        entrust.setBusinessTo(BusinessStockEntrust.STOCK_SELL);
        entrust.setHoldingId(holding.getUuid());
        entrust.setCancelOrder(BusinessStockEntrust.STOCK_ORDER);
        entrust.setCreatedTime(new Date());
        entrust.setCreatorId(model.getUserId());
        entrust.setUpdateUserId(model.getUserId());

        try {
            businessStockEntrustRepository.save(entrust);
        } catch (Exception e) {
            logger.error("userId:" + model.getUserId() + ",msg:保存出售股票委托失败");
            throw new BusinessException(e.getMessage());
        }

        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setCreatorId(entrust.getUserId());
        controlContract.setCreatedTime(new Date());
        controlContract.setLessMoney(BusinessUtils.addMethod(contract.getAvailableMoney(), contract.getColdMoney()));
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setContractId(model.getContractId());
        controlContract.setControlType(ControlCode.CONTROL_ENTRUSTSELLSTOCK.getIndex());
        controlContract.setUpdateUserId(entrust.getUserId());
        businessControlContractRepository.save(controlContract);
        if (!dealAutoSell(entrust, model.getStockNo())) {
            logger.error("userId:" + model.getUserId() + ",msg:卖出股票委托失败");
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
    }

    //自动卖出
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Boolean dealAutoSell(BusinessStockEntrust entrust, String stockNo) {
        Boolean auto = Boolean.parseBoolean(redisTemplate.opsForValue().get(autoBuy));
        if (auto) {
            String account = entrust.getMontherAccount();
            String exchangeId = "";
            if (CommonUtil.regexString(shangRegex, stockNo)) {
                exchangeId = "1";
            } else if (CommonUtil.regexString(shenRegex, stockNo)) {
                exchangeId = "0";
            }
            SellOrderRequestModel sell = new SellOrderRequestModel();
            sell.setExchangeId(exchangeId);
            sell.setMotnerAccount(account);
            sell.setPrice((float) entrust.getBusinessPrice());
            sell.setPszStockCode(stockNo);
            sell.setQuantity(entrust.getBusinessAmount());
            ResponseModel<String> response = exchangeFeignService.sendSellOrder(sell);
            if (response == null) {
                return false;
            }
            //response.setCode(ResultCode.SUCESS);//测试
            if (ResultCode.SUCESS.equals(response.getCode())) {
                logger.debug("code:" + response.getCode() + "msg:" + response.getMsg());
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
                entrust.setDealNo(response.getData());
                logger.debug("委托单号：" + entrust.getDealNo());
            } else {
                logger.debug("code:" + response.getCode() + "msg:" + response.getMsg());
                return false;
            }
        }
        return true;
    }

    //手动卖出申报
    public void dealSellHand(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0", model.getId());
        if (entrust == null) {
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        entrust.setDealNo(model.getDealNo());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
        entrust.setUpdatedTime(new Date());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdateUserName(userInfo.getUserName());
        businessStockEntrustRepository.save(entrust);
    }

    //分页获取申报
    public TranferStockEntrustModel getPageEntrust(StockEntrustModel model) {
        PageHelper.startPage(model.getPageIndex(), model.getPageSize());
        List<StockEntrustModel> models = businessStockEntrustMapper.selectPageEntrusts(model);
        models.forEach(stockEntrust -> {
            if (stockEntrust.getBusiness() == BusinessStockEntrust.STOCK_BUY) {
                stockEntrust.setBusinessStr("买入");
            } else if (stockEntrust.getBusiness() == BusinessStockEntrust.STOCK_SELL) {
                stockEntrust.setBusinessStr("卖出");
            }
            stockEntrust.setStatusStr(EntrustStatus.getName(stockEntrust.getStatus()));
            if (stockEntrust.getCancelOrder().equals(BusinessStockEntrust.STOCK_ORDER)) {
                stockEntrust.setCancelOrder("委托");
            } else if (stockEntrust.getCancelOrder().equals(BusinessStockEntrust.STOCK_CANCEL_ORDER)) {
                stockEntrust.setCancelOrder("撤单");
            }
        });
        PageInfo<StockEntrustModel> page = new PageInfo<StockEntrustModel>(models);
        TranferStockEntrustModel tranferStockEntrustModel = new TranferStockEntrustModel();
        tranferStockEntrustModel.setPageInfo(page);
        tranferStockEntrustModel.setAutoBuy(redisTemplate.opsForValue().get(autoBuy));
        return tranferStockEntrustModel;
    }

    //未完成订单
    public List<StockEntrustModel> getUnFinishedEntrust(ContractModel model) {
        List<StockEntrustModel> stockEntrustModels = businessStockEntrustMapper.selectCancelEntrust(model);
        stockEntrustModels.forEach(stockEntrust -> {
            if (stockEntrust.getBusiness() == BusinessStockEntrust.STOCK_BUY) {
                stockEntrust.setBusinessStr("买入");
            } else if (stockEntrust.getBusiness() == BusinessStockEntrust.STOCK_SELL) {
                stockEntrust.setBusinessStr("卖出");
            }
            stockEntrust.setStatusStr(EntrustStatus.getName(stockEntrust.getStatus()));
        });
        return stockEntrustModels;
    }

    //撤单流程
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(StockEntrustModel model) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.
                findByDeleteFlagAndUuidAndUserId("0", model.getId(), model.getUserId());
        if (adminUuid.equals(model.getUserId())) {
            entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0", model.getId());
        }
        if (entrust == null) {
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        if (EntrustStatus.ENTRUST_FINSISH.getIndex() == entrust.getEntrustStatus() ||
                EntrustStatus.ENTRUST_BACK.getIndex() == entrust.getEntrustStatus()) {
            throw new BusinessException(ResultCode.ENTRUST_IS_DEAL);
        }
        if (EntrustStatus.ENTRUST_WAIT_DEAL.getIndex() == entrust.getEntrustStatus() ||
                EntrustStatus.ENTRUST_BACK_WAIT.getIndex() == entrust.getEntrustStatus() ||
                EntrustStatus.ENTRUST_BACK_ING.getIndex() == entrust.getEntrustStatus()) {
            throw new BusinessException(ResultCode.ENTRUST_WAIT_DEAL);
        }
        BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
        if (RudeStatus.BUSINESS_RUDE.getNum() == contract.getRudeStatus()) {
            throw new BusinessException(ResultCode.RUDE_CONTRACT_ERR);
        }
        if (EntrustStatus.ENTRUST_NOT_DEAL.getIndex() == entrust.getEntrustStatus()) {
            if (BusinessStockEntrust.STOCK_SELL.equals(entrust.getBusinessTo())) {
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
                entrust.setUpdatedTime(new Date());//返回冻结股份

                BusinessControlContract controlContract = new BusinessControlContract();
                controlContract.setControlType(ControlCode.CONTROL_SELLBACK.getIndex());
                controlContract.setContractId(entrust.getContractId());
                controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
                controlContract.setLessMoney(contract.getAvailableMoney());
                controlContract.setAddMoney(entrust.getColdMoney());
                controlContract.setCreatedTime(new Date());
                controlContract.setCreatorId(entrust.getUserId());
                businessControlContractRepository.save(controlContract);

                BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlagAndUuid("0", entrust.getHoldingId());
                holding.setColdAmount(BusinessUtils.minusIntMethod(holding.getColdAmount(), entrust.getBusinessAmount()));
                holding.setCanSell(BusinessUtils.addIntMethod(entrust.getBusinessAmount(), holding.getCanSell()));
                businessStockHoldingRepository.save(holding);
                businessStockEntrustRepository.save(entrust);
            } else if (BusinessStockEntrust.STOCK_BUY.equals(entrust.getBusinessTo())) {  //撤单买入
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
                entrust.setUpdatedTime(new Date());//返回冻结金

                contract.setColdMoney(BusinessUtils.minusMethod(contract.getColdMoney(), entrust.getColdMoney(), entrust.getBusinessMoney(), entrust.getTaxationMoney()));
                contract.setAvailableMoney(BusinessUtils.addMethod(contract.getAvailableMoney(), entrust.getColdMoney(), entrust.getTaxationMoney(), entrust.getBusinessMoney()));
                BusinessControlContract controlContract = new BusinessControlContract();
                controlContract.setControlType(ControlCode.CONTROL_BUYBACK.getIndex());
                controlContract.setContractId(entrust.getContractId());
                controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
                controlContract.setLessMoney(contract.getAvailableMoney());
                controlContract.setAddMoney(entrust.getColdMoney());
                controlContract.setCreatedTime(new Date());
                controlContract.setCreatorId(entrust.getUserId());
                businessControlContractRepository.save(controlContract);
                businessStockEntrustRepository.save(entrust);
                businessContractRepository.save(contract);
            }
        } else {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK_WAIT.getIndex());
            entrust.setUpdateUserId(model.getUserId());
            businessStockEntrustRepository.save(entrust);
            if (!autoDealBack(entrust, model.getStockNum())) {
                throw new BusinessException(ResultCode.ENTRUST_BACK_ERR);
            }
            ContractModel contractModel = businessContractMapper.selectContract(entrust.getContractId(), entrust.getUserId());
            if (null == contractModel) {
                throw new BusinessException(ResultCode.CONTACT_NOT_EXITS);
            }
            BusinessControlContract controlContract = new BusinessControlContract();
            controlContract.setCreatorId(entrust.getUserId());
            controlContract.setCreatedTime(new Date());
            controlContract.setLessMoney(BusinessUtils.addMethod(contractModel.getCanUseMoney(), contractModel.getColdCash()));
            controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlContract.setContractId(contractModel.getId());
            controlContract.setControlType(ControlCode.CONTROL_ENTRUSTBACKSTOCK.getIndex());
            businessControlContractRepository.save(controlContract);
        }

    }

    //自动撤单申报
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public Boolean autoDealBack(BusinessStockEntrust entrust, String stockNo) throws BusinessException {
        Boolean auto = Boolean.parseBoolean(redisTemplate.opsForValue().get(autoBuy));
        if (auto) {
            String account = entrust.getMontherAccount();
            String dealNo = entrust.getDealNo();
            if (!searchIsCancel(account, dealNo)) {
                throw new BusinessException(ResultCode.CANCEL_NULL_ERR);
            }
            String exchangeId = "";
            if (CommonUtil.regexString(shangRegex, stockNo)) {
                exchangeId = "1";
            } else if (CommonUtil.regexString(shenRegex, stockNo)) {
                exchangeId = "0";
            }
            CancleOrderRequestModel cancle = new CancleOrderRequestModel();
            cancle.setExchangeId(exchangeId);
            cancle.setMotnerAccount(account);
            cancle.setOrderNum(dealNo);
            cancle.setPszStockCode(stockNo);
            ResponseModel<String> response = exchangeFeignService.cancelOrder(cancle);
            if (null == response) {
                throw new BusinessException(ResultCode.STOCK_MSG_ERR);
            }
            response.setCode(ResultCode.SUCESS);//test
            if (ResultCode.SUCESS.equals(response.getCode())) {
                logger.debug("code:" + response.getCode() + "msg:" + response.getMsg());
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK_ING.getIndex());
                entrust.setUpdatedTime(new Date());
                entrust.setUpdateUserId(entrust.getUserId());
                entrust.setCancelOrder(BusinessStockEntrust.STOCK_CANCEL_ORDER);
                entrust.setCancelNo(response.getData());
                businessStockEntrustRepository.save(entrust);
                logger.debug("委托单号：" + entrust.getDealNo());
            } else {
                logger.debug("code:" + response.getCode() + "msg:" + response.getMsg());
                return false;
            }
        }
        return true;
    }

    //手动撤单申报
    @Transactional(rollbackFor = Exception.class)
    public void dealBackHand(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0", model.getId());
        if (entrust == null) {
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        entrust.setDealNo(model.getDealNo());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK_ING.getIndex());
        entrust.setUpdatedTime(new Date());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdateUserName(userInfo.getUserName());
        businessStockEntrustRepository.save(entrust);
    }

    //获取可撤单
    private Boolean searchIsCancel(String account, String dealNo) {
        ResponseModel<List<CanCancleOrder>> responseModel = exchangeFeignService.getCanCancleOrderList(account);
        List<CanCancleOrder> canCancleOrders = responseModel.getData();
        for (CanCancleOrder canCancleOrder : canCancleOrders) {
            if (canCancleOrder.getOrderNumber().equals(dealNo)) {
                return true;
            }
        }
        return false;
    }

    //修改自动手动
    public Map<String, String> setAutoSystem(UserTokenInfo userInfo) {
        Boolean auto = !Boolean.valueOf(redisTemplate.opsForValue().get(autoBuy));
        redisTemplate.opsForValue().set(autoBuy, auto.toString());
        Map<String, String> map = new HashMap<String, String>();
        map.put("auto", auto.toString());
        return map;
    }

    //获取当前操作状态
    public Map<String, String> getAuto() {
        Boolean auto = Boolean.valueOf(redisTemplate.opsForValue().get(autoBuy));
        Map<String, String> map = new HashMap<String, String>();
        map.put("auto", auto.toString());
        return map;
    }

    //最大买入量
    public StockHoldingModel getMaxBuyAmount(StockHoldingModel model) {
        ContractModel contractModel = businessContractMapper.selectContract(model.getContractId(), model.getUserId());
        if (null == contractModel) {
            return new StockHoldingModel();
        }
        List<StockHoldingModel> stockHoldingModels = businessStockHoldingMapper.selectStockNum(model.getContractId(), model.getStockNo(), "");
        Pattern pattern = Pattern.compile(chuangRegex);
        Matcher matcher = pattern.matcher(model.getStockNo());
        Double rate = 0.0;
        if (matcher.find()) {
            rate = contractModel.getVenturEditionMaxAccount();
        } else {
            rate = contractModel.getCustomerMaxAccount();
        }
        Double C0 = 0.0;
        if (stockHoldingModels.size() > 0) {
            StockHoldingModel stockHoldingModel = stockHoldingModels.get(0);
            C0 = stockHoldingModel.getCurrentWorth();
        }
        Double A = BusinessUtils.addMethod(contractModel.getColdCash(), contractModel.getWorth(), contractModel.getCanUseMoney());
        Double C1 = 0.0;
        try {
            C1 = BusinessUtils.minusMethod(A * rate, C0);
        } catch (BusinessException e) {
            logger.error("计算报错");
        }
        model.setCurrentWorth(C1);
        return model;
    }

    //撤单买入
    @Transactional(rollbackFor = Exception.class)
    public void updateColdMoneyAndSaveEntrust(TodayOrder order, String account) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndMontherAccountAndDealNo("0", account, order.getOrderNumber());
        if (null != entrust) {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
            entrust.setUpdatedTime(new Date());//返回冻结金
            BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
            contract.setColdMoney(BusinessUtils.minusMethod(contract.getColdMoney(), entrust.getColdMoney(), entrust.getBusinessMoney(), entrust.getTaxationMoney()));
            contract.setAvailableMoney(BusinessUtils.addMethod(contract.getAvailableMoney(), entrust.getColdMoney(), entrust.getTaxationMoney(), entrust.getBusinessMoney()));
            BusinessControlContract controlContract = new BusinessControlContract();
            controlContract.setControlType(ControlCode.CONTROL_BUYBACK.getIndex());
            controlContract.setContractId(entrust.getContractId());
            controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlContract.setLessMoney(contract.getAvailableMoney());
            controlContract.setAddMoney(entrust.getColdMoney());
            controlContract.setCreatedTime(new Date());
            controlContract.setCreatorId(entrust.getUserId());
            businessControlContractRepository.save(controlContract);
            businessStockEntrustRepository.save(entrust);
            businessContractRepository.save(contract);
        } else {
            logger.error("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
        }
    }

    //撤单卖出
    @Transactional(rollbackFor = Exception.class)
    public void updateHoldingAndSaveEntrust(TodayOrder order, String account) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndMontherAccountAndDealNo("0", account, order.getOrderNumber());
        if (null != entrust) {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
            entrust.setUpdatedTime(new Date());//返回冻结股份
            BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());

            BusinessControlContract controlContract = new BusinessControlContract();
            controlContract.setControlType(ControlCode.CONTROL_SELLBACK.getIndex());
            controlContract.setContractId(entrust.getContractId());
            controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlContract.setLessMoney(contract.getAvailableMoney());
            controlContract.setAddMoney(entrust.getColdMoney());
            controlContract.setCreatedTime(new Date());
            controlContract.setCreatorId(entrust.getUserId());
            businessControlContractRepository.save(controlContract);

            BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlagAndUuid("0", entrust.getHoldingId());
            holding.setColdAmount(BusinessUtils.minusIntMethod(holding.getColdAmount(), (int) order.getOrderQuantity()));
            holding.setCanSell(BusinessUtils.addIntMethod((int) order.getOrderQuantity(), holding.getCanSell()));
            businessStockHoldingRepository.save(holding);
            businessStockEntrustRepository.save(entrust);
        } else {
            logger.error("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
        }
    }

    //定时修改买入成交
    @Transactional(rollbackFor = Exception.class)
    public void dealBuyMethod(TodayAcceptOrder order, String account) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository
                .findByDeleteFlagAndMontherAccountAndDealNo("0", account, order.getOrderNumber());
        if (null != entrust) {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_FINSISH.getIndex());
            entrust.setUpdatedTime(new Date());
            entrust.setDealNum(BusinessUtils.addIntMethod((int) order.getActQuantity(), entrust.getDealNum()));
            entrust.setDealPrice(BusinessUtils.avgMethod((double) order.getActPrice(), entrust.getDealPrice()));
            entrust.setDealTime(System.currentTimeMillis());
            Double rate = 1 - (entrust.getDealNum() * entrust.getDealPrice()) / (entrust.getBusinessAmount() * entrust.getBusinessPrice());
            BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
            contract.setColdMoney(BusinessUtils.minusMethod(contract.getColdMoney(), BusinessUtils.multiplicationMethod(entrust.getColdMoney(), (1 - rate)), BusinessUtils.multiplicationMethod(entrust.getBusinessMoney(), (1 - rate)), BusinessUtils.multiplicationMethod(entrust.getTaxationMoney(), (1 - rate))));
            contract.setUpdatedTime(new Date());

            BusinessStockHolding holding = businessStockHoldingRepository
                    .findBusinessStockHoldingByDeleteFlagAndStockIdAndContractId("0", entrust.getStockId(), entrust.getContractId());
            if (holding == null) {
                holding = new BusinessStockHolding();
                holding.setCreatedTime(new Date());
                holding.setCreatorId(entrust.getUserId());
                holding.setMotherAccount(entrust.getMontherAccount());
                holding.setContractId(entrust.getContractId());
                holding.setStockId(entrust.getStockId());
            }
            holding.setCostPrice(BusinessUtils.avgStockPrice(holding.getCostPrice(), holding.getAmount(), entrust.getDealPrice(), entrust.getDealNum()));
            holding.setAmount(BusinessUtils.addIntMethod(holding.getAmount(), entrust.getDealNum()));
            holding.setColdAmount(BusinessUtils.addIntMethod(holding.getColdAmount(), entrust.getDealNum()));
            holding.setUpdatedTime(new Date());
            holding.setUpdateUserId(entrust.getUserId());
            //盈亏暂不算，等调度股票价钱时算
            holding = businessStockHoldingRepository.save(holding);

            BusinessControlContract controlContract = new BusinessControlContract();
            controlContract.setCreatorId(entrust.getUserId());
            controlContract.setCreatedTime(new Date());
            controlContract.setLessMoney(BusinessUtils.addMethod(contract.getAvailableMoney(), contract.getColdMoney()));
            controlContract.setAddMoney(entrust.getColdMoney() * rate);
            controlContract.setBusinessMoney(entrust.getBusinessMoney() * rate);
            controlContract.setTaxationMoney(entrust.getTaxationMoney() * rate);
            controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlContract.setContractId(contract.getUuid());
            controlContract.setControlType(ControlCode.CONTROL_BUYSTOCK.getIndex());
            businessControlContractRepository.save(controlContract);

            entrust.setHoldingId(holding.getUuid());
            businessContractRepository.save(contract);
            businessStockEntrustRepository.save(entrust);
        } else {
            logger.error("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
        }
    }

    //定时修改卖出成交
    @Transactional(rollbackFor = Exception.class)
    public void dealSellMethod(TodayAcceptOrder order, String account) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository
                .findByDeleteFlagAndMontherAccountAndDealNo("0", account, order.getOrderNumber());
        if (entrust.getDealNum() == entrust.getBusinessAmount()) {
            return;
        }
        if (null != entrust) {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_FINSISH.getIndex());
            entrust.setDealPrice(BusinessUtils.avgMethod(entrust.getDealPrice(), (double) order.getActPrice()));
            entrust.setDealNum(BusinessUtils.addIntMethod(entrust.getDealNum(), (int) order.getActQuantity()));
            entrust.setDealTime(System.currentTimeMillis());
            entrust.setUpdatedTime(new Date());
            businessStockEntrustRepository.save(entrust);
            Double taxation = 0.0;
            double fixedTaxation = 0.0;
            List<BusinessTaxation> businessTaxations = businessTaxationRepository.findByDeleteFlagAndBsuinessTo("0", BusinessTaxation.BUSINESS_SELL);
            for (BusinessTaxation businessTaxation : businessTaxations) {
                if (BusinessTaxation.PERCENT_MONEY.equals(businessTaxation.getFixed())) {
                    taxation = taxation + businessTaxation.getTaxRate();
                }
                if (BusinessTaxation.FIXED_MONEY.equals(businessTaxation.getFixed())) {
                    fixedTaxation += businessTaxation.getTaxRate();
                }
            }
            Double gainMoney = (double) (order.getActPrice() * order.getActQuantity());
            ContractModel contractModel = null;
            if (adminUuid.equals(entrust.getUserId())) {
                contractModel = businessContractMapper.selectContractById(entrust.getContractId());
            } else {
                contractModel = businessContractMapper.selectContract(entrust.getContractId(), entrust.getUserId());
            }
            if (null == contractModel) {
                throw new BusinessException(ResultCode.CONTACT_NOT_EXITS);
            }
            Double businessMoney = contractModel.getBusinessRate() * gainMoney;
            taxation = taxation * gainMoney + fixedTaxation;
            gainMoney = BusinessUtils.minusMethod(gainMoney, businessMoney, taxation);
            BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
            contract.setAvailableMoney(contract.getAvailableMoney() + gainMoney);
            contract.setUpdatedTime(new Date());
            contract.setUpdateUserId(entrust.getUserId());
            businessContractRepository.save(contract);

            BusinessControlContract controlContract = new BusinessControlContract();
            controlContract.setAddMoney(gainMoney);
            controlContract.setTaxationMoney(taxation);
            controlContract.setBusinessMoney(businessMoney);
            controlContract.setControlType(ControlCode.CONTROL_SELLSTOCK.getIndex());
            controlContract.setContractId(contract.getUuid());
            controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlContract.setLessMoney(BusinessUtils.addMethod(contract.getAvailableMoney(), contract.getColdMoney()));
            businessControlContractRepository.save(controlContract);

            BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlagAndUuid("0", entrust.getHoldingId());
            if (null == holding) {
                logger.error(entrust.getUuid() + "多出售了一笔股票");
            }
            holding.setColdAmount(BusinessUtils.minusIntMethod(holding.getColdAmount(), (int) order.getActQuantity()));
            holding.setAmount(BusinessUtils.minusIntMethod(holding.getAmount(), (int) order.getActQuantity()));
            holding.setUpdatedTime(new Date());
            if (holding.getCanSell() == 0 && holding.getAmount() == 0 && holding.getColdAmount() == 0) {
                businessStockHoldingRepository.delete(holding);
            } else {
                businessStockHoldingRepository.save(holding);
            }
        } else {
            logger.error("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号：" + order.getOrderNumber());
        }
    }

    //排队访问股价接口
    private List<QuotesTenModel> getQuotesTenModel(String... stockNo) {
        List<String[]> strings = BusinessUtils.getListStringArray(stockNo, 50);
        List<QuotesTenModel> quotesTenModels = new ArrayList<QuotesTenModel>();
        for (int j = 0; j < strings.size(); j++) {
            List<String> markets = new ArrayList<String>();
            List<String> stockCodes = new ArrayList<String>();
            String exchangeId = "";
            for (int i = 0; i < stockNo.length; i++) {
                if (CommonUtil.regexString(shangRegex, stockNo[i])) {
                    exchangeId = "1";
                } else if (CommonUtil.regexString(shenRegex, stockNo[i])) {
                    exchangeId = "0";
                }
                markets.add(exchangeId);
                stockCodes.add(stockNo[i]);
            }
            GetQuotesTenListRequestModel requestModel = new GetQuotesTenListRequestModel();
            requestModel.setMarkets(markets);
            requestModel.setStockCodes(stockCodes);
            ResponseModel<List<QuotesTenModel>> response = stockPriceFeignService.getQuotesTenList(requestModel);
            if (ResultCode.SUCESS.equals(response.getCode())) {
                List<String> result = new ArrayList<String>();
                for (int z = 0; z < response.getData().size(); z++) {
                    quotesTenModels.add(response.getData().get(z));
                    result.add(response.getData().get(z).getStockCode());
                }
                if (response.getData().size() != stockCodes.size()) {
                    quotesTenModels = getBusinessStock(result, stockCodes, quotesTenModels);
                }

            }
        }
        return quotesTenModels;
    }

    //获取交易中的股票当前信息
    private List<QuotesTenModel> getBusinessStock(List<String> result, List<String> stockCodes, List<QuotesTenModel> quotesTenModels) {
        List<String> stock = new ArrayList<String>();
        List<String> mart = new ArrayList<String>();
        for (String stockCode : stockCodes) {
            if (!result.contains(stockCode)) {
                stock.add(stockCode);
                mart.add("2");
            }
        }
        GetQuotesTenListRequestModel requestModel = new GetQuotesTenListRequestModel();
        requestModel.setMarkets(mart);
        requestModel.setStockCodes(stock);
        ResponseModel<List<QuotesTenModel>> responseModel = stockPriceFeignService.getQuotesTenList(requestModel);
        if (ResultCode.SUCESS.equals(responseModel.getCode())) {
            for (int z = 0; z < responseModel.getData().size(); z++) {
                quotesTenModels.add(responseModel.getData().get(z));
            }
        }
        return quotesTenModels;
    }

    //每日获取买入量
    public void getFiveDayMaxAmount() {
        //判断是否是假期，是就不更新
        long newTime = System.currentTimeMillis();
        boolean flag = systemService.isInHoliday(newTime);
        //否更新数据
        if (!flag) {
            //获取所有的在售股票代码
            List<TransactionModel> transactionModels = itemService.findAll(new TransactionModel());
            List<String> stockCodes = new ArrayList<>();
            transactionModels.forEach(transactionModel -> {
                stockCodes.add(transactionModel.getStockNum());
            });
            List<QuotesTenModel> quotesTenModels = getQuotesTenModel(stockCodes.toArray(new String[stockCodes.size()]));
            itemService.oneDayUpdateStock(quotesTenModels);//修改每日股票总量
        }
    }

    //每日清算冻结及可卖
    public void everyDayClear() {
        List<BusinessContract> contracts = businessContractRepository.findByDeleteFlagAndContractStatus("0", BusinessStatus.CONTRACT_BUSINESS.getNum());
        for (BusinessContract contract : contracts) {
            Double coldMoney = contract.getColdMoney();
            contract.setAvailableMoney(BusinessUtils.addMethod(contract.getColdMoney(), contract.getAvailableMoney()));
            contract.setColdMoney(0.0);
            List<BusinessStockEntrust> stockEntrusts = businessStockEntrustRepository.findByContractId(contract.getUuid());
            for (BusinessStockEntrust stockEntrust : stockEntrusts) {
                stockEntrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
            }
            List<BusinessStockHolding> holdings = businessStockHoldingRepository.findByDeleteFlagAndContractId("0", contract.getUuid());
            for (BusinessStockHolding holding : holdings) {
                holding.setAmount(BusinessUtils.addIntMethod(holding.getColdAmount(), holding.getAmount()));
                holding.setCanSell(holding.getAmount());
                holding.setColdAmount(0);
            }
            businessStockEntrustRepository.saveAll(stockEntrusts);
            businessStockHoldingRepository.saveAll(holdings);
            if (coldMoney != 0) {
                BusinessControlContract controlContract = new BusinessControlContract();
                controlContract.setContractId(contract.getUuid());
                controlContract.setLessMoney(contract.getAvailableMoney());
                controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
                controlContract.setControlType(ControlCode.CONTROL_EVERYDAY_BACK.getIndex());
                controlContract.setAddMoney(coldMoney);
                controlContract.setCreatedTime(new Date());
                controlContract.setUpdatedTime(new Date());
                businessControlContractRepository.save(controlContract);
            }
        }
        businessContractRepository.saveAll(contracts);
    }

    //合约交易信息
    public ContractModel getBusinessInfo(UserTokenInfo userInfo) {
        List<ContractModel> contractModels = businessContractMapper.selectCurrentContract(userInfo.getUuid());
        ContractModel contract = new ContractModel();
        for (ContractModel model : contractModels) {
            contract.setFloatMoney(BusinessUtils.addMethod(contract.getFloatMoney(), model.getFloatMoney()));
            contract.setCanUseMoney(BusinessUtils.addMethod(contract.getCanUseMoney(), model.getCanUseMoney()));
        }
        return contract;
    }

    //结束合约
    @Transactional(rollbackFor = Exception.class)
    public ContractModel endContract(ContractModel model) throws BusinessException {
        if (businessStockHoldingMapper.countStockInContract(model.getId(), "") > 0) {
            throw new BusinessException(ResultCode.STOCK_HOLDING_EXIST_ERR);
        }
        if (businessStockEntrustRepository
                .countByDeleteFlagAndContractIdAndEntrustStatus("0", model.getId(), EntrustStatus.ENTRUST_REPORT.getIndex()) > 0) {
            throw new BusinessException(ResultCode.ENTRUST_IS_EXIST);
        }
        BusinessContract contract = businessContractRepository.findByUuid(model.getId());
        Double borrow = contract.getBorrowMoney();
        Double dangrousPromised = contract.getDangerourPrpmised();
        Double avaliable = contract.getAvailableMoney();
        Double coldMoney = contract.getColdMoney();
        Double lessMoney = BusinessUtils.addMethod(dangrousPromised, avaliable, coldMoney);
        if (lessMoney < borrow) {
            throw new BusinessException(ResultCode.CONTACT_ARREAR_ERR);
        }
        Double backMoney = lessMoney - borrow;
        contract.setAvailableMoney(0.0);
        contract.setColdMoney(0.0);
        contract.setRudeStatus(model.getRudeEnd());
        contract.setContractStatus(BusinessStatus.CONTRACT_END.getNum());
        contract.setDeleteFlag("1");
        businessContractRepository.save(contract);
        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setCreatedTime(new Date());
        controlContract.setLessMoney(contract.getAvailableMoney() + contract.getColdMoney());
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setControlType(ControlCode.CONTROL_SHUT.getIndex());
        controlContract.setCreatorId(model.getUserId());
        controlContract.setContractId(contract.getUuid());
        controlContract.setWindUpMoney(backMoney);
        controlContract.setUpdateUserId(model.getUserId());
        controlContract.setUpdatedTime(new Date());
        businessControlContractRepository.save(controlContract);
        model.setCanUseMoney(backMoney);
        model.setUserId(contract.getUserId());
        model.setContractNum(contract.getContractNum());
        return model;
    }

    //分页获取合约内委托
    public PageInfo<StockEntrustModel> getContractEntrusts(StockEntrustModel model) throws BusinessException {
        List<StockEntrustModel> pageInfo = new ArrayList<StockEntrustModel>();
        List<StockEntrustModel> pageList = new ArrayList<StockEntrustModel>();
        List<StockEntrustModel> stockEntrustModels = businessStockEntrustMapper.getContractEntrusts(model);
        for (int i = stockEntrustModels.size() - 1; i >= 0; i--) {
            if (stockEntrustModels.get(i).getCreatedTime().getTime() < model.getDealTime()) {
                stockEntrustModels.remove(stockEntrustModels.get(i));
            }
        }
        if (!StringUtils.isEmpty(model.getDealNo())) {
            for (StockEntrustModel stockEntrust : stockEntrustModels) {
                if (EntrustStatus.ENTRUST_FINSISH.getIndex() == stockEntrust.getStatus()) {
                    pageInfo.add(stockEntrust);
                }
            }
        } else {
            pageInfo = stockEntrustModels;
        }
        int begin = (model.getPageIndex() - 1) * model.getPageSize();
        int end = (model.getPageIndex() - 1) * model.getPageSize() + model.getPageSize()
                > pageInfo.size() ? pageInfo.size() : (model.getPageIndex() - 1) * model.getPageSize() + model.getPageSize();
        for (int i = begin; i < end; i++) {
            pageList.add(pageInfo.get(i));
        }
        pageList.forEach(stockEntrust -> {
            if (stockEntrust.getBusiness() == BusinessStockEntrust.STOCK_BUY) {
                stockEntrust.setBusinessStr("买入");
            } else if (stockEntrust.getBusiness() == BusinessStockEntrust.STOCK_SELL) {
                stockEntrust.setBusinessStr("卖出");
            }
            stockEntrust.setStatusStr(EntrustStatus.getName(stockEntrust.getStatus()));
        });
        PageInfo<StockEntrustModel> page = new PageInfo<StockEntrustModel>();
        page.setTotal(pageInfo.size());
        page.setPageNum(model.getPageIndex());
        page.setPageSize(model.getPageSize());
        page.setList(pageList);
        return page;
    }

    //高频合约
    public void highFrequency() throws Exception {
        if (redisTemplate.hasKey(controlContractKey)) {
            Set<String> contractIds = JSONObject.parseObject(redisTemplate.opsForValue().get(controlContractKey), ContractJsonModel.class).getContractIds();
            for (String contractId : contractIds) {
                ContractModel model = businessContractMapper.selectContractById(contractId);
                if (null == model) {
                    continue;
                }
                List<StockHoldingModel> holdings = businessStockHoldingMapper.selectStockNum(contractId, "", "");
                double strongWorth = (model.getPromisedMoney() + model.getBorrowMoney()) * model.getAbortLine() - model.getColdCash() - model.getCanUseMoney();
                double sumMoney = 0;
                int sumQuantity = 0;
                List<String> stockCodes = new ArrayList<String>();
                Map<String, Integer> quantitiesMap = new HashMap<String, Integer>();
                Map<String, Integer> quantitiesCanSell = new HashMap<String, Integer>();
                for (StockHoldingModel holdingModel : holdings) {
                    if (0 == holdingModel.getCanSell()) {
                        continue;
                    }
                    stockCodes.add(holdingModel.getStockNo());
                    sumMoney += holdingModel.getCurrentWorth();
                    sumQuantity += holdingModel.getAmount();
                    quantitiesMap.put(holdingModel.getStockNo(), holdingModel.getAmount());
                    quantitiesCanSell.put(holdingModel.getStockNo(), holdingModel.getCanSell());
                }
                if (0 == sumQuantity) {
                    if (strongWorth >= 0) {     //无股份，且到达强平线，强平结束合约
                        ContractJsonModel contractJsonModel = new ContractJsonModel();
                        if (redisTemplate.hasKey(rudeEndContractKey)) {
                            contractJsonModel = JSONObject.parseObject(redisTemplate.opsForValue().get(rudeEndContractKey), ContractJsonModel.class);
                        }
                        contractJsonModel.getContractIds().add(contractId);
                        redisTemplate.opsForValue().set(rudeEndContractKey, JSONObject.toJSONString(contractJsonModel));
                    }
                    continue;
                }
                List<QuotesTenModel> quotesTenModels = getQuotesTenModel(stockCodes.toArray(new String[stockCodes.size()]));
                if (holdings.size() == 1) {
                    //单股持仓
                    double strongMoney = strongWorth / sumQuantity;
                    double controlMoney = strongMoney + controlPrice;
                    QuotesTenModel tenModel = quotesTenModels.get(0);
                    double buyOnePrice = tenModel.getBuyTenPrice()[0];
                    int buyOneQuantity = tenModel.getBuyTenQuantity()[0];
                    if ((buyOnePrice <= controlMoney && buyOneQuantity <= sumQuantity * 0.8) || buyOnePrice <= strongMoney) {
                        endEntrustAtStrong(contractId);//撤单所有
                        List<Integer> quantities = new ArrayList<>();
                        quantities.add(holdings.get(0).getCanSell());
                        List<Double> prices = new ArrayList<>();
                        prices.add((double) tenModel.getCurPrice());
                        clearRepository(stockCodes, quantities, prices, contractId);
                    }
                } else {
                    //多股持仓
                    double avgPrice = sumMoney / sumQuantity;
                    double strongMoney = strongWorth / sumQuantity;
                    double controlMoney = strongMoney + controlPrice;
                    if (avgPrice <= controlMoney) {
                        for (QuotesTenModel tenModel : quotesTenModels) {
                            if (tenModel.getBuyTenQuantity()[0] <= quantitiesMap.get(tenModel.getStockCode())) {
                                //卖出所有可卖
                                endEntrustAtStrong(contractId);//撤单所有
                                List<Integer> quantities = new ArrayList<Integer>();
                                List<String> stockNos = new ArrayList<String>();
                                List<Double> prices = new ArrayList<Double>();
                                for (QuotesTenModel quotesTenModel : quotesTenModels) {
                                    stockNos.add(quotesTenModel.getStockCode());
                                    prices.add((double) quotesTenModel.getCurPrice());
                                    quantities.add(quantitiesCanSell.get(quotesTenModel.getStockCode()));
                                }
                                clearRepository(stockNos, quantities, prices, contractId);
                                break;
                            }
                        }
                    }
                }
            }
        }
        oneStockAbort();//高股特殊平仓
    }

    //全仓平仓
    private void clearRepository(List<String> stockCodes, List<Integer> amounts, List<Double> prices, String contractId) throws BusinessException {
        if (null == amounts || null == stockCodes || 0 == amounts.size() || 0 == stockCodes.size()
                || 0 == prices.size() || null == prices || StringUtils.isEmpty(contractId)) {
            throw new BusinessException(ResultCode.PARAM_MISS_MSG);
        }
        if (stockCodes.size() != amounts.size() || stockCodes.size() != prices.size() || amounts.size() != prices.size()) {
            throw new BusinessException(ResultCode.PARAM_ERR_MSG);
        }
        for (int i = 0; i < stockCodes.size(); i++) {
            StockHoldingModel model = new StockHoldingModel();
            model.setAmount(amounts.get(i));
            model.setUserId(adminUuid);
            model.setCostPrice(prices.get(i));
            model.setContractId(contractId);
            model.setStockNo(stockCodes.get(i));
            model.setDealFrom(BusinessStockEntrust.DEAL_ADMIN);
            try {
                synchronizeComponent.synchronizedSellHoldStock(model);
            } catch (BusinessException e) {
                logger.error(model.getContractId() + "平仓出售" + model.getStockNo() + "股份失败");
            }
        }
        BusinessContract contract = businessContractRepository.findByDeleteFlagAndUuid("0", contractId);
        contract.setRudeStatus(RudeStatus.BUSINESS_RUDE.getNum());
        businessContractRepository.save(contract);
    }

    //修改合约内的所有股票
    public void updateStockPrice() {
        //修改股票现值
        List<StockHoldingModel> stocks = businessStockHoldingMapper.selectHoldStockAll();
        Set<String> stockNos = new HashSet<String>();
        Map<String, String> stockMap = new HashMap<String, String>();
        stocks.forEach(stock -> {
            stockNos.add(stock.getStockNo());
            stockMap.put(stock.getStockId(), stock.getStockNo());
        });
        List<QuotesTenModel> quotesTenModels = getQuotesTenModel(stockNos.toArray(new String[stockNos.size()]));
        for (QuotesTenModel quotesTenModel : quotesTenModels) {
            for (StockHoldingModel stockHoldingModel : stocks) {
                if (quotesTenModel.getStockCode().equals(stockHoldingModel.getStockNo())) {
                    stockHoldingModel.setCurrentPrice((double) quotesTenModel.getCurPrice());
                    businessStockHoldingMapper.updateHoldingStockPrice(stockHoldingModel);
                }
            }
        }
        //找出高频监控合约，放入redis
        List<ContractModel> contractModels = businessContractMapper.selectContractRisk();
        Double contractRisk = 0.0;
        Set<String> contractIds = new HashSet<String>();
        Set<ContractHoldingModel> holdingContractIds = new HashSet<ContractHoldingModel>();
        for (ContractModel model : contractModels) {    //高频合约
            contractRisk = BusinessUtils.addMethod(model.getCanUseMoney(), model.getColdCash(), model.getWorth()) / (model.getBorrowMoney() + model.getPromisedMoney());
            double highRate = model.getHighRate() == 0 ? 0.01 : model.getHighRate();
            if (contractRisk - model.getAbortLine() <= highRate) {
                contractIds.add(model.getId());
                continue;
            }
            if (contractRisk < model.getWarningLine()) {    //单股高频
                List<BusinessStockHolding> stockHoldings = businessStockHoldingRepository.findByDeleteFlagAndContractId("0", model.getId());
                for (BusinessStockHolding holding : stockHoldings) {
                    if (holding.getFloatRate() < downStockRate) {
                        ContractHoldingModel contractHoldingModel = new ContractHoldingModel();
                        contractHoldingModel.setContractId(holding.getContractId());
                        contractHoldingModel.setHoldingId(holding.getUuid());
                        contractHoldingModel.setStockCode(stockMap.get(holding.getStockId()));
                        holdingContractIds.add(contractHoldingModel);
                    }
                }
                continue;
            }
            if (contractRisk < model.getWarningLine()) {
                BusinessContract contract = businessContractRepository.findByDeleteFlagAndUuid("0", model.getId());
                if (null != contract) {
                    contract.setWarnningStatus(RudeStatus.CONTRACT_WARN.getNum());
                    businessContractRepository.save(contract);
                }
            }
        }
        ContractHoldingJsonModel holdingJsonModel = new ContractHoldingJsonModel();
        holdingJsonModel.setContractHoldingModels(holdingContractIds);
        ContractJsonModel contractJsonModel = new ContractJsonModel();
        contractJsonModel.setContractIds(contractIds);
        redisTemplate.opsForValue().set(stockContractKey, JSON.toJSONString(holdingJsonModel));
        redisTemplate.opsForValue().set(controlContractKey, JSON.toJSONString(contractJsonModel));
    }

    //单只股票平仓
    public void oneStockAbort() throws BusinessException {
        Set<ContractHoldingModel> contractHoldingModels = JSONObject.parseObject(redisTemplate.opsForValue().get(stockContractKey), ContractHoldingJsonModel.class).getContractHoldingModels();
        Set<String> stockCodes = new HashSet<String>();
        for (ContractHoldingModel holdingModel : contractHoldingModels) {
            stockCodes.add(holdingModel.getStockCode());
        }
        List<QuotesTenModel> quotesTenModels = getQuotesTenModel(stockCodes.toArray(new String[stockCodes.size()]));
        for (QuotesTenModel quotesTenModel : quotesTenModels) {
            String stockCode = quotesTenModel.getStockCode();
            double shutDownPrice = quotesTenModel.getClosePrice() * 0.9;
            double buyOnePrice = quotesTenModel.getBuyTenPrice()[0];
            if (buyOnePrice <= shutDownPrice + controlPrice) {
                for (ContractHoldingModel holdingModel : contractHoldingModels) {
                    if (stockCode.equals(holdingModel.getStockCode())) {
                        BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlagAndUuid("0", holdingModel.getHoldingId());
                        holding.setRudeEnd(RudeStatus.BUSINESS_CONTROL.getNum());
                        if (0 != holding.getCanSell()) {
                            StockHoldingModel model = new StockHoldingModel();
                            model.setAmount(holding.getCanSell());
                            model.setUserId(adminUuid);
                            model.setCostPrice((double) quotesTenModel.getCurPrice());
                            model.setContractId(holding.getContractId());
                            model.setStockNo(stockCode);
                            model.setDealFrom(BusinessStockEntrust.DEAL_ADMIN);
                            synchronizeComponent.synchronizedSellHoldStock(model);
                        }

                    }
                }
            }
        }
    }

    //当前平仓合约状态
    public void contractRudeEnd() throws BusinessException {
        List<BusinessContract> contracts = businessContractRepository.findByDeleteFlagAndContractStatus("0", RudeStatus.BUSINESS_RUDE.getNum());
        for (BusinessContract contract : contracts) {
            String id = contract.getUuid();
            if (!(businessStockHoldingMapper.countStockInContract(id, "") > 0)) {
                ContractModel model = new ContractModel();
                model.setId(id);
                model.setRudeEnd(RudeStatus.BUSINESS_RUDE.getNum());
                model = synchronizeComponent.synchronizedEndContract(model);
                ResponseModel responseModel = customerFeignService.rudeEndContract(model);
            }
        }
    }

    //每日检查扣除服务费
    public void chargeServerMoney() {
        List<BusinessContract> contracts = businessContractRepository.findByDeleteFlagAndContractStatus("0", BusinessStatus.CONTRACT_BUSINESS.getNum());
        for (BusinessContract contract : contracts) {
            if (ProductStatus.DAYS.getIndex() == contract.getChoseWay()) {
                //日配
                costServerMoney(contract);
            } else if (ProductStatus.MONTHS.getIndex() == contract.getChoseWay()) {
                //月配
                if (System.currentTimeMillis() > contract.getExpiredTime()) {
                    costServerMoney(contract);
                    contract.setExpiredTime(CommonUtil.getMonthExpiredTime());
                    businessContractRepository.save(contract);
                }
            } else {
                //特殊，日扣
                if (System.currentTimeMillis() > contract.getExpiredTime()) {
                    costServerMoney(contract);
                }
            }
        }
    }

    //记录扣除服务费
    private void costServerMoney(BusinessContract contract) {
        contract.setAvailableMoney(contract.getAvailableMoney() - (contract.getBorrowMoney() * contract.getOnceServerMoney()));
        businessContractRepository.save(contract);
        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setUpdateUserId(adminUuid);
        controlContract.setCreatorId(adminUuid);
        controlContract.setCreatedTime(new Date());
        controlContract.setContractId(contract.getUuid());
        controlContract.setControlType(ControlCode.CONTROL_PAYINTEREST.getIndex());
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setLessMoney(contract.getAvailableMoney());
        controlContract.setCostMoney(contract.getOnceServerMoney());
        businessControlContractRepository.save(controlContract);
    }

    //将合约内所有申报撤单
    private void endEntrustAtStrong(String contractId) throws Exception {
        List<StockEntrustModel> entrustModels = businessStockEntrustMapper.getCancelEntrust(contractId);
        for (StockEntrustModel entrustModel : entrustModels) {
            entrustModel.setUserId(adminUuid);
            synchronizeComponent.synchronizedCancelOrder(entrustModel);
        }
    }

    //测试for update起不起作用，可删
    @Transactional(rollbackFor = Exception.class)
    public void testForUpdate() {
        String id = "123";
        BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlag("0", id);
        holding.setCanSell(holding.getCanSell() + 100);
        businessStockHoldingRepository.save(holding);
    }

    //获取当前所有人的委托信息
    public PageInfo<TranferEntrustModel> getSystemEntrusts(TranferEntrustModel model) throws BusinessException {
        PageHelper.startPage(model.getPageIndex(), model.getPageSize());
        if (TranferEntrustModel.ENTRUST_FLAG.equals(model.getDealFlag())) {
            if (!StringUtils.isEmpty(model.getBeginTime())) {
                Date begin = CommonUtil.getDateFromStr(entrustPattern, model.getBeginTime());
                model.setEntrustBegin(begin);
            }
            if (!StringUtils.isEmpty(model.getEndTime())) {
                Date end = CommonUtil.getDateFromStr(entrustPattern, model.getEndTime());
                model.setEntrustEnd(end);
            }
        } else if (TranferEntrustModel.ENTRUST_DEAL_FLAG.equals(model.getDealFlag())) {
            if (!StringUtils.isEmpty(model.getBeginTime())) {
                Date begin = CommonUtil.getDateFromStr(entrustPattern, model.getBeginTime());
                model.setDealBegin(begin.getTime());
            }
            if (!StringUtils.isEmpty(model.getEndTime())) {
                Date end = CommonUtil.getDateFromStr(entrustPattern, model.getEndTime());
                model.setDealEnd(end.getTime());
            }
        } else {
            throw new BusinessException(ResultCode.PARAM_ERR_MSG);
        }
        List<TranferEntrustModel> stockEntrustModels = businessStockEntrustMapper.getAllEntrusts(model);
        for (TranferEntrustModel entrustModel : stockEntrustModels) {
            entrustModel.setStatusStr(EntrustStatus.getName(entrustModel.getStatus()));
            if (0 == entrustModel.getBusiness()) {
                entrustModel.setBusinessStr("买入");
            } else if (1 == entrustModel.getBusiness()) {
                entrustModel.setBusinessStr("卖出");
            }
            if (entrustModel.getDealPrice() != null) {
                entrustModel.setDealMoney(BusinessUtils.multiplicationMethod(entrustModel.getDealPrice(), (double) entrustModel.getDealNum()));
            }
            entrustModel.setEntrustTime(entrustModel.getCreatedTime().getTime());
            entrustModel.setProductName(distributService.getProductName(entrustModel.getProductId()));
            List<TaxationModel> taxationModels = businessTaxationMapper.selectEntrustTax(entrustModel.getId());
            for (TaxationModel taxationModel : taxationModels) {
                double taxation = 0.0;
                if (BusinessTaxation.PERCENT_MONEY.equals(taxationModel.getFixed())) {
                    taxation = BusinessUtils.multiplicationMethod(entrustModel.getDealMoney(), taxationModel.getTaxationRate());
                }
                if (BusinessTaxation.FIXED_MONEY.equals(taxationModel.getFixed())) {
                    taxation = taxationModel.getTaxationRate();
                }
                entrustModel.getMap().put(taxationModel.getLabelName(), String.valueOf(taxation));
            }
        }
        PageInfo<TranferEntrustModel> pageInfo = new PageInfo<TranferEntrustModel>(stockEntrustModels);
        return pageInfo;
    }

    //获取查询委托相关条件
    public Map<String, Object> getEntrustCondition() {
        Map<String, Object> map = new HashMap<String, Object>();
        ProductStatus[] productStatuses = ProductStatus.values();
        List<EnumTypeModel> products = new ArrayList<EnumTypeModel>();
        for (ProductStatus status : productStatuses) {
            EnumTypeModel enumTypeModel = new EnumTypeModel();
            enumTypeModel.setType(status.getIndex());
            enumTypeModel.setTypeStr(status.getName());
            products.add(enumTypeModel);
        }
        EntrustStatus[] entrustStatuses = EntrustStatus.values();
        List<EnumTypeModel> entrusts = new ArrayList<EnumTypeModel>();
        for (EntrustStatus status : entrustStatuses) {
            EnumTypeModel enumTypeModel = new EnumTypeModel();
            enumTypeModel.setType(status.getIndex());
            enumTypeModel.setTypeStr(status.getName());
            entrusts.add(enumTypeModel);
        }
        List<TaxationModel> taxationModels = businessTaxationMapper.getEntrustTaxLabel();
        map.put("products", products);
        map.put("entrusts", entrusts);
        map.put("taxations", taxationModels);
        return map;
    }

    //查询分也持仓
    public PageInfo<TranferHoldingModel> getPageHolding(TranferHoldingModel model) {
        PageHelper.startPage(model.getPageIndex(), model.getPageSize());
        List<TranferHoldingModel> tranferHoldingModels = businessStockHoldingMapper.getAllHolding(model);
        for (TranferHoldingModel tranferHoldingModel : tranferHoldingModels) {
            tranferHoldingModel.setProductName(ProductStatus.getName(tranferHoldingModel.getChoseWay()));
        }
        PageInfo<TranferHoldingModel> pageInfo = new PageInfo<TranferHoldingModel>(tranferHoldingModels);
        return pageInfo;
    }

    //根据id查contract平仓
    public void abortRepository(String contractId) throws Exception {
        ContractModel model = businessContractMapper.selectContractById(contractId);
        if (null == model) {
            throw new BusinessException(ResultCode.SELECT_NULL_MSG);
        }
        List<String> stockCodes = new ArrayList<String>();
        Map<String, Integer> quantitiesCanSell = new HashMap<String, Integer>();
        List<StockHoldingModel> holdings = businessStockHoldingMapper.selectStockNum(contractId, "", "");
        for (StockHoldingModel holdingModel : holdings) {
            stockCodes.add(holdingModel.getStockNo());
            quantitiesCanSell.put(holdingModel.getStockNo(), holdingModel.getCanSell());
        }
        List<QuotesTenModel> quotesTenModels = getQuotesTenModel(stockCodes.toArray(new String[stockCodes.size()]));
        //卖出所有可卖
        endEntrustAtStrong(contractId);//撤单所有
        List<Integer> quantities = new ArrayList<Integer>();
        List<String> stockNos = new ArrayList<String>();
        List<Double> prices = new ArrayList<Double>();
        for (QuotesTenModel quotesTenModel : quotesTenModels) {
            stockNos.add(quotesTenModel.getStockCode());
            prices.add((double) quotesTenModel.getCurPrice());
            quantities.add(quantitiesCanSell.get(quotesTenModel.getStockCode()));
        }
        clearRepository(stockNos, quantities, prices, contractId);
    }

    //获取风控合约
    public PageInfo<RiskContractModel> getRiskContract(RiskControlModel model) {
        PageHelper.startPage(model.getPageIndex(), model.getPageSize());
        List<RiskContractModel> contractModels = businessContractMapper.getRiskContract(model);
        for (RiskContractModel contractModel : contractModels) {
            if (null == contractModel.getDownRate() || null == contractModel.getWarnRate()) {
                continue;
            }
            double available = BusinessUtils.addMethod(contractModel.getCanUseMoney(), contractModel.getColdCash(), contractModel.getWorth());
            double sumBegin = (contractModel.getBorrowMoney() + contractModel.getPromisedMoney());
            double contractRisk = available / sumBegin;
            if (contractRisk <= contractModel.getDownRate()) {
                contractModel.setAbortStatus("触发");
            } else {
                contractModel.setAbortStatus("安全");
            }
            contractModel.setAbortLine(available - (sumBegin * contractModel.getDownRate()));
            contractModel.setWarningLine(available - (sumBegin * contractModel.getWarnRate()));
            contractModel.setWarnningStr(RudeStatus.getName(contractModel.getWarnningStatus()));
            contractModel.setContractStatus(BusinessStatus.getName(contractModel.getStatus()));
            contractModel.setProductName(distributService.getProductName(contractModel.getProductId()));
            contractModel.setLessDay(lessDay(contractModel.getExpiredTime()));
            if (null != contractModel.getWorth()) {
                contractModel.setHoldingRate(contractModel.getWorth() / available);
            }
        }
        PageInfo<RiskContractModel> pageInfo = new PageInfo<RiskContractModel>(contractModels);
        return pageInfo;
    }

    //分页获取危险股票列表
    public PageInfo<DangerousStockModel> getPageDangerousStock(String keyWord, Integer index, Integer size, Integer orderBy) throws BusinessException {
        Set<String> contractIds = JSONObject.parseObject(redisTemplate.opsForValue().get(controlContractKey), ContractJsonModel.class).getContractIds();
        /*if (0 == contractIds.size()){
            throw new BusinessException(ResultCode.SELECT_NULL_MSG);
        }*/
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("keyWord",keyWord);
        List<String> ids = null;
        if (0 != contractIds.size()){
            ids = new ArrayList<String>(contractIds);
        }
        params.put("list",ids );
        List<DangerousStockModel> dangerousStockModels = businessStockHoldingMapper.getDangerousStockModels(params);
        List<DangerousStockModel> holdingStockModels = businessStockHoldingMapper.getAllStockModles();
        Map<String, Double> sumWorth = new HashMap<String, Double>();
        Integer sumamounts = 0;
        Double summoneys = 0.0;
        for (DangerousStockModel holding : holdingStockModels) {   //计算全局占比
            sumamounts += holding.getHoldingAmount();
            summoneys += holding.getHoldingWorth();
            sumWorth.put(holding.getStockCode() + "sumWorth", holding.getHoldingWorth());
        }
        Map<String, Double> dangerousWorth = new HashMap<String, Double>();
        for (DangerousStockModel holding : holdingStockModels) {
            if (dangerousWorth.containsKey(holding.getStockCode()+"dangerousWorth")){
                dangerousWorth.put(holding.getStockCode()+"dangerousWorth",dangerousWorth.get(holding.getStockCode()+"dangerousWorth")+holding.getHoldingWorth());
            }else {
                dangerousWorth.put(holding.getStockCode()+"dangerousWorth",holding.getHoldingWorth());
            }
        }
        for (int i = 0; i < dangerousStockModels.size(); i++) {//排序
            String stockCode = dangerousStockModels.get(i).getStockCode();
            dangerousStockModels.get(i).setGlobalAmount(sumamounts);
            dangerousStockModels.get(i).setGlobalWorth(summoneys);
            dangerousStockModels.get(i).setDangerousRate(dangerousWorth.get(stockCode+"dangerousWorth")/sumWorth.get(stockCode+"sumWorth"));
            dangerousStockModels.get(i).setGlobalRate(sumWorth.get(stockCode+"sumWorth")/summoneys);
            if (TransactionSummaryStatus.DYNAMIC_BLACKLIST.getIndex() == dangerousStockModels.get(i).getStockStatus()){
                dangerousStockModels.get(i).setToBlack("no");
            }
        }
        List<DangerousStockModel> pageModels =  sortDangerousList(dangerousStockModels,orderBy);
        int i = (index-1)*size;
        if (i>pageModels.size()-1){
            throw new  BusinessException(ResultCode.PARAM_ERR_MSG);
        }
        int j = i+size>=pageModels.size()?pageModels.size():i+size;
        List<DangerousStockModel> page = new ArrayList<DangerousStockModel>();
        for (int z = i; z < j ;z++){
            page.add(pageModels.get(z));
        }
        PageInfo<DangerousStockModel> pageInfo = new PageInfo<DangerousStockModel>(page);
        pageInfo.setTotal(pageModels.size());
        pageInfo.setPageSize(size);
        pageInfo.setPageNum(index);
        pageInfo.setSize(size);
        pageInfo.setPages(index);
        return pageInfo;
    }

    //排序危险股票列表
    private List<DangerousStockModel> sortDangerousList(List<DangerousStockModel> models,Integer orderBy){
        switch (orderBy) {
            case 1:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = o1.getHoldingAmount() - o2.getHoldingAmount();
                        return i;
                    }
                });
                break;
            case 2:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o1.getHoldingWorth() - o2.getHoldingWorth());
                        return i;
                    }
                });
                break;
            case 3:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (o1.getGlobalAmount() - o2.getGlobalAmount());
                        return i;
                    }
                });
                break;
            case 4:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o1.getGlobalWorth() - o2.getGlobalWorth());
                        return i;
                    }
                });
                break;
            case 5:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o1.getDangerousRate() - o2.getDangerousRate());
                        return i;
                    }
                });
                break;
            case 6:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o1.getGlobalRate() - o2.getGlobalRate());
                        return i;
                    }
                });
                break;
            case -1:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = o2.getHoldingAmount() - o1.getHoldingAmount();
                        return i;
                    }
                });
                break;
            case -2:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o2.getHoldingWorth() - o1.getHoldingWorth());
                        return i;
                    }
                });
                break;
            case -3:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (o2.getGlobalAmount() - o1.getGlobalAmount());
                        return i;
                    }
                });
                break;
            case -4:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o2.getGlobalWorth() - o1.getGlobalWorth());
                        return i;
                    }
                });
                break;
            case -5:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o2.getDangerousRate() - o1.getDangerousRate());
                        return i;
                    }
                });
                break;
            case -6:
                Collections.sort(models, new Comparator<DangerousStockModel>() {
                    @Override
                    public int compare(DangerousStockModel o1, DangerousStockModel o2) {
                        int i = (int)(o2.getGlobalRate() - o1.getGlobalRate());
                        return i;
                    }
                });
                break;
        }
        return models;
    }

    //判断剩余多少天
    private Integer lessDay(long time){
        int days = (int) (time - new Date().getTime()) / (1000*3600*24);
        return days;
    }

    //获取高危合约清单
    public PageInfo<RiskContractModel> getDangerousContract(String keyWord,int index,int size,int orderBy,double warnRate1,double warnRate2) throws BusinessException{
        List<RiskContractModel> contractModels = businessContractMapper.getDangerousContract(keyWord);
        if (0 == contractModels.size()){
            throw new BusinessException(ResultCode.SELECT_NULL_MSG);
        }
        List<RiskContractModel> pageModels = new ArrayList<RiskContractModel>();
        for (RiskContractModel contractModel:contractModels){
            contractModel.setWarnSafeRate(contractModel.getRiskRate()-contractModel.getWarningLine());
            if (!(warnRate1<contractModel.getWarnSafeRate() && warnRate2>contractModel.getWarnSafeRate())){
                continue;
            }
            contractModel.setLessDay(lessDay(contractModel.getExpiredTime()));
            contractModel.setContractStatus(BusinessStatus.getName(contractModel.getStatus()));
            contractModel.setRiskRate(BusinessUtils.addMethod(contractModel.getCanUseMoney(),contractModel.getColdCash(),contractModel.getWorth())/BusinessUtils.addMethod(contractModel.getPromisedMoney(),contractModel.getBorrowMoney()));
            contractModel.setAbortSafeRate(contractModel.getRiskRate()-contractModel.getAbortLine());
            pageModels.add(contractModel);
        }
        pageModels = sortRiskContract(pageModels,orderBy);
        int i = (index-1)*size;
        if (i>pageModels.size()-1){
            throw new  BusinessException(ResultCode.PARAM_ERR_MSG);
        }
        int j = i+size>=pageModels.size()?pageModels.size():i+size;
        List<RiskContractModel> page = new ArrayList<RiskContractModel>();
        for (int z = i; z < j; z++){
            page.add(pageModels.get(z));
        }
        PageInfo<RiskContractModel> pageInfo = new PageInfo<RiskContractModel>(page);
        pageInfo.setPages(index);
        pageInfo.setSize(size);
        pageInfo.setPageNum(index);
        pageInfo.setPageSize(size);
        pageInfo.setTotal(pageModels.size());
        return pageInfo;
    }

    //高危合约清单排序
    private List<RiskContractModel> sortRiskContract(List<RiskContractModel> models,int orderBy){
        switch (orderBy){
            case 1:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getPromisedMoney() - o2.getPromisedMoney());
                        return i;
                    }
                });
                break;
            case 2:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getBorrowMoney() - o2.getBorrowMoney());
                        return i;
                    }
                });
                break;
            case 3:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getCanUseMoney() - o2.getCanUseMoney());
                        return i;
                    }
                });
                break;
            case 4:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getWorth() - o2.getWorth());
                        return i;
                    }
                });
                break;
            case 5:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getExpiredTime() - o2.getExpiredTime());
                        return i;
                    }
                });
                break;
            case 6:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getLessDay() - o2.getLessDay());
                        return i;
                    }
                });
                break;
            case 7:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getRiskRate() - o2.getRiskRate());
                        return i;
                    }
                });
                break;
            case 8:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getAbortLine() - o2.getAbortLine());
                        return i;
                    }
                });
                break;
            case 9:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o1.getAbortSafeRate() - o2.getAbortSafeRate());
                        return i;
                    }
                });
                break;
            case -1:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getPromisedMoney() - o1.getPromisedMoney());
                        return i;
                    }
                });
                break;
            case -2:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getBorrowMoney() - o1.getBorrowMoney());
                        return i;
                    }
                });
                break;
            case -3:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getCanUseMoney() - o1.getCanUseMoney());
                        return i;
                    }
                });
                break;
            case -4:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getWorth() - o1.getWorth());
                        return i;
                    }
                });
                break;
            case -5:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getExpiredTime() - o1.getExpiredTime());
                        return i;
                    }
                });
                break;
            case -6:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getLessDay() - o1.getLessDay());
                        return i;
                    }
                });
                break;
            case -7:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getRiskRate() - o1.getRiskRate());
                        return i;
                    }
                });
                break;
            case -8:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getAbortLine() - o1.getAbortLine());
                        return i;
                    }
                });
                break;
            case -9:
                Collections.sort(models, new Comparator<RiskContractModel>() {
                    @Override
                    public int compare(RiskContractModel o1, RiskContractModel o2) {
                        int i = (int)(o2.getAbortSafeRate() - o1.getAbortSafeRate());
                        return i;
                    }
                });
                break;
        }
        return models;
    }
}
