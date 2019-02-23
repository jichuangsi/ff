package cn.com.fintheircing.admin.login.service;

import cn.com.fintheircing.admin.account.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.login.dao.mapper.IAdminClientLoginInfoMapper;
import cn.com.fintheircing.admin.login.exception.AdminLoginException;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AdminLoginService {
    @Value("${custom.admin.prefix}")
    private String tokrnPre;
    @Value("${custom.admin.longTime}")
    private long longTime;
    Logger logger = LoggerFactory.getLogger(getClass());


    @Resource
    private IAdminClientLoginInfoMapper adminClientLoginInfoMapper;
    @Resource
    private TokenService tokenService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IAdminClientLoginInfoMapper iAdminClientLoginInfoMapper;

    public String adminLogin(UserTokenInfo model) throws AdminLoginException {
        model = adminClientLoginInfoMapper.selectAdminLoginModel(changeAdmin(model));
        if (model == null) {
            throw new AdminLoginException(ResultCode.LOGIN_ADMIN_ERR);
        }
        if (StringUtils.isEmpty(model.getStatus()) ||
                AdminClientInfo.STATUS_NOTEXIST.equals(model.getStatus())) {
            throw new AdminLoginException(ResultCode.POWER_VISIT_ERR);
        }
        String userJson = JSON.toJSONString(model);
        String token = "";
        try {
            token = tokenService.createdToken(userJson);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultCode.LOGIN_TOKEN_ERR);
        }
        if (StringUtils.isEmpty(token)) throw new AdminLoginException(ResultCode.LOGIN_ADMIN_ERR);
        redisTemplate.opsForValue().set(tokrnPre + model.getUuid(), token, longTime, TimeUnit.MINUTES);

        return token;
    }

    private UserTokenInfo changeAdmin(UserTokenInfo model) {
        model.setPwd(CommonUtil.toSha256(model.getPwd()));
        return model;
    }

    public int countAdmin(UserTokenInfo userTokenInfo) {
        return adminClientLoginInfoMapper.selectCountAdmin(userTokenInfo);
    }

    public boolean loginout(String uuid) {
        if (redisTemplate.delete(tokrnPre + uuid)) {
            Map<String, Object> parms = new HashMap<>();
            Date d = new Date();
            parms.put("userId", uuid);
            parms.put("Date", d);
            iAdminClientLoginInfoMapper.updateLogoutTime(parms);
            return true;
        }
        return false;
    }
}
