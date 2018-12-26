package cn.com.fintheircing.admin.common.initCoonfig;

import cn.com.fintheircing.admin.promisedUrls.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitCompoent  {

    @Resource
    private UrlService urlService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());
    @PostConstruct
    public void contextInitialized(){
        urlService.selectAllUrls();
        logger.debug("redis已加载路径数据");
    }
    @PostConstruct
    public void createList(){
        List<String> list =new ArrayList<>();
        redisTemplate.opsForValue().set("1",list);
    }
}
