package cn.com.fintheircing.sms.utils;

import cn.com.fintheircing.sms.Commons.MesStatus;
import cn.com.fintheircing.sms.entity.Recoding;
import cn.com.fintheircing.sms.model.MesModel;

public class Model2Entity {
    public static Recoding CovermesModel(MesModel Model){
        Recoding recoding =new Recoding();
        recoding.setTaskType(Model.getTaskType());
        recoding.setIsSucess(MesStatus.getStatus(Model.getIsSucess()));
        recoding.setContent(Model.getContent());
        return recoding;
    }
}
