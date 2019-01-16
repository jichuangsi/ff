package cn.com.fintheircing.customer.user.utlis;

import cn.com.fintheircing.customer.common.constant.PayStatus;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodInfoPayModel;

import java.util.Date;

public class Model2Entity {
    public static RecodeInfoPay CoverRecodInfoPayModel(RecodInfoPayModel model){
        RecodeInfoPay r =new RecodeInfoPay();
        r.setCostCount("");
        r.setRemark(model.getRemark());
        r.setUserId(model.getUserId());
        r.setWay(model.getWay());
        return r;
    }
    public static RecodeInfoPay UpdateRecodeInfoPayModel(RecodInfoPayModel model){
        RecodeInfoPay r =new RecodeInfoPay();
        r.setAddCount(model.getAddCount());
        r.setCostCount(model.getCostCount());
        r.setRemark(model.getRemark());
        r.setStatus(1);
        r.setUserId(model.getUserId());
        r.setWay(model.getWay());
        r.setCreateTime(model.getCreateTime());
        r.setUpdateTime(new Date());
        return r;
    }
}
