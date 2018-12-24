package cn.com.fintheircing.eureka.filter;

import cn.com.fintheircing.eureka.model.TranferUrlModel;
import cn.com.fintheircing.eureka.model.UserTokenInfo;
import cn.com.fintheircing.eureka.service.IAdminFeignService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import cn.com.fintheircing.eureka.constant.ResultCode;
import cn.com.fintheircing.eureka.util.CommonUtils;

import javax.annotation.Resource;

@Component
public class UrlAcessControlFilterFactory extends AbstractGatewayFilterFactory<Object> {
	
	@Value("${app.token.ingoreTokenUrls}")
	private String[] ingoreTokenUrls;
	@Value("${app.token.headerName}")
	private String headerName;


	@Resource
	private IAdminFeignService adminFeignService;

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
				final String accessToken = request.getHeaders().getFirst(headerName);
				UserTokenInfo userInfo = JSONObject.parseObject(accessToken,UserTokenInfo.class);
				TranferUrlModel model = new TranferUrlModel();
				model.setPosition(userInfo.getPosition());
				model.setRole(userInfo.getRole());
				model.setUrl(url);
				if (!adminFeignService.checkPromisedUrl(model)){
					return CommonUtils.buildResponse(exchange, ResultCode.SYS_ERROR, ResultCode.POWER_VISIT_MSG);
				}
				return chain.filter(exchange);
				
			}catch (Exception e) {
				return CommonUtils.buildResponse(exchange, ResultCode.SYS_ERROR, ResultCode.SYS_ERROR_MSG);
			}
		};
	}

}
