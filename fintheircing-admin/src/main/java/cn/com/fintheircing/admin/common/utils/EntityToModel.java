package cn.com.fintheircing.admin.common.utils;

import cn.com.fintheircing.admin.Repository.UserClientInfoRepository;
import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.entity.AskMoneyInfo;
import cn.com.fintheircing.admin.common.entity.Contact.contactInfo;
import cn.com.fintheircing.admin.common.entity.UserClientInfo;
import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

public class EntityToModel {
    @Autowired
    UserClientInfoRepository userClientInfoRepository;

    public static TodoTaskInfo CoverTodoTaskInfoModel(CreateRegTodoTaskModel model){
        TodoTaskInfo taskInfo =new TodoTaskInfo();
        taskInfo.setTaskInfoId(model.getRegisterUserId());
        taskInfo.setTaskType(model.getTaskType());
        taskInfo.setStatus(TodoTaskInfo.STATUS_FIN);
        return taskInfo;
    }


    public static AdminClientInfModel coverAdminClientInfo(AdminClientInfo adminClientInfo, UserClientInfo userClientInfo){
        AdminClientInfModel model=new AdminClientInfModel();
        model.setId(userClientInfo.getId());
        model.setCer(userClientInfo.getCer());
        model.setSource(userClientInfo.getSource());
        model.setCreateTime(userClientInfo.getCreatedTime());
        //model.setEmployeeId(adminClientInfo.getEmpolyeeId());
        model.setPhone(userClientInfo.getPhone());
        model.setProxyId(adminClientInfo.getProxyNum());
        model.setProxyName(userClientInfo.getUserName());
        model.setUserId(StringUtils.isEmpty(userClientInfo.getUserId())?
                UUID.randomUUID().toString().replace("-",""):userClientInfo.getUserId());
        model.setUserName(userClientInfo.getUserName());

        return model;
    }
    public static List<cn.com.fintheircing.admin.usermanag.model.AdminClientInfModel> coverAdminClientInfos(List<AdminClientInfo> userInfos, UserClientInfo userClientInfo){
        List<AdminClientInfModel> models=null;
        AdminClientInfModel model= new AdminClientInfModel();
        List<String> list=null;
        userInfos.forEach(userInfo -> {
            //匹配上下级信息
            for (AdminClientInfo user : userInfos) {
                if (userInfo.getBossId()==user.getProxyNum()){
                    list.add(user.getProxyNum());
                }
                }
            AdminClientInfModel adminClientInfModel = coverAdminClientInfo( userInfo, userClientInfo);
            models.add(adminClientInfModel);
        });
        return models;
    }

    public static cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel coverAskMoneyInfo(AskMoneyInfo user, UserClientInfo userClientInfo){
        cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel model =new cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel();
        model.setApplyMoney(user.getApplyMoney());
        model.setArrivalTime(user.getArrivalTime());
        model.setBankCard(user.getBankCard());
        model.setBankName(user.getBankName());
        model.setId(userClientInfo.getId());
        model.setPayAcount(user.getPayAcount());
        model.setPhone(userClientInfo.getPhone());
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

    public static List<cn.com.fintheircing.admin.UserManag.model.ContactInfoModel> coverContactInfo(List<contactInfo> info){
        List<cn.com.fintheircing.admin.UserManag.model.ContactInfoModel> models=null;
        cn.com.fintheircing.admin.UserManag.model.ContactInfoModel model=new cn.com.fintheircing.admin.UserManag.model.ContactInfoModel();
        info.forEach(user->{
            model.setId(user.getId());
            model.setAssureMoney(user.getAssureMoney());
            model.setAvailableCash(user.getAvailableCash());
            model.setBorrowDays(user.getBorrowMoney());
            model.setBorrowRate(user.getContactDetails().getBorrowRate());
            model.setBorrowMoney(user.getBorrowMoney());
            model.setBorrowTime(user.getBorrowTime());
            model.setContactAllMoney(user.getContactAllMoney());
            model.setContactAssets(user.getContactDetails().getContactAssets());
            model.setContactId(user.getContactId());
            model.setCreateTime(user.getCreateTime());
            model.setDangerLevel(user.getContactDetails().getDangerLevel());
            model.setDeficitAmount(user.getDeficitAmount());
            model.setDeficitLine(user.getDeficitLine());
            model.setDelegationOrigin(user.getContactDetails().getDelegationOrigin());
            model.setFirstRate(user.getContactDetails().getFirstRate());
            model.setFloatFilled(user.getFloatFilled());
            model.setGoodsType(user.getGoodsType());
            model.setRemainderDays(user.getContactDetails().getRemainderDays());
            model.setRemainderTime(user.getRemainderTime());
            model.setStatus(user.getStatus());
            model.setStockValue(user.getStockValue());
            model.setTaskStatus(user.getContactDetails().getTaskStatus());
            model.setTransactionCommission(user.getContactDetails().getTransactionCommission());
            model.setStockName(user.getContractStock().getStockName());
            model.setStockId(user.getContractStock().getStockId());
            model.setStockAccount(user.getContractStock().getStockAccount());
            model.setBuyPrice(user.getContractStock().getBuyPrice());
            model.setBuyTime(user.getContractStock().getBuyTime());
            model.setMarketPrice(user.getContractStock().getMarketPrice());
            model.setMarketValue(user.getContractStock().getMarketValue());
            models.add(model);
        });
        return models;
    }

    public static cn.com.fintheircing.admin.UserManag.model.UserClientInfoModel coverUserClientInfo(UserClientInfo oneByUuid) {
        cn.com.fintheircing.admin.UserManag.model.UserClientInfoModel model =new cn.com.fintheircing.admin.UserManag.model.UserClientInfoModel();
        model.setCer(oneByUuid.getCer());
        model.setSource(oneByUuid.getSource());
        model.setStatus(oneByUuid.getStatus());
        model.setPhone(oneByUuid.getPhone());
        model.setId(oneByUuid.getId());
        model.setUserId(oneByUuid.getUserId());
        model.setUserName(oneByUuid.getUserName());
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
