package cn.com.fintheircing.admin.account.utils;

import cn.com.fintheircing.admin.account.entity.AdminClientInfo;
import cn.com.fintheircing.admin.account.model.AdminModel;

public final class MappingEntity2ModelConverter {

    private MappingEntity2ModelConverter(){}

    public static AdminModel CONVERTERFORMADMINCLIENTINFO(AdminClientInfo info){
        AdminModel model = new AdminModel();
        model.setBornDay(info.getBornDay());
        model.setEmail(info.getEmail());
        model.setSex(info.getSex());
        model.setUserName(info.getUserName());
        model.setPhone(info.getPhone());
        return model;
    }
}
