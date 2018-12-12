package cn.com.fintheircing.customer.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Component
public  class JWTCommonUtils {
    @Value("${custom.token.claim}")
    private    String tokenClaim;
    @Value("${custom.token.HMAC256}")
    private  String secret;



    public  String creatToken(String user) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Map<String, Object> map = Maps.newHashMap();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map)
                .withClaim(tokenClaim, user)
                .withExpiresAt(new Date(System.currentTimeMillis()+360000))
                .sign(algorithm);
        return token;
    }

    //验证jwt
    public   DecodedJWT verifyJwt(String token){
        DecodedJWT decodedJWT = null;
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            decodedJWT = jwtVerifier.verify(token);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch(JWTVerificationException e) {
            e.printStackTrace();
        }
        return decodedJWT;
    }

}
