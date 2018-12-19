package cn.com.fintheircing.customer.advice;

import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;

@RestControllerAdvice
public class CommonControllerAdvice {

	@Value("${custom.token.claim}")
	private String userClaim;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public UserTokenInfo translateHeader(@RequestHeader @Nullable String userInfo,
										 @RequestHeader @Nullable String accessToken, Model model) throws UnsupportedEncodingException {
		if (!StringUtils.isEmpty(accessToken)) {
			DecodedJWT jwt = JWT.decode(accessToken);
			String user = jwt.getClaim(userClaim).asString();
			// model.addAttribute(userClaim,
			// JSONObject.parseObject(roles,UserInfoForToken.class));
			return JSONObject.parseObject(user, UserTokenInfo.class);
		}
		return null;
	}

	@ExceptionHandler
	public ResponseModel<Object> handler(Exception e) {
		logger.error(e.getCause() + ":" + e.getMessage());
		return ResponseModel.fail("", e.getMessage());

	}

}
