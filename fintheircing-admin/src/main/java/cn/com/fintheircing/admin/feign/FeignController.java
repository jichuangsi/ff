package cn.com.fintheircing.admin.feign;

import cn.com.fintheircing.admin.login.service.AdminLoginService;
import cn.com.fintheircing.admin.promisedUrls.model.TranferUrlModel;
import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api("开放feign")
@RequestMapping("/adminF")
public class FeignController {


    @Resource
    private UrlService urlService;

    @Resource
    private AdminLoginService adminLoginService;

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
}
