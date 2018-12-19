package cn.com.fintheircing.admin.common.utils;

import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

@Component
public class TokenValidate {

    @Value("${custom.token.claim}")
    private    String tokenClaim;

    public UserTokenInfo translateToken(String token) throws UnsupportedEncodingException {
        if (!StringUtils.isEmpty(token)) {
            DecodedJWT jwt = JWT.decode(token);
            String user = jwt.getClaim(tokenClaim).asString();
            //model.addAttribute(userClaim, JSONObject.parseObject(roles,UserInfoForToken.class));
            return JSONObject.parseObject(user,UserTokenInfo.class);
        }
        return null;
    }
}
