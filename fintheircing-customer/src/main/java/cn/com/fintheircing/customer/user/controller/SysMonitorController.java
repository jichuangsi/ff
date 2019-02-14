package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys")
@Api("配资客户登录controller")
public class SysMonitorController {


    @RequestMapping("/findAllUser")
    public List<UserTokenInfo> findAllUser(String id) {

        return null;
    }
}
