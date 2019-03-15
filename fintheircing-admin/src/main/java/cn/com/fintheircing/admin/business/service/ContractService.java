package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractControlMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IStockEquityRecordMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockHoldingRepository;
import cn.com.fintheircing.admin.business.dao.repository.IContactRecodeRepository;
import cn.com.fintheircing.admin.business.dao.repository.IStockEquityRecordRepository;
import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.entity.BusinessStockHolding;
import cn.com.fintheircing.admin.business.entity.record.ContactRecode;
import cn.com.fintheircing.admin.business.entity.record.StockEquityRecord;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.record.ContractEquityModel;
import cn.com.fintheircing.admin.business.model.record.StockEquityModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.risk.dao.repository.IBusinessContractRiskRepository;
import cn.com.fintheircing.admin.risk.entity.BusinessContractRisk;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ContractService {
    @Resource
    private IBusinessContractControlMapper iBusinessContractControlMapper;
    @Resource
    private IContactRecodeRepository iContactRecodeRepository;
    @Resource
    private IStockEquityRecordRepository iStockEquityRecordRepository;
    @Resource
    private IStockEquityRecordMapper iStockEquityRecordMapper;
    @Resource
    private IBusinessContractRiskRepository businessContractRiskRepository;
    @Resource
    private BusinessService businessService;
    @Resource
    private IBusinessContractRepository businessContractRepository;
    @Resource
    private ICustomerFeignService customerFeignService;
    @Resource
    private ItemService itemService;
    @Resource
    private IBusinessStockHoldingRepository businessStockHoldingRepository;

    @Value("${custom.pattern.entrustPattern}")
    private String entrustPattern;

    public boolean ContactDetails(UserTokenInfo userInfo, ContractControlModel model) throws BusinessException {

        ContactRecode c = new ContactRecode();
        c.setApplyType("调整息费");
        c.setContactId(model.getBusinessContractId());
        c.setSubmitterId(userInfo.getUuid());
        c.setSubmitterName(userInfo.getUserName());
        c.setUserId(model.getUserId());
        c.setRemark(model.getRemark());
        c.setWarnLine(model.getWarnningLine());
        c.setAbortLine(model.getAbortLine());
        if (StringUtils.isEmpty(model.getUserId())) {
            throw new BusinessException("用户Id不存在");
        }
        BusinessContractRisk risk = businessContractRiskRepository.findBusinessContractRiskByContractId(model.getBusinessContractId());
        if (null == risk) {
            throw new BusinessException(ResultCode.SELECT_NULL_MSG);
        }
        if (risk.getAbortLine() == c.getAbortLine() && risk.getWarningLine() == c.getWarnLine()) {
            throw new BusinessException(ResultCode.RISK_NULL_UPDATE);

        }
        double userAmountByUserId = iBusinessContractControlMapper.findUserAmountByUserId(model.getUserId());
        iBusinessContractControlMapper.findUserfrezzeAmountByUserId(model.getUserId());
        c.setUserFunds(userAmountByUserId);
        c.setPromiseMoney(model.getPromisedMoney());
        ContactRecode save = iContactRecodeRepository.save(c);
        if (!StringUtils.isEmpty(save)) {
            return true;
        }
        return false;
    }

    public boolean applyUpdateStock(UserTokenInfo userInfo, StockEquityModel model) throws BusinessException {
        String stockId = itemService.getStockIdByStockNameAndStockCode(model.getStockName(), model.getStockCode());
        int flag = model.getAmount() % 100;
        if (flag > 0) {
            throw new BusinessException(ResultCode.PARAM_ERR_MSG);
        }
        if (StringUtils.isEmpty(stockId)) {
            throw new BusinessException(ResultCode.STOCK_NULL_ERR);
        }
        BusinessStockHolding holding = businessStockHoldingRepository.findBusinessStockHoldingByDeleteFlagAndStockIdAndContractId("0",stockId,model.getContractId());
        if (null == holding){
            throw new BusinessException(ResultCode.STOCK_NULL_ERR);
        }
        StockEquityRecord r = new StockEquityRecord();
        r.setSubmitterId(userInfo.getUuid());
        r.setAmount(model.getAmount());
        r.setApplyType(model.getApplyType());
        r.setContactId(model.getContractId());
        r.setApplyType(String.valueOf(model.getFalg()));
        r.setRemark(model.getRemarks());
        r.setStockName(model.getStockName());
        r.setStockCode(model.getStockCode());
        r.setBalance(model.getBalance());
        r.setDealPrice(model.getDealPrice());
        r.setUserId(model.getUserCode());
        r.setBuyTime(CommonUtil.getDateFromStr(entrustPattern, model.getBuyTime()));
        r.setCreatorId(userInfo.getUuid());
        r.setCreatorName(userInfo.getUserName());
        r.setCreatedTime(new Date());
        r.setUpdateUserId(userInfo.getUuid());
        r.setUpdateUserName(userInfo.getUserName());
        r.setStockId(stockId);
        StockEquityRecord save = iStockEquityRecordRepository.save(r);
        if (!StringUtils.isEmpty(save)) {
            return true;
        }
        return false;

    }

    public List<ContractEquityModel> QueryApplyList() {
        List<ContactRecode> ContactList = iContactRecodeRepository.findAllByDeleteFlagAndCheckStatus("0", 0);
        List<ContractEquityModel> list = new ArrayList<>();
        for (ContactRecode r : ContactList) {
            UserTokenInfo userInfo = customerFeignService.findUserByUserId(r.getUserId());
            ContractEquityModel model = new ContractEquityModel();
            if (!StringUtils.isEmpty(r.getContactId())) {
                BusinessContract contract = businessContractRepository.findByDeleteFlagAndUuid("0", r.getContactId());
                model.setContractNo(contract.getContractNum());
            }
            model.setSubmitterName(r.getSubmitterName());
            model.setSubmitterId(r.getSubmitterId());
            model.setContractId(r.getContactId());
            model.setUserCode(r.getUserId());
            if (null != userInfo) {
                model.setUserName(userInfo.getUserName());
                model.setUserPhone(userInfo.getPhone());
            }
            model.setApplyType(r.getApplyType());
            model.setRemarks(r.getRemark());
            model.setAbortLine(r.getAbortLine());
            model.setWarnLine(r.getWarnLine());
            model.setExAbortLine(r.getExAbortLine());
            model.setExWarnLine(r.getExWarnLine());
            model.setProductStr(r.getGoodsType());
            model.setCreatedTime(r.getCreateTime());
            model.setContactRecodeId(r.getUuid());
            list.add(model);
        }
        return list;
    }

    public boolean agreeApply(ContractEquityModel model) throws BusinessException {
        Map<String, Object> parms = new HashMap<>();
        //判断警戒线
        if (StringUtils.isEmpty(model.getExWarnLine())) {
            throw new BusinessException(ResultCode.WARNLINE_NOT_EXITS);
        }
        if (StringUtils.isEmpty(model.getExAbortLine())) {
            throw new BusinessException(ResultCode.ABORTLINE_NOT_EXITS);
        }
        if (StringUtils.isEmpty(model.getContractId())) {
            throw new BusinessException(ResultCode.CONTRACTID_NOT_EXITS);
        }
        if (StringUtils.isEmpty(model.getContactRecodeId())) {
            throw new BusinessException(ResultCode.PARAM_ERR_MSG);
        }
        parms.put("contractId", model.getContractId());
        parms.put("exAbortLine", model.getExAbortLine());
        parms.put("exWarnLine", model.getExWarnLine());
        int i = iBusinessContractControlMapper.updateContractRisk(parms);
        if (i <= 0) {
            throw new BusinessException(ResultCode.UPDATE_ERR);
        }
        int j = iBusinessContractControlMapper.updateContactRecode(model.getContactRecodeId());
        if (j <= 0) {
            throw new BusinessException(ResultCode.UPDATE_ERR);
        }
        return true;
    }

    public boolean disagreeApply(ContractEquityModel model) throws BusinessException {
        int j = iBusinessContractControlMapper.disagreeContactRecode(model.getContactRecodeId());
        if (j <= 0) {
            throw new BusinessException(ResultCode.UPDATE_ERR);
        }
        return true;
    }

    public List<StockEquityModel> queryContractList() {
        List<StockEquityModel> equityValidateList = iStockEquityRecordMapper.getEquityValidateList();
        return equityValidateList;
    }

    public String agereeCreateStock(StockEquityModel model) throws BusinessException {
        StockEquityRecord stockEquityRecord = iStockEquityRecordRepository.findByUuidAndDeleteFlag(model.getSrId(), "0");
        int i = iStockEquityRecordMapper.agereeCreateStock(model.getSrId());
        if (0 == i) {
            throw new BusinessException(ResultCode.CONTRACTID_NOT_EXITS);
        }
        if (0 == model.getFalg()) {
            businessService.addStockAfterValidate(stockEquityRecord);
            return ResultCode.CREATE_SUCEESS;
        } else if (1 == model.getFalg()) {
            businessService.multStockAfterValidate(stockEquityRecord);
            return ResultCode.CREATE_SUCEESS;
        }
        return ResultCode.SELECT_NULL_MSG;
    }

    public boolean disAgereeCreateStock(StockEquityModel model) throws BusinessException {
        int i = iStockEquityRecordMapper.disAgereeCreateStock(model.getSrId());
        if (0 == i) {
            throw new BusinessException(ResultCode.CONTRACTID_NOT_EXITS);
        }
        return true;
    }

    public ContractModel getContractById(String contractId) {
        ContractModel model = businessService.getContractById(contractId);
        model.setProductName(ProductStatus.getName(model.getChoseWay()));
        return model;
    }

    public String getStockPrice(String stockCode) {
        List<String> stockCodes = new ArrayList<String>();
        stockCodes.add(stockCode);
        List<QuotesTenModel> quotesTenModels = businessService.getQuotesTenModel(stockCodes.toArray(new String[stockCodes.size()]));
        if (quotesTenModels.size() > 0) {
            return String.valueOf(quotesTenModels.get(0).getCurPrice());
        }
        return null;
    }
}
