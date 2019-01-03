package cn.com.fintheircing.admin.common.initConfig;

import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import cn.com.fintheircing.admin.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class InitComponent {

    @Resource
    private UrlService urlService;
    @Resource
    private SystemService systemService;

    Logger logger = LoggerFactory.getLogger(getClass());


    @PostConstruct
    public void contextInitialized(){
        urlService.selectAllUrls();
        logger.debug("redis已加载路径数据");
    }

    @PostConstruct
    public void saveRoles(){
        systemService.saveRoles();
        logger.debug("加载角色完毕");
    }
}
