package cn.com.fintheircing.customer.user.utlis;

import cn.com.fintheircing.customer.common.constant.PayStatus;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodInfoPayModel;

import java.util.List;

public class Entity2Model {
    public static RecodInfoPayModel CoverRecodInfoPay(RecodeInfoPay r){
        RecodInfoPayModel model=new RecodInfoPayModel();
        model.setUuid(r.getUuid());
        model.setWay(r.getWay());
        model.setUserId(r.getUserId());
        model.setRemark(r.getRemark());
        model.setAddCount(r.getAddCount());
        model.setCreateTime(r.getCreateTime());
        model.setStatus(PayStatus.getName(r.getStatus()));
        return  model;
    }
    public static List<RecodInfoPayModel> CoverListRecodInfoPay(List<RecodeInfoPay> payList){
        List<RecodInfoPayModel> models = null;
        payList.forEach(P->{
            models.add(CoverRecodInfoPay(P));
        });
        return models;
    }
    public static List<RecodInfoPayModel> CoverListRecodeInfoPay(List<RecodeInfoPay> payList, UserTokenInfo user){
        List<RecodInfoPayModel> models = null;
        payList.forEach(P->{
            models.add(CoverRecodInfoPay(P));
        });
        return models;
    }
}
