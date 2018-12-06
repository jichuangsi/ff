/**
 * 客户注册controller
 */
package cn.com.fintheircing.customer.user.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.fintheircing.customer.common.CommonUtil;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.exception.RegisterheckExistExcption;
import cn.com.fintheircing.customer.user.model.RegisterModel;
import cn.com.fintheircing.customer.user.model.UserForLoginModel;
import cn.com.fintheircing.customer.user.service.RegisterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserRegisterController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private RegisterService registerService;

	@ApiOperation(value = "短信获取注册验证码", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "phoneNo", value = "手机号", required = true, dataType = "String") })
	@GetMapping("/{phoneNo}")
	public ResponseModel<String> getRegisterValCode(@PathVariable String phoneNo) {

		if (StringUtils.isEmpty(phoneNo) || !CommonUtil.isPhone(phoneNo)) {
			return ResponseModel.fail("", "手机号码不正确");
		}
		
		try {
			registerService.getValCode(phoneNo);
		} catch (RegisterheckExistExcption e) {
			return ResponseModel.fail("", e.getMessage());
		}

		return ResponseModel.sucessWithEmptyData("");
	}
	
	@ApiOperation(value = "注册", notes = "")
	@ApiImplicitParams({
			})
	@PostMapping("/register")
	public ResponseModel<String> register(@Validated @RequestBody RegisterModel registerModel) {
		
		try {
			registerService.register(registerModel);
		} catch (RegisterheckExistExcption e) {
			return ResponseModel.fail("", e.getMessage());
		}
		return ResponseModel.sucessWithEmptyData("");
	}
	
	@ApiOperation(value = "登录", notes = "")
	@ApiImplicitParams({
			})
	@PostMapping("/login")
	public ResponseModel<String> login(@Validated @RequestBody UserForLoginModel model) {
		registerService.getUserForLogin(model);
		logger.debug("user Info:"+model);
		return ResponseModel.sucessWithEmptyData("");
	}
	
	
}
