package cn.com.fintheircing.admin.dividend.service;

import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.entity.BusinessControlContract;
import cn.com.fintheircing.admin.business.entity.BusinessStockHolding;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.common.constant.ControlCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.model.ValidateModel;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.dividend.dao.mapper.IDividendMapper;
import cn.com.fintheircing.admin.dividend.dao.repository.IDividendRelationRepository;
import cn.com.fintheircing.admin.dividend.dao.repository.IDividendRepository;
import cn.com.fintheircing.admin.dividend.entity.Dividend;
import cn.com.fintheircing.admin.dividend.entity.DividendRelation;
import cn.com.fintheircing.admin.dividend.exception.DividendException;
import cn.com.fintheircing.admin.dividend.model.DividendControlModel;
import cn.com.fintheircing.admin.dividend.model.DividendHoldingModel;
import cn.com.fintheircing.admin.dividend.model.DividendModel;
import cn.com.fintheircing.admin.dividend.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.system.exception.SystemException;
import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DividendService {
    @Resource
    private ItemService itemService;
    @Resource
    private IDividendRepository dividendRepository;
    @Resource
    private BusinessService businessService;
    @Resource
    private IDividendRelationRepository dividendRelationRepository;
    @Resource
    private IDividendMapper dividendMapper;

    @Value("${custom.system.sdformat}")
    private String sdformat;
    @Value("${custom.system.hms}")
    private String hms;
    @Value("${custom.admin.uuid}")
    private String adminUuid;

    //保存除权除息
    public void saveDividend(UserTokenInfo userInfo, DividendModel model) throws DividendException {
        String stockId = itemService.getStockIdByStockNameAndStockCode(model.getStockName(), model.getStockCode());
        if (StringUtils.isEmpty(stockId)) {
            throw new DividendException(ResultCode.STOCK_VALIDATE_ERR);
        }
        try {
            Dividend dividend = new Dividend();
            dividend.setStockId(stockId);
            if (!StringUtils.isEmpty(model.getActiveTimeAmount())) {
                dividend.setActiveTimeAmount(CommonUtil.getlongTime(model.getActiveTimeAmount() + hms, sdformat));
            }
            if (!StringUtils.isEmpty(model.getActiveTimeMoney())) {
                dividend.setActiveTimeMoney(CommonUtil.getlongTime(model.getActiveTimeMoney() + hms, sdformat));
            }
            dividend.setStatus(Dividend.STATUS_WAIT);   //未到启动日期
            dividend.setTenStockAmount(model.getAmount());
            dividend.setTenStockCost(model.getCost());
            dividend.setTenStockMoney(model.getMoney());
            dividend.setCreatedTime(new Date());
            dividend.setCreatorId(userInfo.getUuid());
            dividend.setCreatorName(userInfo.getUserName());
            dividend.setUpdateUserId(userInfo.getUuid());
            dividend.setUpdateUserName(userInfo.getUserName());
            dividend = dividendRepository.save(dividend);
            List<BusinessStockHolding> holdings = businessService.getHoldingByStockId(stockId);
            List<DividendRelation> relations = new ArrayList<DividendRelation>();
            for (BusinessStockHolding holding : holdings) {
                if (dividend.getActiveTimeAmount() > 0) {
                    DividendRelation relation = new DividendRelation();
                    relation.setContractId(holding.getContractId());
                    relation.setAmount(holding.getAmount());
                    relation.setStockId(holding.getStockId());
                    relation.setDividendId(dividend.getUuid());
                    relation.setValidateStatus(DividendRelation.VALIDATE_WAIT);
                    relation.setChoseWay(DividendRelation.CHOSE_STOCK);
                    relation.setHappenTime(dividend.getActiveTimeAmount());
                    relation.setTenStockAmount(dividend.getTenStockAmount());
                    relation.setCreatedTime(new Date());
                    relation.setCreatorId(userInfo.getUuid());
                    relation.setCreatorName(userInfo.getUserName());
                    relation.setUpdateUserId(userInfo.getUuid());
                    relation.setUpdateUserName(userInfo.getUserName());
                    relation.setDividendStatus(DividendRelation.DIVIDEND_WAIT);
                    relations.add(relation);
                }
                if (dividend.getActiveTimeMoney() > 0) {
                    DividendRelation relation = new DividendRelation();
                    relation.setContractId(holding.getContractId());
                    relation.setStockId(holding.getStockId());
                    relation.setDividendId(dividend.getUuid());
                    relation.setAmount(holding.getAmount());
                    relation.setValidateStatus(DividendRelation.VALIDATE_WAIT);
                    relation.setChoseWay(DividendRelation.CHOSE_MONEY);
                    relation.setHappenTime(dividend.getActiveTimeMoney());
                    relation.setTenStockCost(dividend.getTenStockCost());
                    relation.setTenStockMoney(dividend.getTenStockMoney());
                    relation.setCreatedTime(new Date());
                    relation.setCreatorId(userInfo.getUuid());
                    relation.setCreatorName(userInfo.getUserName());
                    relation.setUpdateUserId(userInfo.getUuid());
                    relation.setUpdateUserName(userInfo.getUserName());
                    relation.setDividendStatus(DividendRelation.DIVIDEND_WAIT);
                    relations.add(relation);
                }
            }
            dividendRelationRepository.saveAll(relations);
        } catch (SystemException e) {
            throw new DividendException(e.getMessage());
        }
    }

    //分页除权除息
    public PageInfo<DividendModel> getDividendList(int index, int size) {
        PageHelper.startPage(index, size);
        List<DividendModel> dividendModels = dividendMapper.getDividendByStatus(DividendRelation.VALIDATE_WAIT,"");
        PageInfo<DividendModel> pageInfo = new PageInfo<DividendModel>(dividendModels);
        return pageInfo;
    }

    //获取除权除息审核
    public DividendModel getDividendDetail(String id) {
        DividendRelation relation = dividendRelationRepository.findByUuid(id);
        DividendModel model = MappingEntity2ModelConverter.CONVERTERFROMDIVIDENDRELATION(relation);
        Map<String, String> map = itemService.getStockNameAndStockCodeById(id);
        model.setStockName(map.get("name"));
        model.setStockCode(map.get("code"));
        return model;
    }

    //审核除权除息
    public void validateDividend(UserTokenInfo userInfo, ValidateModel model) throws DividendException {
        DividendRelation relation = dividendRelationRepository.findByUuid(model.getId());
        if (null == relation) {
            throw new DividendException(ResultCode.SELECT_NULL_MSG);
        }
        if (DividendRelation.VALIDATE_PASS.equals(model.getValidateResult())) {
            relation.setValidateStatus(DividendRelation.VALIDATE_PASS);
        } else if (DividendRelation.VALIDATE_SUS.equals(model.getValidateResult())) {
            relation.setValidateStatus(DividendRelation.VALIDATE_SUS);
        } else {
            throw new DividendException(ResultCode.PARAM_ERR_MSG);
        }
        relation.setValidateTime(new Date().getTime());
        relation.setUpdateUserId(userInfo.getUuid());
        relation.setUpdateUserName(userInfo.getUserName());
        dividendRelationRepository.save(relation);
    }

    //定时除权除息
    public void scheduledDividend() {
        List<DividendModel> dividendModels = dividendMapper.getDividendByStatus(DividendRelation.VALIDATE_SUS,DividendRelation.DIVIDEND_WAIT);
        Set<String> dividendIds = new HashSet<String>();
        long nowTime = new Date().getTime();
        for (DividendModel model : dividendModels) {
            if (model.getHappenTime() < nowTime) {
                int rate = (model.getDividendAmount() / 10);
                int amount = rate * model.getAmount();
                double cost = rate * model.getCost();
                double money = rate * model.getMoney();
                BusinessContract contract = businessService.getContractByContractId(model.getContractId());
                BusinessStockHolding holding = businessService.getHoldingByContractIdAndStockId(model.getContractId(), model.getStockId());
                if (0 != amount) {
                    holding.setAmount(amount + holding.getAmount());
                    holding.setColdAmount(amount + holding.getColdAmount());
                    businessService.saveHolding(holding);
                }
                if (0 != money) {
                    contract.setAvailableMoney(contract.getAvailableMoney() + money - cost);
                    businessService.saveContract(contract);
                }

                DividendRelation relation = dividendRelationRepository.findByUuid(model.getId());
                relation.setDividendStatus(DividendRelation.DIVIDEND_END);
                dividendRelationRepository.save(relation);

                BusinessControlContract controlContract = new BusinessControlContract();
                controlContract.setControlType(ControlCode.CONTROL_DIVIDEND.getIndex());
                controlContract.setContractId(contract.getUuid());
                controlContract.setVerifyStatus(VerifyCode.VERIFY_S.getIndex());
                controlContract.setLessMoney(contract.getAvailableMoney());
                controlContract.setAddMoney(money);
                controlContract.setCostMoney(cost);
                controlContract.setCreatedTime(new Date());
                controlContract.setCreatorId(adminUuid);
                controlContract.setAddStock(amount);
                controlContract.setLessStock(holding.getAmount());
                controlContract.setStockId(holding.getStockId());
                businessService.saveContractControl(controlContract);
                dividendIds.add(model.getDividendId());
            }
        }
        List<Dividend> dividends = new ArrayList<Dividend>();
        for (String dividendId:dividendIds){
            Dividend dividend = dividendRepository.findByUuid(dividendId);
            if (dividend.getActiveTimeMoney()<nowTime && dividend.getActiveTimeAmount()<nowTime){
                dividend.setStatus(Dividend.STATUS_END);
                dividends.add(dividend);
            }
        }
        dividendRepository.saveAll(dividends);
    }

    //查看操作
    public PageInfo<DividendControlModel> getValidateRecord(UserTokenInfo userInfo,DividendControlModel model) throws DividendException{
        try {
            if (!StringUtils.isEmpty(model.getBegin())) {
                model.setBeginTime(CommonUtil.getlongTime(model.getBegin(), sdformat));
            }
            if (!StringUtils.isEmpty(model.getEnd())) {
                model.setEndTime(CommonUtil.getEndLongTime(model.getEnd(), sdformat));
            }
        } catch (SystemException e) {
            throw new DividendException(e.getMessage());
        }
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<DividendControlModel> models = dividendMapper.getDividendControl(model);
        for (DividendControlModel model1:models){
            model1.setProduct(ProductStatus.getName(model1.getChoseWay()));
            if (DividendRelation.VALIDATE_WAIT.equals(model1.getValidateStatus())){
                model1.setValidateStatus("未审核");
            }else if (DividendRelation.VALIDATE_PASS.equals(model1.getValidateStatus())){
                model1.setValidateStatus("未通过");
            }else if (DividendRelation.VALIDATE_SUS.equals(model1.getValidateStatus())){
                model1.setValidateStatus("通过");
            }
        }
        PageInfo<DividendControlModel> pageInfo = new PageInfo<DividendControlModel>(models);
        return pageInfo;
    }

    //根据关键字查询合约持有
    public PageInfo<DividendHoldingModel> getContractHolding(int index,int size,String keyWord){
        return businessService.getPageHolding(index,size,keyWord);
    }
}
