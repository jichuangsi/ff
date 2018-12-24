package cn.com.fintheircing.admin.promisedUrls.controller;

import cn.com.fintheircing.admin.promisedUrls.model.TranferUrlModel;
import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api("开放feign")
public class FeignController {


    @Resource
    private UrlService urlService;

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
}
