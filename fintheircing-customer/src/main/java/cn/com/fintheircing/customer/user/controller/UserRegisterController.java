/**
 * 客户注册controller
 */
package cn.com.fintheircing.customer.user.controller;

import cn.com.fintheircing.customer.common.utils.CommonUtil;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.exception.RegisterheckExistException;
import cn.com.fintheircing.customer.user.model.RegisterModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.service.RegisterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
	@CrossOrigin
	public ResponseModel<String> getRegisterValCode(@PathVariable String phoneNo) {

		if (StringUtils.isEmpty(phoneNo) || !CommonUtil.isPhone(phoneNo)) {
			return ResponseModel.fail("", "手机号码不正确");
		}
		
		try {
			registerService.getValCode(phoneNo);
		} catch (RegisterheckExistException e) {
			return ResponseModel.fail("", e.getMessage());
		}

		return ResponseModel.sucessWithEmptyData("");
	}
	
	@ApiOperation(value = "注册", notes = "")
	@ApiImplicitParams({
			})
	@PostMapping("/register")
	@CrossOrigin
	public ResponseModel<String> register(@Validated @RequestBody RegisterModel registerModel) {
		
		try {
			registerService.register(registerModel);
		} catch (RegisterheckExistException e) {
			return ResponseModel.fail("", e.getMessage());
		}
		return ResponseModel.sucessWithEmptyData("");
	}
	
	@ApiOperation(value = "登录", notes = "")
	@ApiImplicitParams({
			})
	@PostMapping("/login")
	public ResponseModel<String> login(@Validated @RequestBody UserTokenInfo model) {
		registerService.getUserForLogin(model);
		logger.debug("user Info:"+model);
		return ResponseModel.sucessWithEmptyData("");
	}
	
	
}
