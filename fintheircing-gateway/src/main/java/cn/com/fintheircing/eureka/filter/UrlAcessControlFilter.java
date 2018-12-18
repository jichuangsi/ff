package cn.com.fintheircing.eureka.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import cn.com.fintheircing.eureka.constant.ResultCode;
import cn.com.fintheircing.eureka.util.CommonUtils;

@Component
public class UrlAcessControlFilter extends AbstractGatewayFilterFactory<Object> {
	
	@Value("${app.token.ingoreTokenUrls}")
	private String[] ingoreTokenUrls;

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
				
				//todo 
				//1.写一个serivce，系统启动时读取数据库关于角色与可访问url的配置，缓存到redis
				//2.使用这个service获取缓存好的url访问权限，根据token中获取的角色权限，判断能否访问该地址
				
				return chain.filter(exchange);
				
			}catch (Exception e) {
				return CommonUtils.buildResponse(exchange, ResultCode.SYS_ERROR, ResultCode.SYS_ERROR_MSG);
			}
		};
	}

}
