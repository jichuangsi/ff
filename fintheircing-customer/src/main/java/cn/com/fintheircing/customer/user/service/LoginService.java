package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.utils.JWTCommonUtils;
import cn.com.fintheircing.customer.user.dao.repository.IBlackListRepository;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.UserForLoginModel;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RegisterService registerService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private IBlackListRepository blackListRepository;
    @Resource
    private JWTCommonUtils jwtCommonUtils;

    @Value("${custom.token.prefix}")
    private String tokrnPre;
    @Value("${custom.token.longTime}")
    private long longTime;

    public Boolean isExistBlack(String ipAddress){
        return blackListRepository.countBlackListByIpAddress(ipAddress)>0;
    }

    public String userLogin(UserForLoginModel model,String oldtoken) throws LoginException{
        UserForLoginModel user = registerService.getUserForLogin(model);
        if(user==null){
            throw new LoginException(ResultCode.LOGIN_USER_NOTEXIST);
        }
        if(UserClientInfo.STATUS_STOP.equals(user.getStatus())){
            throw new LoginException(ResultCode.LOGIN_USER_STOP);
        }
        String userJson = JSON.toJSONString(user);
        String token = "";
        try {
            token = jwtCommonUtils.creatToken(userJson);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultCode.LOGIN_TOKEN_ERR);
        }
        if(StringUtils.isEmpty(token)) throw new LoginException(ResultCode.LOGIN_USER_ERR);
        redisTemplate.opsForValue().set(tokrnPre+user.getLoginName(),userJson,longTime, TimeUnit.MINUTES);
        return token;
    }
}
