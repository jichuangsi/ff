package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.constant.ApplyStatus;
import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractControlMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IStockEquityRecordMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessControlContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IContactRecodeRepository;
import cn.com.fintheircing.admin.business.dao.repository.IStockEquityRecordRepository;
import cn.com.fintheircing.admin.business.entity.BusinessControlContract;
import cn.com.fintheircing.admin.business.entity.record.ContactRecode;
import cn.com.fintheircing.admin.business.entity.record.StockEquityRecord;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.record.ContactApplyModel;
import cn.com.fintheircing.admin.business.model.record.StockEquityModel;
import cn.com.fintheircing.admin.business.utils.MappingModel2EntityConverter;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.EnumTypeModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ContractService {
    @Resource
    private IBusinessContractControlMapper iBusinessContractControlMapper;
    @Resource
    private IBusinessControlContractRepository iBusinessControlContractRepository;
    @Resource
    private IContactRecodeRepository iContactRecodeRepository;
    @Resource
    private IStockEquityRecordRepository stockEquityRecordRepository;
    @Resource
    private BusinessService businessService;
    @Resource
    private IStockEquityRecordMapper stockEquityRecordMapper;
    @Resource
    private ItemService itemService;

    public List<ContractControlModel> findAllContact(String productStr) {
        List<ContractControlModel> allContact = iBusinessContractControlMapper.findAllContact(productStr);
        for (ContractControlModel m : allContact
        ) {
            m.setTotalAssets(m.getBorrowMoney() + m.getPromisedMoney() + m.getCurrentWorth() + m.getLessMoney());
            m.setLessTime((m.getExpiredTime() - m.getBorrowTime()) / 1000 / 60 / 60 / 24);
            m.setVerifyStr(BusinessStatus.getName(m.getVerifyStatus()));
            m.setNetAssets(m.getBorrowMoney() + m.getLessMoney());
        }
        return allContact;
    }

    public List<StockEntrustModel> updateContact(String contractId) {
        return iBusinessContractControlMapper.findAllStock(contractId);
    }

    /**
     * 合约提交申请
     *
     * @param model
     * @return
     */
    public boolean applyList(UserTokenInfo userTokenInfo, ContractControlModel model) {
        BusinessControlContract ControlContract = iBusinessControlContractRepository.findOneByUuid(model.getBusinessControlContractId());
        double funds = iBusinessContractControlMapper.findUserAmountByUserId(model.getUserId());
        ContactRecode contactRecode = new ContactRecode();
        contactRecode.setAbortLine(model.getAbortLine());
        contactRecode.setApplyType("修改");
        contactRecode.setContactId(model.getContractNo());
        contactRecode.setExAbortLine(ControlContract.getAbortLine());
        contactRecode.setGoodsType(model.getProductStr());
        contactRecode.setPhone(model.getPhone());
        contactRecode.setExWarnLine(ControlContract.getWarnningLine());
        contactRecode.setPromiseMoney(model.getPromisedMoney());
        contactRecode.setRemark(model.getRemark());
        contactRecode.setCheckStatus(0);
        contactRecode.setUserFunds(funds);
        contactRecode.setUserName(model.getName());
        contactRecode.setUserId(model.getUserId());
        contactRecode.setSubmitterId(userTokenInfo.getUuid());
        contactRecode.setSubmitterName(userTokenInfo.getUserName());
        contactRecode.setBusinessControlContractId(model.getBusinessControlContractId());
        ContactRecode save = iContactRecodeRepository.save(contactRecode);
        if (StringUtils.isEmpty(save)) {
            return false;
        }
        return true;
    }

    //保存提交增减股份申请
    public void saveStockRecode(UserTokenInfo userInfo, StockEquityModel model) throws BusinessException {
        String stockId = itemService.getStockIdByStockNameAndStockCode(model.getStockName(),model.getStockCode());
        if (StringUtils.isEmpty(stockId)){
            throw new BusinessException(ResultCode.STOCK_NULL_ERR);
        }
        StockEquityRecord equityRecode = MappingModel2EntityConverter.CONVERTERFROMSTOCKEQUITYMODEL(model);
        equityRecode.setCheckStatus(0);
        equityRecode.setCreatedTime(new Date());
        equityRecode.setSubmitterId(userInfo.getUuid());
        equityRecode.setCreatorId(userInfo.getUuid());
        equityRecode.setCreatorName(userInfo.getUserName());
        equityRecode.setUpdateUserId(userInfo.getUuid());
        equityRecode.setUpdateUserName(userInfo.getUserName());
        equityRecode.setStockId(stockId);
        stockEquityRecordRepository.save(equityRecode);
    }

    //获取增减股份时的查看的股份持仓
    public PageInfo<StockHoldingModel> getEquityHolding(String contractId, int index, int size) {
        return businessService.getEquityHolding(contractId, index, size);
    }

    //获取增减条件以及合约的资金
    public Map<String, Object> getContractCondition(String contractId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<EnumTypeModel> enumTypeModels = new ArrayList<EnumTypeModel>();
        ApplyStatus[] applyStatuses = ApplyStatus.values();
        for (ApplyStatus status : applyStatuses) {
            EnumTypeModel model = new EnumTypeModel();
            model.setTypeStr(status.getName());
            model.setType(status.getNum());
            enumTypeModels.add(model);
        }
        map.put("condition", enumTypeModels);
        ContractModel model = businessService.getContractById(contractId);
        map.put("contract", model);
        return map;
    }

    //获取待审核合约权益列表
    public PageInfo<StockEquityModel> getEquityValidateList(int index, int size) {
        PageHelper.startPage(index, size);
        List<StockEquityModel> equityModels = stockEquityRecordMapper.getEquityValidateList();
        PageInfo<StockEquityModel> pageInfo = new PageInfo<StockEquityModel>(equityModels);
        return pageInfo;
    }

    //审核股票权益
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void validateContractStock(UserTokenInfo userInfo, String equityId, Integer result) throws BusinessException {
        StockEquityRecord equityRecord = stockEquityRecordRepository.findByUuidAndDeleteFlag(equityId, "0");
        if (1 == result || 2 == result) {
            equityRecord.setCheckStatus(result);
        } else {
            throw new BusinessException(ResultCode.PARAM_ERR_MSG);
        }
        equityRecord.setUpdateUserName(userInfo.getUserName());
        equityRecord.setUpdateUserId(userInfo.getUuid());
        stockEquityRecordRepository.save(equityRecord);
        if (1 == result){
            if ("0".equals(equityRecord.getApplyType())){
                //添加
                businessService.addStockAfterValidate(equityRecord);
            }else if ("1".equals(equityRecord.getApplyType())){
                //删减
                businessService.multStockAfterValidate(equityRecord);
            }
        }
    }

    /**
     * 查看合约申请列表
     * @param
     * @return
     */
    public List<ContactApplyModel> QueryContactList( ) {
        List<ContactRecode> allByCheckStatus = iContactRecodeRepository.findAllByCheckStatus(0);
        List<ContactApplyModel> list =new ArrayList<>();
        for (ContactRecode model:allByCheckStatus
             ) {
            ContactApplyModel ContactRecode =new ContactApplyModel();
            ContactRecode.setAbortLine(model.getAbortLine());
            ContactRecode.setApplyType(model.getApplyType());
            ContactRecode.setCheckStatus( model.getCheckStatus());
            ContactRecode.setContactId(model.getContactId());
            ContactRecode.setCreateTime( model.getCreateTime());
            ContactRecode.setExAbortLine( model.getExAbortLine());
            ContactRecode.setGoodsType(model.getGoodsType());
            ContactRecode.setPromiseMoney( model.getPromiseMoney());
            ContactRecode.setPhone( model.getPhone());
            ContactRecode.setSubmitterId( model.getSubmitterId());
            ContactRecode.setUserFunds(model.getUserFunds());
            ContactRecode.setUserId(model.getUserId());
            ContactRecode.setUserName(model.getUserName());
            ContactRecode.setRemark(model.getRemark());
            ContactRecode.setExWarnLine(model.getExWarnLine());
            ContactRecode.setBusinessControlContractId( model.getBusinessControlContractId());
            ContactRecode.setWarnLine( model.getWarnLine());
            ContactRecode.setSubmitterName( model.getSubmitterName());
            ContactRecode.setContactRecodeId(model.getUuid());
            list.add(ContactRecode);
        }
        return list;
    }

    public boolean agreeApplyForContact(ContactApplyModel model) {
        return false;
    }
}