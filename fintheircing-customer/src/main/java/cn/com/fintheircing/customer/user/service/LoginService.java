package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.utils.Entity2Model;
import cn.com.fintheircing.customer.user.dao.repository.IBlackListRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserClientLoginInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
    private IBlackListRepository blackListRepository;
    @Resource
    private TokenService tokenService;
    @Resource
    private IUserClientLoginInfoRepository iUserClientLoginInfoRepository;
    @Value("${custom.token.prefix}")
    private String tokrnPre;
    @Value("${custom.token.longTime}")
    private long longTime;

    public Boolean isExistBlack(String ipAddress){
        return blackListRepository.countBlackListByIpAddress(ipAddress)>0;
    }

    public String userLogin(UserTokenInfo model) throws LoginException{
        UserTokenInfo user = registerService.getUserForLogin(model);
        if(user==null){
            throw new LoginException(ResultCode.LOGIN_USER_NOTEXIST);
        }
        if(UserClientInfo.STATUS_STOP.equals(user.getStatus())){
            throw new LoginException(ResultCode.LOGIN_USER_STOP);
        }
        String userJson = JSON.toJSONString(user);
        String token = "";
        try {
            token = tokenService.createdToken(userJson);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultCode.LOGIN_TOKEN_ERR);
        }
        if(StringUtils.isEmpty(token)) throw new LoginException(ResultCode.LOGIN_USER_ERR);
        redisTemplate.opsForValue().set(tokrnPre+user.getUuid(),token,longTime, TimeUnit.MINUTES);
        redisTemplate.opsForList().leftPush("1",user.getUuid());
        return token;
    }
    public  List<OnlineUserInfo> findAllOnline(){
        List<String> lists = redisTemplate.opsForList().range("1",0,-1);
        List<UserClientLoginInfo> list= new ArrayList<>();
        lists.forEach(i->{
            if (StringUtils.isEmpty(redisTemplate.opsForValue().get(i+tokrnPre))) {
                lists.remove(i);
            }
        });
        lists.forEach(id->{
            UserClientLoginInfo oneByUuid = iUserClientLoginInfoRepository.findOneByUuid(id);
            list.add(oneByUuid);
        });
       return Entity2Model.coverUserClientLoginInfo(list);
    }
    public boolean outLine(String id){
       return redisTemplate.delete(id+tokrnPre);
    }
    public List<OnlineUserInfo> findAllRecoding(String operating, String loginName){
       return registerService.findAllRecoding(operating,loginName);
    }
    public int deleteRecoding(String userId){
       return registerService.deleteRecoding(userId);
    }
}
