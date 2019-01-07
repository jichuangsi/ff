package cn.com.fintheircing.customer.common.constant;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public  class RoleCodes {

    public static  Map<Integer,String> ROLE_KEY_INTEGER = new HashMap<>();

    public static  Map<String,Integer> ROLE_KEY_STRING = new HashMap<>();

    /*private static IAdminFeignService adminFeignService;*/
   /* @Autowired
    public void setSystemService(ITodoTaskService todoTaskService) {
        RoleCodes.todoTaskService = todoTaskService;
        List<RoleModel> roles = todoTaskService.getRoles();
        for (RoleModel role :roles){
            ROLE_KEY_INTEGER.put(role.getPosition(),role.getSign());
            ROLE_KEY_STRING.put(role.getSign(),role.getPosition());
        }
    }*/



}
