package cn.com.fintheircing.admin.common.constant;

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

	public static final String IP_VISIT_ERR = "非法访问";
	public static final String LOGIN_VALIDATE_ERR = "账号或者密码错误";
	public static final String POWER_VISIT_ERR = "无权限访问";
	public static final String LOGIN_TOKEN_ERR = "token生成失败";
	public static final String LOGIN_ADMIN_ERR = "管理员登录失败";

	public static final String UPDATE_ERR = "修改失败";
	public static final String COMMISSION_NULL_ERR = "请填写分成";
	public static final String INVITCODE_GET_MSG = "请重新申请邀请码";
	public static final String INVITCODE_PIC_MSG = "二维码生成失败";
	public static final String SELECT_NULL_MSG = "查无此信息";
	public static final String SELECT_OVER_MSG = "下标越界";
}
