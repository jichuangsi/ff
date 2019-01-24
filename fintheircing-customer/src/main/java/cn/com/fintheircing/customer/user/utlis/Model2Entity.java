package cn.com.fintheircing.customer.user.utlis;

import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;

import java.util.Date;

public class Model2Entity {
    public static RecodeInfoPay CoverRecodInfoPayModel(RecodeInfoPayModel model){
        RecodeInfoPay r =new RecodeInfoPay();
        r.setCostCount(model.getCostCount());
        r.setRemark(model.getRemark());
        r.setUserId(model.getUserId());
        r.setWay(model.getWay());
        return r;
    }
    public static RecodeInfoPay UpdateRecodeInfoPayModel(RecodeInfoPayModel model){
        RecodeInfoPay r =new RecodeInfoPay();
        r.setAddCount(model.getAddCount());
        r.setCostCount(model.getCostCount());
        r.setRemark(model.getRemark());
        r.setStatus(1);
        r.setUserId(model.getUserId());
        r.setWay(model.getWay());
        r.setCreateTime(model.getCreateTime());
        r.setBusinessContractId(model.getBusinessContractId());
        r.setUpdateTime(new Date());
        return r;
    }
}
