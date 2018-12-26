/**
 * 
 */
package cn.com.fintheircing.customer.user.controller.forinner;

import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 
 *
 */
@RestController
@Api("UserInterfaceForInnerController外键")
public class UserInterfaceForInnerController {
    @Resource
    private LoginService loginService;
    @RequestMapping("/findAllOnline")
    @ApiOperation(value = "查询所有在线用户", notes = "")
    public List<OnlineUserInfo> findAllOnline() throws LoginException {
        return loginService.findAllOnline();
    }
    @RequestMapping("/outLine")
    @ApiOperation(value = "根据Id强制登出", notes = "")
    public boolean outLine(String id) throws LoginException {
        return loginService.outLine(id);
    }

    @RequestMapping("/findAllRecoding")
    @ApiOperation(value = "查询所有记录", notes = "")
    public List<OnlineUserInfo> findAllRecoding(String operating, String loginName) throws LoginException {
        return loginService.findAllRecoding(operating, loginName);
    }
    @RequestMapping("/deleteRecoding")
    @ApiOperation(value = "删除用户记录", notes = "")
    public int deleteRecoding(String userId) throws LoginException {
        return loginService.deleteRecoding(userId);
    }

}
