package cn.com.fintheircing.customer.common.constant;

import cn.com.fintheircing.customer.common.model.RoleModel;
import cn.com.fintheircing.customer.user.service.feign.ITodoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public  class RoleCodes {

    public static  Map<Integer,String> ROLE_KEY_INTEGER = new HashMap<>();

    public static  Map<String,Integer> ROLE_KEY_STRING = new HashMap<>();

    private static ITodoTaskService systemService;
    @Autowired
    public void setSystemService(ITodoTaskService systemService) {
        RoleCodes.systemService = systemService;
        List<RoleModel> roles = systemService.getRoles();
        for (RoleModel role :roles){
            ROLE_KEY_INTEGER.put(role.getPosition(),role.getSign());
            ROLE_KEY_STRING.put(role.getSign(),role.getPosition());
        }
    }



}
