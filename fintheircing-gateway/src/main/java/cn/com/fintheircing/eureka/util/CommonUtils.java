package cn.com.fintheircing.eureka.util;

import java.io.UnsupportedEncodingException;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;

import cn.com.fintheircing.eureka.model.ResponseModel;
import reactor.core.publisher.Mono;

public class CommonUtils {

	public static final Mono<Void> buildResponse(ServerWebExchange exchange, String code, String msg) {
		ServerHttpResponse response = exchange.getResponse();
		// 设置headers
		HttpHeaders httpHeaders = response.getHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");

		DataBuffer bodyDataBuffer;
		try {
			bodyDataBuffer = response.bufferFactory()
					.wrap(JSONObject.toJSONString(new ResponseModel(code, msg)).getBytes("UTF-8"));
			return response.writeWith(Mono.just(bodyDataBuffer));
		} catch (UnsupportedEncodingException e) {
			bodyDataBuffer = response.bufferFactory()
					.wrap(JSONObject.toJSONString(new ResponseModel(code, msg)).getBytes());
			return response.writeWith(Mono.just(bodyDataBuffer));
		}
	}

}
