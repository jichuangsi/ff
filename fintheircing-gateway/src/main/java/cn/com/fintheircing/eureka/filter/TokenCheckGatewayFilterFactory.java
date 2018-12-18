package cn.com.fintheircing.eureka.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import cn.com.fintheircing.eureka.constant.ResultCode;
import cn.com.fintheircing.eureka.util.CommonUtils;

@Component
public class TokenCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${app.token.headerName}")
	private String headerName;

	@Value("${app.token.cache.expireAfterAccessWithMinutes}")
	private long expireAfterAccessWithMinutes;

	@Value("${app.token.ingoreTokenUrls}")
	private String[] ingoreTokenUrls;

	@Autowired
	private Algorithm tokenAlgorithm;

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
					final JWTVerifier verifier = JWT.require(tokenAlgorithm).build();
					verifier.verify(accessToken);// 校验有效性
					// todo 校验有效期
					// 如果在redis里面找不到，则看token中的过期时间是否超过，若超过，则返回过期，若没超过，则将token写入redis（设置超时时间）
					
					//在redis里面找到对应的token，则刷新在redis里缓存token的时间
					
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

}
