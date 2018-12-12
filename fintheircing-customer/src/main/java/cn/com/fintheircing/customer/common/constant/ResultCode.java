package cn.com.fintheircing.customer.common.constant;

public class ResultCode {
	public static final String SUCESS = "0010";//成功
	public static final String PARAM_MISS = "0020";//缺少参数
	public static final String PARAM_ERR = "0021";//参数不正确
	public static final String TOKEN_MISS = "0030";//缺少token
	public static final String TOKEN_CHECK_ERR = "0031";//token校验异常
	public static final String SYS_ERROR = "0050";//系统内部异常
	public static final String SYS_BUSY = "0051";//熔断
	
	public static final String SUCESS_MSG = "成功";
	public static final String PARAM_MISS_MSG = "缺少参数";
	public static final String PARAM_ERR_MSG = "参数不正确";
	public static final String TOKEN_MISS_MSG = "缺少token";
	public static final String TOKEN_CHECK_ERR_MSG = "token校验异常";
	public static final String SYS_ERROR_MSG = "系统繁忙";
	public static final String SYS_BUSY_MSG = "系统繁忙";

	public static final String IP_VALIDATE_ERR = "非法访问";
	public static final String IP_BLACK_VISIT = "黑名单用户，非法访问";
	public static final String LOGIN_USER_NOTEXIST = "用户账号密码不匹配";
	public static final String LOGIN_USER_STOP = "此号已被冻结";
	public static final String LOGIN_USER_ERR = "用户登录失败";
	public static final String LOGIN_TOKEN_ERR = "token转换失败";
}
