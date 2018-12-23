package cn.com.fintheircing.customer.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    @Resource
    private Algorithm algorithm;
    @Value("${custom.token.claim}")
    private    String tokenClaim;
    @Value("${custom.token.alg}")
    private String alg;
    @Value("${custom.token.survival}")
    private long survival;

    public String createdToken(String user) throws IllegalArgumentException, UnsupportedEncodingException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("alg", alg);
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map)
                .withClaim(tokenClaim, user)
                .withExpiresAt(new Date(System.currentTimeMillis()+survival))
                .sign(algorithm);
        return token;
    }

    public boolean checkToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
