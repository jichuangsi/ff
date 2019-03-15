package cn.com.fintheircing.customer.user.utlis;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;

public class MappingEntity2ModelConverter {

    private MappingEntity2ModelConverter(){}

    public static UserTokenInfo CONVERTERFROMUSERCLIENTINFO(UserClientInfo clientInfo){
        UserTokenInfo info = new UserTokenInfo();
        info.setUserName(clientInfo.getUserName());
        info.setRoleGrade(clientInfo.getRoleGrade());
        info.setUuid(clientInfo.getUuid());
        info.setDisplayname(clientInfo.getDisplayname());
        info.setPhone(clientInfo.getPhone());
        info.setStatus(clientInfo.getStatus());
        return info;
    }
}
