package cn.com.fintheircing.customer.common.initConfig;

import cn.com.fintheircing.customer.user.service.feign.ITodoTaskService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class initComponent {

    @Resource
    private ITodoTaskService todoTaskService;

   /* @PostConstruct
    public void getRole(){
        List<RoleModel> roles = todoTaskService.getRoles();
        for (RoleModel role :roles){
            RoleCodes.ROLE_KEY_INTEGER.put(role.getPosition(),role.getSign());
            RoleCodes.ROLE_KEY_STRING.put(role.getSign(),role.getPosition());
        }
    }*/
}
