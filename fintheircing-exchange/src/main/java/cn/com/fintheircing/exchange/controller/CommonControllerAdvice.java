package cn.com.fintheircing.exchange.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.com.fintheircing.exchange.constant.ResultCode;
import cn.com.fintheircing.exchange.model.ResponseModel;

@RestControllerAdvice
public class CommonControllerAdvice {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler
	public ResponseModel<Object> handler(Exception e) {
		if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
			ResponseModel<Object> responseModel = new ResponseModel<>("", ResultCode.PARAM_ERR,
					ex.getBindingResult().getFieldError().getDefaultMessage(), null);
			return responseModel;
		}

		logger.error(e.getCause() + ":" + e.getMessage());
		return ResponseModel.fail("", e.getMessage());

	}

}
