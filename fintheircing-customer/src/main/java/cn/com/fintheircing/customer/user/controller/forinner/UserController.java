package cn.com.fintheircing.customer.user.controller.forinner;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.service.feign.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("UserController")
public class UserController {
    private UserService userService;
    @ApiOperation(value = "获取指定用户信息", notes = "")
    @GetMapping("/getUserClientInfoByPhone")
    public UserClientInfo getUserClientInfoByPhone(@RequestParam(value = "userName") String userName){
        return  userService.getUserClientInfoByPhone(userName);
    }
}
