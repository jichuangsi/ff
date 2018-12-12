package cn.com.fintheircing.customer.user.controller.advice;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler
	public ResponseModel<Object> handler(Exception e) {
		                 
		if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
			ResponseModel<Object> responseModel = new ResponseModel<Object>();
			responseModel.setCode(ResultCode.PARAM_ERR);
			responseModel.setMsg(ex.getBindingResult().getFieldError().getDefaultMessage());
			return responseModel;
		}
		logger.error(e.getCause() + ":" + e.getMessage());
		return ResponseModel.fail("", e.getMessage());

	}

}
