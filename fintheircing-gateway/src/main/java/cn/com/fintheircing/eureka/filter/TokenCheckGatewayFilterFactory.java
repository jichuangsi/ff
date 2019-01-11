package cn.com.fintheircing.eureka.filter;

import cn.com.fintheircing.eureka.constant.ResultCode;
import cn.com.fintheircing.eureka.model.UserTokenInfo;
import cn.com.fintheircing.eureka.util.CommonUtils;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class TokenCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${app.token.headerName}")
	private String headerName;

	@Value("${app.token.cache.expireAfterAccessWithMinutes}")
	private long expireAfterAccessWithMinutes;

	@Value("${app.token.ingoreTokenUrls}")
	private String[] ingoreTokenUrls;

	@Value("${custom.token.prefix}")
	private String tokrnPre;
	@Value("${custom.token.userClaim}")
	private String userClaim;
	@Value("${custom.token.dieTime}")
	private long dieTime;

	@Autowired
	private Algorithm tokenAlgorithm;

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public final GatewayFilter apply(Object config) {

		return (exchange, chain) -> {
			try {
				final ServerHttpRequest request = exchange.getRequest();

				final String url = request.getURI().getPath();
				if (null != ingoreTokenUrls && ingoreTokenUrls.length > 0) {
					for (String ingoreUrl : ingoreTokenUrls) {
						if (ingoreUrl.equals(url) || url.startsWith(ingoreUrl)) {// 对免检查token的url放行
							return chain.filter(exchange);
						}
					}
				}

				final String accessToken = request.getHeaders().getFirst(headerName);
				if (!StringUtils.isEmpty(accessToken)) {
					/*final JWTVerifier verifier = JWT.require(tokenAlgorithm).build();
					*//**
					 * 这个验证将过期token也打回去了
					 *//*
					verifier.verify(accessToken);// 校验有效性*/
					// todo 校验有效期
					// 如果在redis里面找不到，则看token中的过期时间是否超过，若超过，则返回过期，若没超过，则将token写入redis（设置超时时间）
					if(!redisTemplate.hasKey(getTokenKey(accessToken))){
						if(checkToken(accessToken)){
							redisTemplate.opsForValue().set(getTokenKey(accessToken),accessToken,dieTime,TimeUnit.MINUTES);
						}else{
							return  CommonUtils.buildResponse(exchange, ResultCode.TOKEN_CHECK_ERR, ResultCode.TOKEN_OVER_MSG);
						}
					}
					//在redis里面找到对应的token，则刷新在redis里缓存token的时间
					else{
						if(accessToken.equals(redisTemplate.opsForValue().get(getTokenKey(accessToken)))){
							redisTemplate.expire(getTokenKey(accessToken),dieTime, TimeUnit.MINUTES);
						}else{
							return CommonUtils.buildResponse(exchange,ResultCode.TOKEN_CHECK_ERR,ResultCode.TOKEN_CHECK_ERR_MSG);
						}
					}
					return chain.filter(exchange);
				} else {
					return CommonUtils.buildResponse(exchange, ResultCode.TOKEN_MISS, ResultCode.TOKEN_MISS_MSG);
				}
			} catch (JWTVerificationException e) {
				logger.error("token检验不通过：" + e.getMessage());
				return CommonUtils.buildResponse(exchange, ResultCode.TOKEN_CHECK_ERR, ResultCode.TOKEN_CHECK_ERR_MSG);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return CommonUtils.buildResponse(exchange, ResultCode.SYS_ERROR, ResultCode.SYS_ERROR_MSG);
			}

		};
	}


	private  String getTokenKey(String token){
		if (!StringUtils.isEmpty(token)) {
			DecodedJWT jwt = JWT.decode(token);
			String user = jwt.getClaim(userClaim).asString();
			// model.addAttribute(userClaim,
			// JSONObject.parseObject(roles,UserInfoForToken.class));
			UserTokenInfo userTokenInfo = JSONObject.parseObject(user, UserTokenInfo.class);
			return tokrnPre+userTokenInfo.getUuid();
		}else
			return null;
	}

	private boolean checkToken(String token) {
		JWTVerifier verifier = JWT.require(tokenAlgorithm).build();
		try {
			verifier.verify(token);
			return true;
		} catch (JWTVerificationException e) {
			e.printStackTrace();
			return false;
		}

	}

}
