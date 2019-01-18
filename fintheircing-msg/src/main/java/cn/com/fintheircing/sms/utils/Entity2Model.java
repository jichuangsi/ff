package cn.com.fintheircing.sms.utils;

import cn.com.fintheircing.sms.entity.Recoding;
import cn.com.fintheircing.sms.model.MesModel;

public class Entity2Model {
    public  static MesModel coverRecoding(Recoding recoding){
        MesModel model =new MesModel();
       model.setIsSucess(recoding.getIsSucess().getName());
       model.setTaskType(recoding.getTaskType());
       model.setContent(recoding.getContent());
       model.setCreateTime(recoding.getCreateTime());
       return  model;
    }
}
