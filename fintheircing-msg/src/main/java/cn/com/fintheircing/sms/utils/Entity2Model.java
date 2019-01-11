package cn.com.fintheircing.sms.utils;

import cn.com.fintheircing.sms.entity.Recoding;
import cn.com.fintheircing.sms.model.mesModel;

public class Entity2Model {
    public  static mesModel coverRecoding(Recoding recoding){
        mesModel model =new mesModel();
       model.setIsSucess(recoding.getIsSucess());
       model.setPhone(recoding.getPhone());
       model.setTaskType(recoding.getTaskType());
       model.setContent(recoding.getContent());
       model.setUuid(recoding.getUuid());
       model.setCreatedTime(recoding.getCreatedTime());
       return  model;
    }
}
