package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.constant.BusinessStatus;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractControlMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessControlContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockEntrustRepository;
import cn.com.fintheircing.admin.business.dao.repository.IContactRecodeRepository;
import cn.com.fintheircing.admin.business.dao.repository.IStockEntrustRecodeRespository;
import cn.com.fintheircing.admin.business.entity.BusinessControlContract;
import cn.com.fintheircing.admin.business.entity.BusinessStockEntrust;
import cn.com.fintheircing.admin.business.entity.recode.ContactRecode;
import cn.com.fintheircing.admin.business.entity.recode.StockEntrustRecode;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContractService {
    @Resource
    private IBusinessContractControlMapper iBusinessContractControlMapper;
    @Resource
    private IBusinessStockEntrustRepository iBusinessStockEntrustRepository;
    @Resource
    private TransactionSummaryRepository transactionSummaryRepository;
    @Resource
    private IBusinessControlContractRepository iBusinessControlContractRepository;
    @Resource
    private IStockEntrustRecodeRespository iStockEntrustRecodeRespository;
    @Resource
    private IContactRecodeRepository iContactRecodeRepository;
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
//合约权益增 删 改操作
    public boolean oparteStockInContact(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException {
       //关联用户
        String userName = iBusinessContractControlMapper.findNameByUserId(model.getUserId());
        //冻结资金
        double frezzeAmount = iBusinessContractControlMapper.findUserfrezzeAmountByUserId(model.getUserId());
        //可用余额
        double Amount = iBusinessContractControlMapper.findUserAmountByUserId(model.getUserId());
        StockEntrustRecode stockEntrustRecode =new StockEntrustRecode();
        stockEntrustRecode.setAmount(model.getAmount());
        stockEntrustRecode.setBuyTime(model.getBuyTime());
        stockEntrustRecode.setCheckStatus(0);
        stockEntrustRecode.setContactId(model.getContractId());
        stockEntrustRecode.setDealPrice(model.getDealPrice());
        stockEntrustRecode.setPhone(userInfo.getPhone());
        stockEntrustRecode.setStockName(model.getStockName());
        stockEntrustRecode.setRemark(model.getRemark());
        stockEntrustRecode.setStockId(model.getStockId());
        stockEntrustRecode.setSubmitterName(userInfo.getUserName());
        stockEntrustRecode.setSubmitterId(userInfo.getUuid());
        stockEntrustRecode.setUserId(model.getUserId());
        stockEntrustRecode.setUserName(userName);
        stockEntrustRecode.setBusinessStockEntrustId(model.getBusinessStockEntrustId());
        stockEntrustRecode.setCodeMoney(frezzeAmount);
        stockEntrustRecode.setUserfulMoney(Amount);
        stockEntrustRecode.setApplyType(model.getApplyType());
        StockEntrustRecode save = iStockEntrustRecodeRespository.save(stockEntrustRecode);

        if (StringUtils.isEmpty(save)){
            return false;
        }
        return true;
    }


    /**
     * 合约提交申请
     * @param model
     * @return
     */
    public boolean applyList(UserTokenInfo userTokenInfo,ContractControlModel model) {
        BusinessControlContract ControlContract = iBusinessControlContractRepository.findOneByUuid(model.getBusinessControlContractId());
        double funds = iBusinessContractControlMapper.findUserAmountByUserId(model.getUserId());
        ContactRecode contactRecode =new ContactRecode();
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
        ContactRecode save = iContactRecodeRepository.save(contactRecode);
        if (StringUtils.isEmpty(save)){
            return false;
        }
        return true;
    }
}