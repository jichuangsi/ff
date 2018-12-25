package cn.com.fintheircing.admin.common.initCoonfig;

import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class InitCompoent  {

    @Resource
    private UrlService urlService;

    Logger logger = LoggerFactory.getLogger(getClass());
    @PostConstruct
    public void contextInitialized(){
        urlService.selectAllUrls();
        logger.debug("redis已加载路径数据");
    }
}
