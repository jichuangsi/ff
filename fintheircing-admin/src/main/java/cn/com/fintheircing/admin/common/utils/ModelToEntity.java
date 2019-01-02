package cn.com.fintheircing.admin.common.utils;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.entity.AskMoneyInfo;
import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import cn.com.fintheircing.admin.todotask.model.FeedbackModel;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfModel;
import cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel;

public class ModelToEntity {
    public static FeedbackModel coverTodoTaskInfo(TodoTaskInfo info){
        FeedbackModel feedbackModel=new FeedbackModel();
        feedbackModel.setTaskType(info.getTaskType());
        feedbackModel.setResult(ResultCode.SUCESS_MSG);
        return feedbackModel;
    }
    public static  final AdminClientInfo cover2entity(AdminClientInfModel Model){
        AdminClientInfo admin = new AdminClientInfo();
        admin.setUuid(Model.getId());
//        admin.setBossId(Model.);
//        admin.setStatus(Model.g);
        admin.setUserName(Model.getUserName());
        admin.setPhone(Model.getPhone());
        //admin.setPosition(Model.getP);
        admin.setProxyNum(Model.getProxyId());
        //admin.setRemarks(Model.ge);
        admin.setUserName(Model.getProxyName());
        admin.setUuid(Model.getUserId());
        admin.setCreatedTime(Model.getCreatedTime());
        return  admin;
    }
    public static  final AskMoneyInfo coverAskMoneyInfo2entity(AskMoneyInfoModel Model){
        AskMoneyInfo info = new AskMoneyInfo();
        info.setApplyMoney(Model.getApplyMoney());
        info.setArrivalTime(Model.getArrivalTime());
        info.setBankCard(Model.getBankCard());
        info.setId(Model.getId());
        info.setBankName(Model.getBankName());
        info.setPayAcount(Model.getPayAcount());
        info.setPhone(Model.getPhone());
        info.setRecharge(Model.getRecharge());
        info.setTaskstatus(Model.getTaskStatus());
        info.setStatus(Model.getStatus());
        info.setUserId(Model.getUserId());
        info.setUserName(Model.getUserName());
        info.setPayFrom(Model.getPayFrom());
        info.setTrulyMoney(Model.getTrulyMoney());
        info.setRechargeTime(Model.getRechargeTime());
        info.setApplyTime(Model.getApplyTime());
        info.setRemark(Model.getRemark());
        info.setPayWay(Model.getPayWay());
        return  info;
    }

}
