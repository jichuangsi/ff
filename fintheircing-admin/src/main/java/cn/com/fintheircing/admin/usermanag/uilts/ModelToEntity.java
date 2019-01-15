package cn.com.fintheircing.admin.usermanag.uilts;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.usermanag.model.result.RecodeInfoPayModel;
import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import cn.com.fintheircing.admin.todotask.model.FeedbackModel;

import cn.com.fintheircing.admin.usermanag.entity.Bill;
import cn.com.fintheircing.admin.usermanag.entity.PayInfo;
import cn.com.fintheircing.admin.usermanag.model.result.BillResponseModel;

public class ModelToEntity {
    public static FeedbackModel coverTodoTaskInfo(TodoTaskInfo info){
        FeedbackModel feedbackModel=new FeedbackModel();
        feedbackModel.setTaskType(info.getTaskType());
        feedbackModel.setResult(ResultCode.SUCESS_MSG);
        return feedbackModel;
    }
//    public static  final AdminClientInfo cover2entity(AdminClientInfoModel Model){
//        AdminClientInfo admin = new AdminClientInfo();
//        admin.setUuid(Model.getId());
////        admin.setBossId(Model.);
////        admin.setStatus(Model.g);
//        admin.setUserName(Model.getUserName());
//        admin.setPhone(Model.getPhone());
//        //admin.setPosition(Model.getP);
//        admin.setProxyNum(Model.getProxyId());
//        //admin.setRemarks(Model.ge);
//        admin.setUserName(Model.getProxyName());
//        admin.setUuid(Model.());
//        admin.setCreatedTime(Model.getCreatedTime());
//        return  admin;
//    }
//    public static  final AskMoneyInfo coverAskMoneyInfo2entity(AskMoneyInfoModel Model){
//        AskMoneyInfo info = new AskMoneyInfo();
//        info.setApplyMoney(Model.getApplyMoney());
//        info.setArrivalTime(Model.getArrivalTime());
//        info.setBankCard(Model.getBankCard());
//        info.setId(Model.getId());
//        info.setBankName(Model.getBankName());
//        info.setPayAcount(Model.getPayAcount());
//
//        info.setRecharge(Model.getRecharge());
//        info.setTaskstatus(Model.getTaskStatus());
//        info.setStatus(Model.getStatus());
//        info.setUserId(Model.getUserId());
//
//        info.setPayFrom(Model.getPayFrom());
//        info.setTrulyMoney(Model.getTrulyMoney());
//        info.setRechargeTime(Model.getRechargeTime());
//        info.setApplyTime(Model.getApplyTime());
//        info.setRemark(Model.getRemark());
//        info.setPayWay(Model.getPayWay());
//        return  info;
//    }
    public static final Bill COVERBILLTOMODEL(BillResponseModel model){
        Bill b= new Bill();
        b.setOrderId(model.getOrderId());
        b.setPayStatus(model.getPayStatus());
        b.setTransAmount(model.getTransAmount());
        b.setTransTime(model.getTransTime());
        return b;
    }
    public static PayInfo CoverPayInfo(RecodeInfoPayModel model){
        PayInfo p =new PayInfo();
        p.setAddCount(model.getAmount());
        p.setCreateTime(model.getDate());
        p.setPhone(model.getPhone());
        p.setRemark(model.getRemark());
        p.setStatus(model.getStatus());
        p.setUserId(model.getUserId());
        p.setUserName(model.getUserName());
        p.setWay(model.getWay());
        return p;
    }
}
