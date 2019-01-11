package cn.com.fintheircing.sms.utils;

import cn.com.fintheircing.sms.entity.Recoding;
import cn.com.fintheircing.sms.model.mesModel;

public class Model2Entity {
    public static Recoding CovermesModel(mesModel Model){
        Recoding recoding =new Recoding();
        recoding.setUuid(Model.getUuid());
        recoding.setCreatedTime(Model.getCreatedTime());
        recoding.setPhone(Model.getPhone());
        recoding.setTaskType(Model.getTaskType());
        recoding.setIsSucess(Model.getIsSucess());
        recoding.setContent(Model.getContent());
        return recoding;
    }
}
