package cn.com.fintheircing.admin.proxy.utils;

import cn.com.fintheircing.admin.account.entity.AdminClientInfo;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

public final class MappingModel2EntityConverter {

    private MappingModel2EntityConverter(){}

    public static  final AdminClientInfo CONVERTERFORPROXYMODEL(ProxyModel proxyModel){
        AdminClientInfo admin = new AdminClientInfo();
        admin.setUuid(StringUtils.isEmpty(proxyModel.getProxyId())?
                UUID.randomUUID().toString().replace("-",""):proxyModel.getProxyId());  //id
        admin.setPhone(proxyModel.getLinkPhone());  //联系电话
        admin.setName(proxyModel.getLinkMan());   //联系人
       admin.setRoleGrade(proxyModel.getRoleGrade());  //角色
        admin.setStatus(proxyModel.getStatus());   //状态
        admin.setUserName(proxyModel.getProxyName());   //代理名称
        admin.setProxyNum(proxyModel.getProxyNum());   //代理代码
        admin.setRemarks(proxyModel.getRemarks());  //备注
        admin.setCreatedTime(proxyModel.getCreatedTime()==null
                ?new Date():proxyModel.getCreatedTime());
        admin.setUpdatedTime(new Date());
        return  admin;
    }
}
