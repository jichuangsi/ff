package cn.com.fintheircing.customer.user.utlis;

import cn.com.fintheircing.customer.common.constant.PayStatus;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;

import java.util.List;

public class Entity2Model {
    public static RecodeInfoPayModel CoverRecodInfoPay(RecodeInfoPay r){
        RecodeInfoPayModel model=new RecodeInfoPayModel();
        model.setUuid(r.getUuid());
        model.setWay(r.getWay());
        model.setUserId(r.getUserId());
        model.setRemark(r.getRemark());
        model.setAddCount(r.getAddCount());
        model.setCostCount(r.getCostCount());
        model.setCreateTime(r.getCreateTime());
        model.setStatus(PayStatus.getName(r.getStatus()));
        return  model;
    }
    public static List<RecodeInfoPayModel> CoverListRecodInfoPay(List<RecodeInfoPay> payList){
        List<RecodeInfoPayModel> models = null;
        payList.forEach(P->{
            models.add(CoverRecodInfoPay(P));
        });
        return models;
    }
    public static List<RecodeInfoPayModel> CoverListRecodeInfoPay(List<RecodeInfoPay> payList, UserTokenInfo user){
        List<RecodeInfoPayModel> models = null;
        payList.forEach(P->{
            models.add(CoverRecodInfoPay(P));
        });
        return models;
    }
}
