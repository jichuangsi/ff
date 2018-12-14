package cn.com.fintheircing.admin.todotask.service;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.common.utils.JWTCommonUtils;
import cn.com.fintheircing.admin.todotask.dao.mapper.IAdminClientInfoMapper;
import cn.com.fintheircing.admin.todotask.dao.mapper.IAdminClientLoginInfoMapper;
import cn.com.fintheircing.admin.todotask.dao.repository.IBlackListRepository;
import cn.com.fintheircing.admin.todotask.entity.AdminClientInfo;
import cn.com.fintheircing.admin.todotask.exception.AdminLoginException;
import cn.com.fintheircing.admin.todotask.model.AdminLoginModel;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;


@Service
public class AdminLoginService {
    @Value("${custom.admin.prefix}")
    private String tokrnPre;
    @Value("${custom.admin.longTime}")
    private long longTime;
    Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private IBlackListRepository blackListRepository;

    @Resource
    private IAdminClientInfoMapper adminClientInfoMapper;
    @Resource
    private IAdminClientLoginInfoMapper adminClientLoginInfoMapper;
    @Resource
    private JWTCommonUtils jwtCommonUtils;

    @Resource
    private RedisTemplate redisTemplate;


    @Transactional
    public Boolean isExistBlack(String ip){
        return blackListRepository.countBlackListByIpAddress(ip)>0;
    }


    @Transactional
    public String adminLogin( AdminLoginModel model) throws AdminLoginException{
        model = adminClientLoginInfoMapper.selectAdminLoginModel(changeAdmin(model));
        if(model==null){
            throw new AdminLoginException(ResultCode.Login_VALIDATE_ERR);
        }
        if(StringUtils.isEmpty(model.getStatus())||
                AdminClientInfo.STATUS_NOTEXIST.equals(model.getStatus())){
            throw new AdminLoginException(ResultCode.POWER_VISIT_ERR);
        }
        String userJson = JSON.toJSONString(model);
        String token = "";
        try {
            token = jwtCommonUtils.creatToken(userJson);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultCode.LOGIN_TOKEN_ERR);
        }
        if(StringUtils.isEmpty(token)) throw new AdminLoginException(ResultCode.LOGIN_ADMIN_ERR);
        redisTemplate.opsForValue().set(tokrnPre+model.getAdminName(),userJson,longTime, TimeUnit.MINUTES);
        return token;
    }

    private AdminLoginModel changeAdmin(AdminLoginModel model){
        model.setPwd(CommonUtil.toSha256(model.getPwd()));
        return model;
    }
}
