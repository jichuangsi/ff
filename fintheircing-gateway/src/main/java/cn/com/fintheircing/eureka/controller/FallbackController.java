package cn.com.fintheircing.eureka.controller;

import cn.com.fintheircing.eureka.constant.ResultCode;
import cn.com.fintheircing.eureka.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/hystrixTimeout")
	public Mono<ResponseModel> hystrixTimeout() {
		logger.error("触发了断路由");
		return Mono.just(new ResponseModel(ResultCode.SYS_BUSY, ResultCode.SYS_BUSY_MSG));
	}

}
