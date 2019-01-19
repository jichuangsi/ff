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

	public static final String SYS_BUSY_BLACK = "黑名单访问，融断";
	public static final String PRODUCT_GET_ERR = "获取产品失败";
	public static final String ACCOUNT_NUM_ERR = "保证金金额数目不正确";
	public static final String ACCOUNT_LESS_ERR = "账户余额不足";
	public static final String ACCOUNT_PAY_ERR = "扣款失败";
	public static final String CONTRACT_SAVE_ERR = "保存合约失败";


	public static final String SPREAD_CREATED_ERR = "生成邀请码失败";
	public static final String PRODUCT_NOTEXIST_ERR = "不存在该产品";
	public static final String PRODUCT_ISEXIST_ERR = "不可购买该类产品";
	public static final String STOCK_DANGER_ERR  = "此类股票为危险股票";
	public static final String APPLY_FAIL  = "申请中,数据保存失败,请稍后再填";

	public static final String SUCESS_INFO = "success";
	public static final String ERR_INFO = "error";

	public static final String STOCK_HOLD_ERR = "您尚未持有该股票";
	public static final String STOCK_SELL_LESS_ERR = "您的可售股份不足";
	public static final String STOCK_SELL_ERR = "出售股份失败";

}
