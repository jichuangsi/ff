package cn.com.fintheircing.customer.common.utils;

import cn.com.fintheircing.customer.user.model.UserForLoginModel;
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

    public UserForLoginModel translateToken(String token) throws UnsupportedEncodingException {
        if (!StringUtils.isEmpty(token)) {
            DecodedJWT jwt = JWT.decode(token);
            String user = jwt.getClaim(tokenClaim).asString();
            //model.addAttribute(userClaim, JSONObject.parseObject(roles,UserInfoForToken.class));
            return JSONObject.parseObject(user,UserForLoginModel.class);
        }
        return null;
    }

    public Boolean validateUser(UserForLoginModel userInfo,String token)  throws UnsupportedEncodingException {
        UserForLoginModel oldUser = translateToken(token);
        if(oldUser.getLoginName().equals(userInfo.getLoginName())
                &&oldUser.getDisplayname().equals(userInfo.getDisplayname())&&
                oldUser.getPhone().equals(userInfo.getPhone())&&
                oldUser.getPwd().equals(userInfo.getPwd())&&
                oldUser.getStatus().equals(userInfo.getStatus())){
            return true;
        }
        return false;
    }
}
