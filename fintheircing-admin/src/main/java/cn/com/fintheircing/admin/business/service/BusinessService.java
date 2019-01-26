package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.constant.EntrustStatus;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessStockEntrustMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessStockHoldingMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessTaxationMapper;
import cn.com.fintheircing.admin.business.dao.repository.*;
import cn.com.fintheircing.admin.business.entity.*;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferStockEntrustModel;
import cn.com.fintheircing.admin.business.utils.BusinessUtils;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.feign.IExchangeFeignService;
import cn.com.fintheircing.admin.common.feign.IStockPriceFeignService;
import cn.com.fintheircing.admin.common.feign.model.*;
import cn.com.fintheircing.admin.common.model.MotherAccount;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.common.utils.MappingModel2EntityConverter;
import cn.com.fintheircing.admin.system.service.SystemService;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private IExchangeFeignService exchangeFeignService;
    @Resource
    private IBusinessTaxationRepository businessTaxationRepository;
    @Resource
    private IBusinessTaxationRelationRepository businessTaxationRelationRepository;
    @Resource
    private IBusinessTaxationMapper businessTaxationMapper;
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

    @Value("${custom.risk.maxBuyOne}")
    private Double maxBuyOne;
    @Value("${custom.risk.venturEditionMax}")
    private Double venturEditionMax;
    @Value("${custom.risk.holdOverFiveAvg}")
    private Double holdOverFiveAvg;
    @Value("${custom.risk.holdOverCurrency}")
    private Double holdOverCurrency;
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
/*    @Value("${custom.report.isReport}")
    private String report;*/


    //是否可买
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

    //测试，可删
    @Transactional(rollbackFor = Exception.class)
    public void testRollBack() throws BusinessException{
        int i = businessContractRepository.
                updateAvailableMoney("40289f1a686084460168608694110000",100.0,1);
        try {
            i=1/0;
        } catch (Exception e) {
            throw new BusinessException("");
        }
    }

    //创建合约
    @Transactional(rollbackFor = Exception.class)
    public void saveContract(ContractModel model){
        //新建合约
        BusinessContract contract = MappingModel2EntityConverter.CONVERTERFORCONTRACT(model);
        contract.setContractStatus(BusinessStatus.CONTRACT_NEW.getNum());  //给予新建合约状态
        contract.setRudeStatus(BusinessStatus.BUSINESS_NOT.getNum());       //给予未平仓状态
        if (ProductStatus.MONTHS.getIndex() == contract.getChoseWay()){
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            contract.setExpiredDay(sdf.format(new Date()));
        }
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
    }

    public List<ContractModel> getCurrentContract(String userId){
        List<ContractModel> contractModels = businessContractMapper.selectCurrentContract(userId);
        contractModels.forEach(c->{
            c.setChoseStr(ProductStatus.getName(c.getChoseWay()));
        });
        return contractModels;
    }



    //交易过程中冻结资金
    //买入流程
    @Transactional(rollbackFor = Exception.class,timeout = 1000000)
    public void costColdContract(StockEntrustModel model) throws BusinessException{
        Double payMoney = BusinessUtils.multiplicationMethod(model.getAmount().doubleValue(),model.getPrice());
        String contractId = model.getContractId();
        /*List<QuotesTenModel> quotesTenModels = getQuotesTenModel(model.getStockNum());//根据stockNum获取当前股票实时数据
        if (!(quotesTenModels.size()>0)){
            throw new BusinessException(ResultCode.STOCK_NULL_ERR);
        }
        QuotesTenModel quotesTenModel = quotesTenModels.get(0);*/
        List<StockHoldingModel> stockHoldingModels = businessStockHoldingMapper.selectStockNum(model.getContractId(),model.getStockNum());
        StockHoldingModel stockHoldingModel = new StockHoldingModel();
        if (stockHoldingModels.size()>0){
            stockHoldingModel = stockHoldingModels.get(0);
        }
        ContractModel contract = businessContractMapper.selectContract(contractId);//获取相关合约
        TransactionSummary transactionSummary = transactionSummaryRepository.findByDeleteFlagAndStockNum("0",model.getStockNum());
        if (transactionSummary==null){
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
        //BusinessUtils.throughRisk(stockHoldingModel,model,contract,quotesTenModel,chuangRegex);//验证是否能交易，不能直接抛出异常
        /**
         * 需要计算税费
         */
        Double taxation = 0.0;
        List<BusinessTaxation> businessTaxations = businessTaxationRepository.findByDeleteFlagAndBsuinessTo("0",BusinessTaxation.BUSINESS_BUY);
        for (BusinessTaxation businessTaxation:businessTaxations){
            taxation = taxation + businessTaxation.getTaxRate();
        }
        taxation = taxation * payMoney;
        Double businessCash = BusinessUtils.addMethod(contract.getBusinessRate(),contract.getBuyRate())*payMoney;
        Double worth = contract.getWorth();
        Double coldCash = contract.getColdCash();
        Double canUseMoney = contract.getCanUseMoney();
        Double totalMoney = BusinessUtils.addMethod(canUseMoney,coldCash,worth);//总资产
        Double warningMoney =BusinessUtils.addMethod(contract.getBorrowMoney(),contract.getPromisedMoney())*contract.getWarningLine();//警戒线
        Double coldMoney = taxation + payMoney + businessCash;
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
        stockEntrust.setBusinessPrice(model.getPrice());
        stockEntrust.setBusinessAmount(model.getAmount());
        stockEntrust.setUserId(model.getUserId());
        stockEntrust.setBusinessMoney(businessCash);
        stockEntrust.setTaxationMoney(taxation);
        stockEntrust.setStockId(transactionSummary.getId());
        stockEntrust.setCreatedTime(new Date());
        stockEntrust.setContractId(contractId);
        stockEntrust.setCreatorId(model.getUserId());
        stockEntrust.setCancelOrder(BusinessStockEntrust.STOCK_ORDER);
        stockEntrust.setEntrustStatus(EntrustStatus.ENTRUST_NOT_DEAL.getIndex());
        stockEntrust.setColdMoney(payMoney);
        stockEntrust = businessStockEntrustRepository.save(stockEntrust);

        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setCreatorId(stockEntrust.getUserId());
        controlContract.setCreatedTime(new Date());
        controlContract.setLessMoney(BusinessUtils.addMethod(contract.getCanUseMoney(),contract.getColdCash()));
        controlContract.setCostMoney( stockEntrust.getColdMoney());
        controlContract.setBusinessMoney(stockEntrust.getBusinessMoney());
        controlContract.setTaxationMoney(stockEntrust.getTaxationMoney());
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setContractId(contract.getId());
        controlContract.setControlType(ControlCode.CONTROL_ENTRUSTBUYSTOCK.getIndex());
        businessControlContractRepository.save(controlContract);

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
        if (!dealStockAuto(stockEntrust,model.getStockNum(),contractModel)){
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
    }

    /*@Async*/
    //失败回滚
    //自动买入
    @Transactional(rollbackFor = Exception.class)
    public Boolean dealStockAuto(BusinessStockEntrust entrust,String stockNo,ContractModel contractModel) throws BusinessException{
        Boolean auto = Boolean.parseBoolean(redisTemplate.opsForValue().get(autoBuy));
        if (auto){
            String account = getMotherAccount(contractModel.getId(),stockNo);
            if (StringUtils.isEmpty(account)){
                List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
                if (motherAccounts.size()>0) {
                    Random random = new Random(BusinessUtils.fromStringWhitoutHyphens(entrust.getUserId()).getLeastSignificantBits());
                    account = motherAccounts.get(random.nextInt(motherAccounts.size())).getAccountNo();
                }else {
                    throw new BusinessException(ResultCode.MOTHER_NULL_ERR);
                }
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
            buyOrderRequestModel.setPrice(Float.valueOf(entrust.getBusinessPrice().toString()));
            buyOrderRequestModel.setPszStockCode(stockNo);
            buyOrderRequestModel.setQuantity(entrust.getBusinessAmount());
            ResponseModel<String> responseModel = exchangeFeignService.sendBuyOrder(buyOrderRequestModel);
            if (responseModel==null){
                throw new BusinessException(ResultCode.STOCK_MSG_ERR);
            }
            //responseModel.setCode(ResultCode.SUCESS);//test
            if (ResultCode.SUCESS.equals(responseModel.getCode())){
                    entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
                    entrust.setDealNo(responseModel.getData());
                    entrust.setMontherAccount(account);
                    businessStockEntrustRepository.save(entrust);
           //真实买入时才扣除冻结
            }else {
                return false;
            }
        }
        return true;
    }


    //手动买入
    @Transactional(rollbackFor = Exception.class)
    public void dealStockHand(UserTokenInfo userInfo,StockEntrustModel model) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0",model.getId());
        if (entrust==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        /*ContractModel contract = businessContractMapper.selectContract(model.getContractId());//获取相关合约
        if (contract==null){
            throw new BusinessException(ResultCode.CONTRACT_NULL_FIND);
        }*/
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
    public Map<String,String> checkEntrust(UserTokenInfo userInfo,StockEntrustModel model) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0",model.getId());
        if (entrust==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        if (!EntrustStatus.ENTRUST_NOT_DEAL.getIndex().
                equals(entrust.getEntrustStatus())){
            throw new BusinessException(ResultCode.UPDATE_ALREAD_MSG);
        }
        entrust.setUpdateUserName(userInfo.getUserName());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdatedTime(new Date());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_WAIT_DEAL.getIndex());
        businessStockEntrustRepository.save(entrust);
        String account = getMotherAccount(model.getContractId(),model.getStockNum());
        if (StringUtils.isEmpty(account)){
            List<MotherAccount> motherAccounts = motherAccountQueryService.getAllAviable();
            if (motherAccounts.size()>0) {
                Random random = new Random(BusinessUtils.fromStringWhitoutHyphens(model.getUserId()).getLeastSignificantBits());
                account = motherAccounts.get(random.nextInt(motherAccounts.size())).getAccountNo();
            }
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("motherAccount",account);
        return map;
    }

    //查询是否已购买相同的股票
    private String getMotherAccount(String contractId,String stockNo){
        String account = "";
        List<StockHoldingModel> holdings = businessStockHoldingMapper.selectStockNum(contractId,stockNo);
        if (holdings.size()>0) {
            StockHoldingModel holding = holdings.get(0);
            if (holding != null) {
                account = holding.getMotherAccount();
            }
        }
        return account;
    }


    //当前持有股
    public List<StockHoldingModel> getCurrentHolding(StockHoldingModel model){
        if (businessContractRepository.countByDeleteFlagAndUuidAndUserId
                ("0",model.getContractId(),model.getUserId())>0){
            return businessStockHoldingMapper.selectStockNum(model.getContractId(),model.getStockNo());
        }
        return null;
    }

    /*@Transactional(rollbackFor = Exception.class)
    public  void savePayControl(String contractId,Integer cotrolType,
                                  Double pay,Double available) throws BusinessException{
        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setCostMoney(pay);
        controlContract.setLessMoney(available);
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setContractId(contractId);
        controlContract.setControlType(cotrolType);
        controlContract = businessControlContractRepository.save(controlContract);
        if (StringUtils.isEmpty(controlContract.getUuid())){
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
    }*/


    //卖出申报流程
    @Transactional(rollbackFor = Exception.class)
    public void sellHoldStock(StockHoldingModel model) throws BusinessException{
        TransactionSummary summary = transactionSummaryRepository.findByDeleteFlagAndStockNum("0",model.getStockNo());
        if (null==summary){
            throw new BusinessException(ResultCode.STOCK_ENTRUST_DANGER);
        }
        BusinessStockHolding holding = businessStockHoldingRepository.findBusinessStockHoldingByDeleteFlagAndStockIdAndContractId("0",summary.getId(),model.getContractId());
        if (null==holding){
            throw new BusinessException(ResultCode.STOCK_HOLDING_ERR);
        }
        Integer amount = holding.getAmount()==null?0:holding.getAmount();
        Integer canSell = holding.getCanSell()==null?0:holding.getCanSell();
        Integer cold = holding.getColdAmount()==null?0:holding.getColdAmount();
        if (model.getAmount()>canSell){
            logger.error("userID:"+model.getUserId()+";msg:"+ResultCode.STOCK_SELL_LESS_ERR);
            throw new BusinessException(ResultCode.STOCK_SELL_LESS_ERR);
        }
        holding.setAmount(amount-model.getAmount());
        holding.setCanSell(canSell-model.getAmount());
        holding.setColdAmount(cold+model.getAmount());
        try {
            businessStockHoldingRepository.save(holding);
        } catch (Exception e) {
            logger.error("userId:"+model.getUserId()+",msg:持仓修改失败");
            throw new BusinessException(e.getMessage());
        }
        BusinessStockEntrust entrust = new BusinessStockEntrust();
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_NOT_DEAL.getIndex());
        entrust.setMontherAccount(holding.getMotherAccount());
        entrust.setStockId(holding.getStockId());
        entrust.setBusinessAmount(model.getAmount());
        entrust.setBusinessPrice(model.getCostPrice());
        entrust.setUserId(model.getUserId());
        entrust.setContractId(model.getContractId());
        entrust.setBusinessTo(BusinessStockEntrust.STOCK_SELL);
        entrust.setHoldingId(holding.getUuid());
        entrust.setCancelOrder(BusinessStockEntrust.STOCK_ORDER);
        entrust.setCreatedTime(new Date());
        entrust.setCreatorId(model.getUserId());

        try {
            businessStockEntrustRepository.save(entrust);
        } catch (Exception e) {
            logger.error("userId:"+model.getUserId()+",msg:保存出售股票委托失败");
            throw new BusinessException(e.getMessage());
        }

        ContractModel contract = businessContractMapper.selectContract(entrust.getContractId());

        BusinessControlContract controlContract = new BusinessControlContract();
        controlContract.setCreatorId(entrust.getUserId());
        controlContract.setCreatedTime(new Date());
        controlContract.setLessMoney(BusinessUtils.addMethod(contract.getCanUseMoney(),contract.getColdCash()));
        controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
        controlContract.setContractId(contract.getId());
        controlContract.setControlType(ControlCode.CONTROL_ENTRUSTSELLSTOCK.getIndex());
        businessControlContractRepository.save(controlContract);

        if (!dealAutoSell(entrust,model.getStockNo())){
            logger.error("userId:"+model.getUserId()+",msg:卖出股票委托失败");
            throw new BusinessException(ResultCode.STOCK_BUSINESS_ERR);
        }
    }

    //自动卖出
    @Transactional(rollbackFor = Exception.class)
    public Boolean dealAutoSell(BusinessStockEntrust entrust,String stockNo) {
        Boolean auto = Boolean.parseBoolean(redisTemplate.opsForValue().get(autoBuy));
        if (auto){
            String account = entrust.getMontherAccount();
            String exchangeId = "";
            if (CommonUtil.regexString(shenRegex,stockNo)){
                exchangeId = "0";
            }else if (CommonUtil.regexString(shangRegex,stockNo)){
                exchangeId = "1";
            }
            SellOrderRequestModel sell = new SellOrderRequestModel();
            sell.setExchangeId(exchangeId);
            sell.setMotnerAccount(account);
            sell.setPrice(Float.valueOf(entrust.getBusinessPrice().toString()));
            sell.setPszStockCode(stockNo);
            sell.setQuantity(entrust.getBusinessAmount());
            ResponseModel<String> response = exchangeFeignService.sendSellOrder(sell);
            if (response==null){
                return false;
            }
            //response.setCode(ResultCode.SUCESS);//测试
            if (ResultCode.SUCESS.equals(response.getCode())){
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
                entrust.setDealNo(response.getData());
            }else {
                return false;
            }
        }
        return true;
    }

    //手动卖出申报
    public void dealSellHand(UserTokenInfo userInfo,StockEntrustModel model) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0",model.getId());
        if (entrust==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        entrust.setDealNo(model.getDealNo());
        entrust.setEntrustStatus(EntrustStatus.ENTRUST_REPORT.getIndex());
        entrust.setUpdatedTime(new Date());
        entrust.setUpdateUserId(userInfo.getUuid());
        entrust.setUpdateUserName(userInfo.getUserName());
        businessStockEntrustRepository.save(entrust);
    }


    /*private Boolean searchIsReport(String motherAccount,String dealNo){
        ResponseModel<List<TodayOrder>> response = exchangeFeignService.getTodayOrderList(motherAccount);
        List<TodayOrder> orders = response.getData();
        for (TodayOrder order:orders){
            if (order.getOrderNumber().equals(dealNo)){
                if (report.equals(order.getStatus())){
                    return true;
                }
            }
        }
        return false;
    }*/

    //分页获取申报
    public TranferStockEntrustModel getPageEntrust(StockEntrustModel model){
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<StockEntrustModel> models = businessStockEntrustMapper.selectPageEntrusts(model);
        PageInfo<StockEntrustModel> page = new PageInfo<StockEntrustModel>(models);
        TranferStockEntrustModel tranferStockEntrustModel = new TranferStockEntrustModel();
        tranferStockEntrustModel.setPageInfo(page);
        tranferStockEntrustModel.setAutoBuy(redisTemplate.opsForValue().get(autoBuy));
        return tranferStockEntrustModel;
    }

    //未完成订单
    public List<StockEntrustModel> getUnFinishedEntrust(ContractModel model){
        return businessStockEntrustMapper.selectCancelEntrust(model);
    }


    //撤单流程
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(StockEntrustModel model) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.
                findByDeleteFlagAndUuidAndUserId("0",model.getId(),model.getUserId());
        if (entrust==null){
            throw new BusinessException(ResultCode.STOCK_NULL_FIND);
        }
        if (EntrustStatus.ENTRUST_FINSISH.getIndex()==entrust.getEntrustStatus()||
                EntrustStatus.ENTRUST_BACK.getIndex()==entrust.getEntrustStatus()){
            throw new BusinessException(ResultCode.ENTRUST_IS_DEAL);
        }
        if (EntrustStatus.ENTRUST_WAIT_DEAL.getIndex()==entrust.getEntrustStatus()||
                EntrustStatus.ENTRUST_BACK_WAIT.getIndex()==entrust.getEntrustStatus()||
                EntrustStatus.ENTRUST_BACK_ING.getIndex()==entrust.getEntrustStatus()){
            throw new BusinessException(ResultCode.ENTRUST_WAIT_DEAL);
        }
        if (EntrustStatus.ENTRUST_NOT_DEAL.getIndex()==entrust.getEntrustStatus()){
            if (BusinessStockEntrust.STOCK_SELL.equals(entrust.getBusinessTo())){
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

                BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlagAndUuid("0",entrust.getHoldingId());
                holding.setColdAmount(BusinessUtils.minusIntMethod(holding.getColdAmount(),entrust.getBusinessAmount()));
                holding.setAmount(BusinessUtils.addIntMethod(entrust.getBusinessAmount(),holding.getAmount()));
                holding.setCanSell(BusinessUtils.addIntMethod(entrust.getBusinessAmount(),holding.getCanSell()));
                businessStockHoldingRepository.save(holding);
                businessStockEntrustRepository.save(entrust);
            }else if (BusinessStockEntrust.STOCK_BUY.equals(entrust.getBusinessTo())){  //撤单买入
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
                entrust.setUpdatedTime(new Date());//返回冻结金
                BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
                contract.setColdMoney(BusinessUtils.minusMethod(contract.getColdMoney(), entrust.getColdMoney(),entrust.getBusinessMoney(),entrust.getTaxationMoney()));
                contract.setAvailableMoney(BusinessUtils.addMethod(contract.getAvailableMoney(), entrust.getColdMoney(),entrust.getTaxationMoney(),entrust.getBusinessMoney()));
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
        }else {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK_WAIT.getIndex());
            entrust.setUpdateUserId(model.getUserId());
            businessStockEntrustRepository.save(entrust);
            if (!autoDealBack(entrust, model.getStockNum())) {
                throw new BusinessException(ResultCode.ENTRUST_BACK_ERR);
            }
            ContractModel contract = businessContractMapper.selectContract(entrust.getContractId());
            BusinessControlContract controlContract = new BusinessControlContract();
            controlContract.setCreatorId(entrust.getUserId());
            controlContract.setCreatedTime(new Date());
            controlContract.setLessMoney(BusinessUtils.addMethod(contract.getCanUseMoney(),contract.getColdCash()));
            controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlContract.setContractId(contract.getId());
            controlContract.setControlType(ControlCode.CONTROL_ENTRUSTBACKSTOCK.getIndex());
            businessControlContractRepository.save(controlContract);
        }

    }


    //自动撤单申报
    @Transactional(rollbackFor = Exception.class)
    public Boolean autoDealBack(BusinessStockEntrust entrust,String stockNo) throws BusinessException{
        Boolean auto = Boolean.parseBoolean( redisTemplate.opsForValue().get(autoBuy));
        if (auto){
            String account = entrust.getMontherAccount();
            String dealNo = entrust.getDealNo();
            if (!searchIsCancel(account,dealNo)){
                throw new BusinessException(ResultCode.CANCEL_NULL_ERR);
            }
            String exchangeId = "";
            if (CommonUtil.regexString(shenRegex,stockNo)){
                exchangeId = "0";
            }else if (CommonUtil.regexString(shangRegex,stockNo)){
                exchangeId = "1";
            }
            CancleOrderRequestModel cancle = new CancleOrderRequestModel();
            cancle.setExchangeId(exchangeId);
            cancle.setMotnerAccount(account);
            cancle.setOrderNum(dealNo);
            cancle.setPszStockCode(stockNo);
            ResponseModel<String> response = exchangeFeignService.cancelOrder(cancle);
            if (null == response){
                throw new BusinessException(ResultCode.STOCK_MSG_ERR);
            }
            response.setCode(ResultCode.SUCESS);//test
            if (ResultCode.SUCESS.equals(response.getCode())){
                entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK_ING.getIndex());
                entrust.setUpdatedTime(new Date());
                entrust.setUpdateUserId(entrust.getUserId());
                entrust.setCancelOrder(BusinessStockEntrust.STOCK_CANCEL_ORDER);
                entrust.setCancelNo(response.getData());
                businessStockEntrustRepository.save(entrust);
            }else {
                return false;
            }
        }
        return true;
    }

    //手动撤单申报
    @Transactional(rollbackFor = Exception.class)
    public void dealBackHand(UserTokenInfo userInfo,StockEntrustModel model) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndUuid("0",model.getId());
        if (entrust==null){
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
    private Boolean searchIsCancel(String account,String dealNo){
        ResponseModel<List<CanCancleOrder>> responseModel = exchangeFeignService.getCanCancleOrderList(account);
        List<CanCancleOrder> canCancleOrders = responseModel.getData();
        for (CanCancleOrder canCancleOrder:canCancleOrders){
            if (canCancleOrder.getOrderNumber().equals(dealNo)){
                return true;
            }
        }
        return false;
    }


    //修改自动手动
    public void setAutoSystem(UserTokenInfo userInfo){
        Boolean auto = !Boolean.valueOf(redisTemplate.opsForValue().get(autoBuy));
        redisTemplate.opsForValue().set(autoBuy,auto.toString());
    }


    //最大买入量
    public StockHoldingModel getMaxBuyAmount(StockHoldingModel model) {
        ContractModel contractModel = businessContractMapper.selectContract(model.getContractId());
        if (null == contractModel){
            return new StockHoldingModel();
        }
        List<StockHoldingModel> stockHoldingModels = businessStockHoldingMapper.selectStockNum(model.getContractId(),model.getStockNo());
        Pattern pattern = Pattern.compile(chuangRegex);
        Matcher matcher = pattern.matcher(model.getStockNo());
        Double rate = 0.0;
        if (matcher.find()){
            rate = contractModel.getVenturEditionMaxAccount();
        }else{
            rate = contractModel.getCustomerMaxAccount();
        }
        Double C0 = 0.0;
        if (stockHoldingModels.size()>0){
            StockHoldingModel stockHoldingModel = stockHoldingModels.get(0);
            C0 = stockHoldingModel.getCurrentWorth();
        }
        Double A = BusinessUtils.addMethod(contractModel.getColdCash(),contractModel.getWorth(),contractModel.getCanUseMoney());
        Double C1 = 0.0;
        try {
            C1 = BusinessUtils.minusMethod(A*rate,C0);
        } catch (BusinessException e) {
            logger.error("计算报错");
        }
        model.setCurrentWorth(C1);
        return model;
    }


    //撤单买入
    @Transactional(rollbackFor = Exception.class)
    public void updateColdMoneyAndSaveEntrust(TodayOrder order,String account) throws BusinessException {
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndMontherAccountAndDealNo("0",account,order.getOrderNumber());
        if (null != entrust) {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
            entrust.setUpdatedTime(new Date());//返回冻结金
            BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
            contract.setColdMoney(BusinessUtils.minusMethod(contract.getColdMoney(), entrust.getColdMoney(),entrust.getBusinessMoney(),entrust.getTaxationMoney()));
            contract.setAvailableMoney(BusinessUtils.addMethod(contract.getAvailableMoney(), entrust.getColdMoney(),entrust.getTaxationMoney(),entrust.getBusinessMoney()));
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
        }else {
            logger.error("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
        }
    }


    //撤单卖出
    @Transactional(rollbackFor = Exception.class)
    public void updateHoldingAndSaveEntrust(TodayOrder order,String account) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository.findByDeleteFlagAndMontherAccountAndDealNo("0",account,order.getOrderNumber());
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

            BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlagAndUuid("0",entrust.getHoldingId());
            holding.setColdAmount(BusinessUtils.minusIntMethod(holding.getColdAmount(),(int)order.getOrderQuantity()));
            holding.setAmount(BusinessUtils.addIntMethod((int)order.getOrderQuantity(),holding.getAmount()));
            holding.setCanSell(BusinessUtils.addIntMethod((int)order.getOrderQuantity(),holding.getCanSell()));
            businessStockHoldingRepository.save(holding);
            businessStockEntrustRepository.save(entrust);
        }else {
            logger.error("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
        }
    }

   /* public void saveEntrust(BusinessStockEntrust entrust){
        businessStockEntrustRepository.save(entrust);
    }*/

   //定时修改买入成交
   @Transactional(rollbackFor = Exception.class)
    public void dealBuyMethod(TodayAcceptOrder order,String account) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository
                .findByDeleteFlagAndMontherAccountAndDealNo("0",account,order.getOrderNumber());
        if (null!=entrust) {
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_FINSISH.getIndex());
            entrust.setUpdatedTime(new Date());
            entrust.setDealNum(BusinessUtils.addIntMethod((int) order.getActQuantity(),entrust.getDealNum()));
            entrust.setDealPrice(BusinessUtils.avgMethod((double) order.getActPrice(),entrust.getDealPrice()));
            entrust.setDealTime(new Date());
            Double rate = 1 - (entrust.getDealNum() * entrust.getDealPrice()) / (entrust.getBusinessAmount() * entrust.getBusinessPrice())  ;
            BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
            contract.setColdMoney(BusinessUtils.minusMethod(contract.getColdMoney(), BusinessUtils.multiplicationMethod(entrust.getColdMoney(),(1-rate))  ,BusinessUtils.multiplicationMethod(entrust.getBusinessMoney(),(1-rate)),BusinessUtils.multiplicationMethod(entrust.getTaxationMoney(),(1-rate))));
            contract.setUpdatedTime(new Date());


            BusinessStockHolding holding = businessStockHoldingRepository
                    .findBusinessStockHoldingByDeleteFlagAndStockIdAndContractId("0", entrust.getStockId(), entrust.getContractId());
            if (holding==null){
                holding = new BusinessStockHolding();
                holding.setCreatedTime(new Date());
                holding.setCreatorId(entrust.getUserId());
                holding.setMotherAccount(entrust.getMontherAccount());
                holding.setContractId(entrust.getContractId());
                holding.setStockId(entrust.getStockId());
            }
            holding.setCostPrice(BusinessUtils.avgMethod(holding.getCostPrice(),entrust.getDealPrice()));
            holding.setAmount(BusinessUtils.addIntMethod(holding.getAmount(),entrust.getDealNum()));
            holding.setUpdatedTime(new Date());
            holding.setUpdateUserId(entrust.getUserId());
            //盈亏暂不算，等调度股票价钱时算
            holding = businessStockHoldingRepository.save(holding);

            BusinessControlContract controlContract = new BusinessControlContract();
            controlContract.setCreatorId(entrust.getUserId());
            controlContract.setCreatedTime(new Date());
            controlContract.setLessMoney(BusinessUtils.addMethod(contract.getAvailableMoney(),contract.getColdMoney()));
            controlContract.setAddMoney( entrust.getColdMoney() * rate);
            controlContract.setBusinessMoney(entrust.getBusinessMoney()*rate);
            controlContract.setTaxationMoney(entrust.getTaxationMoney()*rate);
            controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
            controlContract.setContractId(contract.getUuid());
            controlContract.setControlType(ControlCode.CONTROL_BUYSTOCK.getIndex());
            businessControlContractRepository.save(controlContract);

            entrust.setHoldingId(holding.getUuid());
            businessContractRepository.save(contract);
            businessStockEntrustRepository.save(entrust);
        }else {
            logger.error("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
        }
    }

    //定时修改卖出成交
    @Transactional(rollbackFor = Exception.class)
    public void dealSellMethod(TodayAcceptOrder order,String account) throws BusinessException{
        BusinessStockEntrust entrust = businessStockEntrustRepository
                .findByDeleteFlagAndMontherAccountAndDealNo("0",account,order.getOrderNumber());
        if (null!=entrust){
            entrust.setEntrustStatus(EntrustStatus.ENTRUST_FINSISH.getIndex());
            entrust.setDealPrice(BusinessUtils.avgMethod(entrust.getDealPrice(),(double)order.getActPrice()));
            entrust.setDealNum(BusinessUtils.addIntMethod(entrust.getDealNum(),(int)order.getActQuantity()));
            entrust.setDealTime(new Date());
            entrust.setUpdatedTime(new Date());
            businessStockEntrustRepository.save(entrust);
            Double taxation = 0.0;
            List<BusinessTaxation> businessTaxations = businessTaxationRepository.findByDeleteFlagAndBsuinessTo("0",BusinessTaxation.BUSINESS_SELL);
            for (BusinessTaxation businessTaxation:businessTaxations){
                taxation = taxation + businessTaxation.getTaxRate();
            }
            Double gainMoney = (double) (order.getActPrice()*order.getActQuantity());
            ContractModel contractModel = businessContractMapper.selectContract(entrust.getContractId());
            Double businessMoney = contractModel.getBusinessRate() * gainMoney;
            taxation = taxation * gainMoney;
            gainMoney = BusinessUtils.minusMethod(gainMoney,businessMoney,taxation);
            BusinessContract contract = businessContractRepository.findByUuid(entrust.getContractId());
            contract.setAvailableMoney(contract.getAvailableMoney()+gainMoney);
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
            controlContract.setLessMoney(BusinessUtils.addMethod(contract.getAvailableMoney(),contract.getColdMoney()));
            businessControlContractRepository.save(controlContract);

            BusinessStockHolding holding = businessStockHoldingRepository.findByDeleteFlagAndUuid("0",entrust.getHoldingId());
            holding.setColdAmount(BusinessUtils.minusIntMethod(holding.getColdAmount(),(int)order.getActQuantity()));
            holding.setUpdatedTime(new Date());
            businessStockHoldingRepository.save(holding);
        }else {
            logger.error("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
            throw new BusinessException("您有一笔未登记交易记录,委托单号："+order.getOrderNumber());
        }
    }

    //排队访问股价接口
    private List<QuotesTenModel> getQuotesTenModel(String... stockNo){
       List<String[]> strings = BusinessUtils.getListStringArray(stockNo,50);
       List<QuotesTenModel> quotesTenModels = new ArrayList<QuotesTenModel>();
       for (int j =0;j<strings.size();j++) {
           List<String> markets = new ArrayList<String>();
           List<String> stockCodes = new ArrayList<String>();
           String exchangeId = "";
           for (int i = 0; i < stockNo.length; i++) {
               if (CommonUtil.regexString(shenRegex, stockNo[i])) {
                   exchangeId = "0";
               } else if (CommonUtil.regexString(shangRegex, stockNo[i])) {
                   exchangeId = "1";
               }
               markets.add(exchangeId);
               stockCodes.add(stockNo[i]);
           }
           GetQuotesTenListRequestModel requestModel = new GetQuotesTenListRequestModel();
           requestModel.setMarkets(markets);
           requestModel.setMarkets(stockCodes);
           ResponseModel<List<QuotesTenModel>> response = stockPriceFeignService.getQuotesTenList(requestModel);
           if (ResultCode.SUCESS.equals(response.getCode())) {
               for (int z = 0;z<response.getData().size();z++){
                   quotesTenModels.add(response.getData().get(z));
               }
           }
       }
       return quotesTenModels;
    }


    //每日获取买入量
    public void getFiveDayMaxAmount(){
       //判断是否是假期，是就不更新
        long newTime = new Date().getTime();
        boolean flag = systemService.isInHoliday(newTime);
        //否更新数据
        if (!flag){
            //获取所有的在售股票代码
            List<TransactionModel> transactionModels = itemService.findAll(new TransactionModel());
            List<String> stockCodes = new ArrayList<>();
            transactionModels.forEach(transactionModel->{
                stockCodes.add(transactionModel.getStockNum());
            });
            List<QuotesTenModel> quotesTenModels = getQuotesTenModel((String[]) stockCodes.toArray());
            itemService.oneDayUpdateStock(quotesTenModels);//修改每日股票总量
        }
    }


    //每日清算冻结及可卖
    public void everyDayClear(){
       List<BusinessContract> contracts = businessContractRepository.findByDeleteFlag("0");
       for (BusinessContract contract:contracts){
           Double coldMoney = contract.getColdMoney()==null?0.0:contract.getColdMoney();
           contract.setAvailableMoney(BusinessUtils.addMethod(contract.getColdMoney(),contract.getAvailableMoney()));
           contract.setColdMoney(0.0);
           List<BusinessStockEntrust> stockEntrusts = businessStockEntrustRepository.findByDeleteFlagAndContractId("0",contract.getUuid());
           for (BusinessStockEntrust stockEntrust:stockEntrusts){
               stockEntrust.setEntrustStatus(EntrustStatus.ENTRUST_BACK.getIndex());
           }
           List<BusinessStockHolding> holdings = businessStockHoldingRepository.findByDeleteFlagAndContractId("0",contract.getUuid());
           for (BusinessStockHolding holding:holdings){
               holding.setAmount(BusinessUtils.addIntMethod(holding.getColdAmount(),holding.getAmount()));
               holding.setCanSell(holding.getAmount());
               holding.setColdAmount(0);
           }
           businessStockEntrustRepository.saveAll(stockEntrusts);
           businessStockHoldingRepository.saveAll(holdings);
           if (coldMoney!=0) {
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


    public ContractModel getBusinessInfo(UserTokenInfo userInfo){
        List<ContractModel> contractModels = businessContractMapper.selectCurrentContract(userInfo.getUuid());
        ContractModel contract = new ContractModel();
        for (ContractModel model:contractModels){
            contract.setWorth(BusinessUtils.addMethod(contract.getWorth(),model.getWorth()));
            contract.setCanUseMoney(BusinessUtils.addMethod(contract.getCanUseMoney(),model.getCanUseMoney()));
        }
       return contract;
    }
}
