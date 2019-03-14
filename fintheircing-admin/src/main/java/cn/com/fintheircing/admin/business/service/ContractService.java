package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractControlMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IStockEquityRecordMapper;
import cn.com.fintheircing.admin.business.dao.repository.IContactRecodeRepository;
import cn.com.fintheircing.admin.business.dao.repository.IStockEquityRecordRepository;
import cn.com.fintheircing.admin.business.entity.record.ContactRecode;
import cn.com.fintheircing.admin.business.entity.record.StockEquityRecord;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.record.ContractEquityModel;
import cn.com.fintheircing.admin.business.model.record.StockEquityModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private BusinessService businessService;
    public boolean ContactDetails(UserTokenInfo userInfo, ContractControlModel model) {
        ContactRecode c =new ContactRecode();
        c.setApplyType("调整息费");
        c.setContactId(model.getBusinessContractId());
        c.setExAbortLine(model.getExAbortLine());
        c.setSubmitterId(userInfo.getUuid());
        c.setSubmitterName(userInfo.getUserName());
        c.setUserId(model.getUserId());
        c.setUserName(model.getName());
        c.setPhone(model.getPhone());
        c.setRemark(model.getRemark());
        c.setWarnLine(model.getWarnningLine());
        c.setExWarnLine(model.getExWarnningLine());
        c.setAbortLine(model.getAbortLine());
        double userAmountByUserId = iBusinessContractControlMapper.findUserAmountByUserId(model.getUserId());
        iBusinessContractControlMapper.findUserfrezzeAmountByUserId(model.getUserId());

        c.setUserFunds(userAmountByUserId);
        c.setPromiseMoney(model.getPromisedMoney());
        ContactRecode save = iContactRecodeRepository.save(c);
        if (!StringUtils.isEmpty(save)){
            return true;
        }
        return false;
    }

    public boolean applyUpdateStock(UserTokenInfo userInfo, StockEquityModel model) {
        StockEquityRecord r =new StockEquityRecord();
        r.setSubmitterId(userInfo.getUuid());
        r.setAmount(model.getAmount());
        r.setContactId(model.getContractId());
        r.setApplyType(model.getApplyType());
        r.setRemark(model.getApplyType());
        r.setStockName(model.getStockName());
        r.setStockCode(model.getStockCode());
        r.setBalance(model.getBalance());
        r.setDealPrice(model.getDealPrice());
        r.setUserId(model.getUserCode());
        StockEquityRecord save = iStockEquityRecordRepository.save(r);
        if (!StringUtils.isEmpty(save)){
            return true;
        }
        return false;

    }

    public List<ContractEquityModel> QueryApplyList() {
        List<ContactRecode> ContactList = iContactRecodeRepository.findAllByDeleteFlagAndCheckStatus("0",0);
        List<ContractEquityModel> list=new ArrayList<>();
        for (ContactRecode r :ContactList){
            ContractEquityModel model =new ContractEquityModel();
            model.setSubmitterName(r.getSubmitterName());
            model.setSubmitterId(r.getSubmitterId());
            model.setContractId(r.getContactId());
            model.setUserCode(r.getUserId());
            model.setUserName(r.getUserName());
            model.setUserPhone(r.getPhone());
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

    public boolean agreeApply(ContractEquityModel model)throws BusinessException {
        Map<String,Object> parms =new HashMap<>();
        //判断警戒线
        if (StringUtils.isEmpty(model.getExWarnLine())){
            throw new BusinessException(ResultCode.WARNLINE_NOT_EXITS);
        }
        if (StringUtils.isEmpty(model.getExAbortLine())){
            throw new BusinessException(ResultCode.ABORTLINE_NOT_EXITS);
        }
        if (StringUtils.isEmpty(model.getContractId())){
            throw new BusinessException(ResultCode.CONTRACTID_NOT_EXITS);
        }
        parms.put("contractId", model.getContractId());
        parms.put("exAbortLine",model.getExAbortLine());
        parms.put("exWarnLine",model.getExWarnLine());
        int i = iBusinessContractControlMapper.updateContractRisk(parms);
        if (i<=0){
            throw new BusinessException(ResultCode.UPDATE_ERR);
        }
        int j = iBusinessContractControlMapper.updateContactRecode(model.getContactRecodeId());
        if (j<=0){
            throw new BusinessException(ResultCode.UPDATE_ERR);
        }
        return true;
    }

    public boolean disagreeApply(ContractEquityModel model) throws BusinessException {
        int j = iBusinessContractControlMapper.disagreeContactRecode(model.getContactRecodeId());
        if (j<=0){
            throw new BusinessException(ResultCode.UPDATE_ERR);
        }
        return true;
    }

    public  List<StockEquityModel>  queryContractList() {
        List<StockEquityModel> equityValidateList = iStockEquityRecordMapper.getEquityValidateList();
        return equityValidateList;
    }

    public String agereeCreateStock(StockEquityModel model) throws BusinessException {
        StockEquityRecord stockEquityRecord = iStockEquityRecordRepository.findByUuidAndDeleteFlag(model.getSrId(), 0);
        int i = iStockEquityRecordMapper.agereeCreateStock(model.getSrId());
        if (0==i){
            throw new BusinessException(ResultCode.CONTRACTID_NOT_EXITS);
        }
        if (0==model.getFalg()) {
            businessService.addStockAfterValidate(stockEquityRecord);
            return ResultCode.CREATE_SUCEESS;
        }else if (1==model.getFalg()){
            businessService.multStockAfterValidate(stockEquityRecord);
            return ResultCode.CREATE_SUCEESS;
        }
            return ResultCode.SELECT_NULL_MSG;
}
    public boolean disAgereeCreateStock(StockEquityModel model) throws BusinessException {
        int i = iStockEquityRecordMapper.disAgereeCreateStock(model.getSrId());
        if (0==i){
            throw new BusinessException(ResultCode.CONTRACTID_NOT_EXITS);
        }
        return true;
    }
}
