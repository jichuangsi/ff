
package cn.com.fintheircing.customer.common.feign.controller;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
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
    @RequestMapping("/findAllPayInfo")
    public List<RecodeInfoPayModel> findAllPayInfo() {
       return iPayMapper.findAllPayInfo();
    }

}
