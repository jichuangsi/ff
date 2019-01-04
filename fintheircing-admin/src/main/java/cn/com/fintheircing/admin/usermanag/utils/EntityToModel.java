package cn.com.fintheircing.admin.usermanag.utils;

import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.entity.UserClientInfo;
import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IUserClientInfoRepository;
import cn.com.fintheircing.admin.usermanag.entity.AskMoneyInfo;
import cn.com.fintheircing.admin.usermanag.entity.contact.ContactInfo;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.AskMoneyInfoModel;
import cn.com.fintheircing.admin.usermanag.model.ContactInfoModel;
import cn.com.fintheircing.admin.usermanag.model.UserClientInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

public class EntityToModel {
    @Autowired
    IUserClientInfoRepository userClientInfoRepository;

    public static TodoTaskInfo CoverTodoTaskInfoModel(CreateRegTodoTaskModel model){
        TodoTaskInfo taskInfo =new TodoTaskInfo();
        taskInfo.setTaskInfoId(model.getRegisterUserId());
        taskInfo.setTaskType(model.getTaskType());
        taskInfo.setStatus(TodoTaskInfo.STATUS_FIN);
        return taskInfo;
    }


    public static AdminClientInfoModel coverAdminClientInfo(AdminClientInfo adminClientInfo, UserClientInfo userClientInfo){
        AdminClientInfoModel model=new AdminClientInfoModel();
        model.setId(userClientInfo.getId());
        model.setCer(userClientInfo.getCer());
        model.setSource(userClientInfo.getSource());
        model.setCreateTime(userClientInfo.getCreatedTime());
        //model.setEmployeeId(adminClientInfo.getEmpolyeeId());
        model.setPhone(adminClientInfo.getPhone());
        model.setProxyId(adminClientInfo.getProxyNum());
        model.setProxyName(adminClientInfo.getName());
        model.setUserId(StringUtils.isEmpty(userClientInfo.getUserId())?
                UUID.randomUUID().toString().replace("-",""):userClientInfo.getUserId());
        model.setUserName(adminClientInfo.getUserName());

        return model;
    }
    public static List<AdminClientInfoModel> coverAdminClientInfos(List<AdminClientInfo> userInfos, UserClientInfo userClientInfo){
        List<AdminClientInfoModel> models=null;
        AdminClientInfoModel model= new AdminClientInfoModel();
        List<String> list=null;
        userInfos.forEach(userInfo -> {
            //匹配上下级信息
            for (AdminClientInfo user : userInfos) {
                if (userInfo.getBossId()==user.getProxyNum()){
                    list.add(user.getProxyNum());
                }
                }
            AdminClientInfoModel adminClientInfoModel = coverAdminClientInfo( userInfo, userClientInfo);
            models.add(adminClientInfoModel);
        });
        return models;
    }

    public static AskMoneyInfoModel coverAskMoneyInfo(AskMoneyInfo user, UserClientInfo userClientInfo){
        AskMoneyInfoModel model =new AskMoneyInfoModel();
        model.setApplyMoney(user.getApplyMoney());
        model.setArrivalTime(user.getArrivalTime());
        model.setBankCard(user.getBankCard());
        model.setBankName(user.getBankName());
        model.setId(userClientInfo.getId());
        model.setPayAcount(user.getPayAcount());
        model.setRecharge(user.getRecharge());
        model.setRechargeTime(user.getRechargeTime());
        model.setTaskStatus(userClientInfo.getStatus());
        model.setTrulyMoney(user.getTrulyMoney());
        model.setUserId(userClientInfo.getUserId());
        model.setPayFrom(user.getPayFrom());
        model.setSource(userClientInfo.getSource());
        model.setApplyTime(user.getApplyTime());
        model.setRemark(user.getRemark());
        model.setStatus(user.getStatus());
        model.setPayWay(user.getPayWay());
        return model;
    }

    public static List<ContactInfoModel> coverContactInfo(List<ContactInfo> info){
        List<ContactInfoModel> models=null;
        ContactInfoModel model=new ContactInfoModel();
        info.forEach(user->{
            model.setId(user.getId());
            model.setAssureMoney(user.getAssureMoney());
            model.setAvailableCash(user.getAvailableCash());
            model.setBorrowDays(user.getBorrowMoney());
           /* model.setBorrowRate(user.getContactDetails().getBorrowRate());*/
            model.setBorrowTime(user.getBorrowTime());
            model.setContactAllMoney(user.getContactAllMoney());
            /*model.setContactAssets(user.getContactDetails().getContactAssets());*/
            model.setContactId(user.getContactId());
            model.setCreateTime(user.getCreateTime());
            /*model.setDangerLevel(user.getContactDetails().getDangerLevel());*/
            model.setDeficitAmount(user.getDeficitAmount());
            model.setDeficitLine(user.getDeficitLine());
           /* model.setDelegationOrigin(user.getContactDetails().getDelegationOrigin());
            model.setFirstRate(user.getContactDetails().getFirstRate());*/
            model.setFloatFilled(user.getFloatFilled());
            model.setGoodsType(user.getGoodsType());
            /*model.setRemainderDays(user.getContactDetails().getRemainderDays());*/
            model.setRemainderTime(user.getRemainderTime());
            model.setStatus(user.getStatus());
            model.setStockValue(user.getStockValue());
//            model.setTaskStatus(user.getContactDetails().getTaskStatus());
//            model.setTransactionCommission(user.getContactDetails().getTransactionCommission());
//            model.setStockName(user.getContractStock().getStockName());
//            model.setStockId(user.getContractStock().getStockId());
//            model.setStockAccount(user.getContractStock().getStockAccount());
//            model.setBuyPrice(user.getContractStock().getBuyPrice());
//            model.setBuyTime(user.getContractStock().getBuyTime());
//            model.setMarketPrice(user.getContractStock().getMarketPrice());
//            model.setMarketValue(user.getContractStock().getMarketValue());
            models.add(model);
        });
        return models;
    }

    public static UserClientInfoModel coverUserClientInfo(UserClientInfo oneByUuid) {
        UserClientInfoModel model =new UserClientInfoModel();
        model.setCer(oneByUuid.getCer());
        model.setSource(oneByUuid.getSource());
        model.setStatus(oneByUuid.getStatus());
        model.setId(oneByUuid.getId());
        model.setUserId(oneByUuid.getUserId());
        model.setDisplayname(oneByUuid.getDisplayname());
        model.setCreatedTime(oneByUuid.getCreatedTime());
        model.setCreatorId(oneByUuid.getCreatorId());
        model.setDeleteFlag(oneByUuid.getDeleteFlag());
        model.setCreatorName(oneByUuid.getCreatorName());
        model.setUpdatedTime(oneByUuid.getUpdatedTime());
        model.setUpdateUserId(oneByUuid.getUpdateUserId());
        model.setUpdateUserName(oneByUuid.getUpdateUserName());
        return model;
    }
}
