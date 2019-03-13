package cn.com.fintheircing.admin.common.initConfig;

import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import cn.com.fintheircing.admin.proxy.service.ProxyService;
import cn.com.fintheircing.admin.risk.service.RiskService;
import cn.com.fintheircing.admin.system.service.SystemService;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class InitComponent {

    @Resource
    private UrlService urlService;
    @Resource
    private SystemService systemService;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private ProxyService proxyService;
    @Resource
    private IDistributService distributService;
    @Resource
    private RiskService riskService;

    @Value("${custom.system.autoBuy}")
    private String autoBuy;

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

    @PostConstruct
    public void saveAutoBuy(){
        redisTemplate.opsForValue().set(autoBuy,"true");//关闭自动购买
    }

    @PostConstruct
    public void saveAdmin(){    //保存管理员
        try {
            proxyService.saveAdmin();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @PostConstruct
    public void saveProduct(){  //保存产品
        try {
            distributService.saveProduct();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @PostConstruct
    public void saveSystemRisk(){
        try {
            riskService.saveSystemRisk();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
