
package cn.com.fintheircing.customer.common.feign.controller;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 
 *
 */
@RestController
@Api("/对外开放的user")
@RequestMapping("/Cu")
public class UserInterfaceForInnerController {
    @Resource
    private IPayMapper iPayMapper;
    @Resource
    private LoginService loginService;
    @RequestMapping("/findAllPayInfo")
    public List<RecodeInfoPayModel> findAllPayInfo() {
       return iPayMapper.findAllPayInfo();
    }
    @RequestMapping("/viewOnline")
    public List<OnlineUserInfo> viewOnline(){
        return loginService.viewOnline();
    }
    @RequestMapping("/isOut")
    public ResponseModel isOut(String id)  {

            try {
                if (loginService.outOnline(id)) {
                    return ResponseModel.sucessWithEmptyData("");
                }
            } catch (Exception e) {
                return ResponseModel.fail("", e.getMessage());
            }

            return  null;
        }
}
