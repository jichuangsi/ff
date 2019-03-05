package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RegisterService registerService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private TokenService tokenService;

    @Value("${custom.token.prefix}")
    private String tokrnPre;
    @Value("${custom.token.longTime}")
    private long longTime;
    @Value("${custom.token.appLongTime}")
    private long appLongTime;


    public String userLogin(UserTokenInfo model) throws LoginException {
        UserTokenInfo user = registerService.getUserForLogin(model);

        if (user == null) {
            throw new LoginException(ResultCode.LOGIN_USER_NOTEXIST);
        }
        if (UserClientInfo.STATUS_STOP.equals(user.getStatus())) {
            throw new LoginException(ResultCode.LOGIN_USER_STOP);
        }
        String userJson = JSON.toJSONString(user);
        String token = "";
        try {
            token = tokenService.createdToken(userJson);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultCode.LOGIN_TOKEN_ERR);
        }
        if (StringUtils.isEmpty(token)) throw new LoginException(ResultCode.LOGIN_USER_ERR);
        if ("app".equalsIgnoreCase(model.getApplyOn())) {
            redisTemplate.opsForValue().set(tokrnPre + user.getUuid(), token, appLongTime, TimeUnit.DAYS);
            redisTemplate.opsForValue().set(user.getUuid()+",", user.getUuid(), appLongTime, TimeUnit.DAYS);

        } else {
            redisTemplate.opsForValue().set(tokrnPre + user.getUuid(), token, longTime, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(user.getUuid()+",", user.getUuid(), longTime, TimeUnit.MINUTES);
        }

        return token;
    }

    public List<OnlineUserInfo> viewOnline() {
        List<OnlineUserInfo> list = new ArrayList<>();
        String[] ids =redisTemplate.opsForValue().get("0", 0, -1).split(",");
        for (String id : ids
        ) {
            OnlineUserInfo userForLogin = registerService.getUserForLogin(id);
            list.add(userForLogin);

        }

        return list;
    }

    public boolean outOnline(String id) throws LoginException{

        List<String> range = redisTemplate.opsForList().range("0", 0, -1);
        for (String r : range
        ) {
            if (id.equalsIgnoreCase(r)) {
                Long remove = redisTemplate.opsForList().remove("0", 1, id);
                if (remove<0){
                    throw new LoginException(ResultCode.LOGIN_OUT_FAILE);
                }
                if (!redisTemplate.delete(tokrnPre+id)){
                    throw new LoginException(ResultCode.FORCE_LOGIN_OUT_ERR);
                }
                return true;
            }else {
                throw new LoginException(ResultCode.LOGIN_OUT_ERR);
            }
        }
        return false;

    }
    public boolean outOnlineByUser(String id) throws LoginException{

        return redisTemplate.delete(tokrnPre+id);

}
}