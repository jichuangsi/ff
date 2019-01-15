package cn.com.fintheircing.admin.usermanag.uilts;

import cn.com.fintheircing.admin.common.entity.AdminClientInfo;

import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;

import cn.com.fintheircing.admin.usermanag.entity.AskMoneyInfo;
import cn.com.fintheircing.admin.usermanag.entity.Bill;
import cn.com.fintheircing.admin.usermanag.entity.PayInfo;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;

import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

public class EntityToModel {


    public static TodoTaskInfo CoverTodoTaskInfoModel(CreateRegTodoTaskModel model){
        TodoTaskInfo taskInfo =new TodoTaskInfo();
        taskInfo.setTaskInfoId(model.getRegisterUserId());
        taskInfo.setTaskType(model.getTaskType());
        taskInfo.setStatus(TodoTaskInfo.STATUS_FIN);
        return taskInfo;
    }



//    public static List<ContactInfoModel> coverContactInfo(List<ContactInfo> info){
//        List<cn.com.fintheircing.admin.usermanag.model.ContactInfoModel> models=null;
//        ContactInfoModel model=new ContactInfoModel();
//        info.forEach(user->{
//            model.setId(user.getId());
//            model.setAssureMoney(user.getAssureMoney());
//            model.setAvailableCash(user.getAvailableCash());
//            model.setBorrowDays(user.getBorrowMoney());
//            model.setBorrowMoney(user.getBorrowMoney());
//            model.setBorrowTime(user.getBorrowTime());
//            model.setContactAllMoney(user.getContactAllMoney());
//            model.setContactId(user.getContactId());
//            model.setCreateTime(user.getCreateTime());
//            model.setDeficitAmount(user.getDeficitAmount());
//            model.setDeficitLine(user.getDeficitLine());
//            model.setFloatFilled(user.getFloatFilled());
//            model.setGoodsType(user.getGoodsType());
//
//            model.setRemainderTime(user.getRemainderTime());
//            model.setStatus(user.getStatus());
//            model.setStockValue(user.getStockValue());
//            models.add(model);
//        });
//        return models;
//    }

    public static final BillResponseModel COVERBILLTOENTITY(Bill model){
        BillResponseModel b= new BillResponseModel();

        b.setOrderId(model.getOrderId());
        b.setPayStatus(model.getPayStatus());

        b.setTransAmount(model.getTransAmount());
        b.setTransTime(model.getTransTime());
        return b;
    }
    public static final RecodeInfoPayModel CoverPayInfo(PayInfo model){
        RecodeInfoPayModel p =new RecodeInfoPayModel();
        p.setCostCount(model.getCostCount());
        p.setAddCount(model.getAddCount());
        p.setCreatTime(model.getCreateTime());
        p.setPhone(model.getPhone());
        p.setRemark(model.getRemark());
        p.setStatus(model.getStatus());
        p.setUserId(model.getUserId());
        p.setUserName(model.getUserName());
        p.setWay(model.getWay());
        p.setPhone(model.getPhone());
        return p;
    }

}
