package cn.com.fintheircing.admin.feign;

import cn.com.fintheircing.admin.common.model.RoleModel;
import cn.com.fintheircing.admin.login.service.AdminLoginService;
import cn.com.fintheircing.admin.promisedUrls.model.TranferUrlModel;
import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import cn.com.fintheircing.admin.system.service.SystemService;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("开放feign")
@RequestMapping("/adminF")
public class FeignController {


    @Resource
    private UrlService urlService;

    @Resource
    private AdminLoginService adminLoginService;
    @Resource
    private SystemService systemService;
    @Resource
    private IDistributService distributService;

    @ApiOperation(value = "判断是否是允许的url", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/IsPromisedUrls")
    public Boolean  IsPromisedUrls(@RequestBody TranferUrlModel model){
        if (StringUtils.isEmpty(model.getUrl())){
            return false;
        }
        return urlService.checkUrl(model);
    }



    @ApiOperation(value = "判断是否IP存在黑名单", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/isExistBlackList")
    public Boolean isExistBlackList(@RequestParam String ip){
        return adminLoginService.isExistBlackList(ip);
    }

    @ApiOperation(value = "传递role角色", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getRoles")
    public List<RoleModel> getRoles(){
        return systemService.getRoles();
    }

    @ApiOperation(value = "传递product信息", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/getProduct")
    public ProductModel getProduct(@RequestBody ProductModel model){
        return distributService.getProduct(model);
    }


    @ApiOperation(value = "获取邀请人id", notes = "")
    @ApiImplicitParams({})
    @RequestMapping("/getInvitId")
    public String getInvitId(@RequestParam("invitId") String invitId){
        return null;
    }

}
