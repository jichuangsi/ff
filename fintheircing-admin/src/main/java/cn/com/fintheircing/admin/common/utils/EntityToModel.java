package cn.com.fintheircing.admin.common.utils;

import cn.com.fintheircing.admin.common.constant.AccountStatus;
import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import cn.com.fintheircing.admin.topLevel.entity.ParentAccount;
import cn.com.fintheircing.admin.topLevel.model.ParentAccountModel;
import cn.com.fintheircing.admin.usermanag.entity.Bill;
import cn.com.fintheircing.admin.usermanag.entity.pay.PayInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.result.BillResponseModel;

public class EntityToModel {
  public static ParentAccountModel CoverParentAccount(ParentAccount model){
      ParentAccountModel p =new ParentAccountModel();
      p.setTradeAccount(model.getTradeAccount());
      p.setPassWord(model.getPassWord());
      p.setJyPassword(model.getJyPassword());
      p.setTxPassword(model.getTxPassword());
      p.setAccountType(model.getAccountType());
      p.setTradeName(model.getTradeName());
      p.setBeginAmount(model.getBeginAmount());
      p.setPromiseAmount(model.getPromiseAmount());
      p.setLoanAmount(model.getLoanAmount());
      p.setCreateTime(model.getCreateTime());
      p.setAmount(model.getAmount());
      p.setTradeCode(model.getTradeCode());
      p.setQsId(model.getQsId());
      p.setIp(model.getIp());
      p.setPort(model.getPort());
      p.setVersion(model.getVersion());
      p.setYybId(model.getYybId());
      p.setAccountNo(model.getAccountNo());
      p.setSzAccout(model.getSzAccout());
      p.setShAccout(model.getShAccout());
      p.setSecurities(model.getSecurities());
      p.setStatus(AccountStatus.getName(model.getStatus()));
      return p;
  }
    public static TodoTaskInfo CoverTodoTaskInfoModel(CreateRegTodoTaskModel model){
        TodoTaskInfo taskInfo =new TodoTaskInfo();
        taskInfo.setTaskInfoId(model.getRegisterUserId());
        taskInfo.setTaskType(model.getTaskType());
        taskInfo.setStatus(TodoTaskInfo.STATUS_FIN);
        return taskInfo;
    }


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
        p.setRemark(model.getRemark());
        p.setUserId(model.getUserId());
        p.setWay(model.getWay());
        return p;
    }

}
