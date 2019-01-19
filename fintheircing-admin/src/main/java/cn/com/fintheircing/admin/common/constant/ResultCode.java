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
	public static final String USER_EXITS = "用户不存在";

	public static final String IP_VISIT_ERR = "非法访问";
	public static final String LOGIN_VALIDATE_ERR = "账号或者密码错误";
	public static final String POWER_VISIT_ERR = "无权限访问";
	public static final String LOGIN_TOKEN_ERR = "token生成失败";
	public static final String LOGIN_ADMIN_ERR = "管理员登录失败";

	public static final String UPDATE_ERR = "修改失败";
	public static final String PROXY_ISEXIST_ERR = "已存在该代理";
	public static final String COMMISSION_NULL_ERR = "请填写分成";
	public static final String INVITCODE_GET_MSG = "请重新申请邀请码";
	public static final String INVITCODE_PIC_MSG = "二维码生成失败";
	public static final String SELECT_NULL_MSG = "查无此信息";
	public static final String SELECT_OVER_MSG = "下标越界";
	public static final String DELETE_FAIL_MSG = "删除失败";

	public static final String DATE_FORMATE_MSG = "日期格式不正确";
	public static final String DATE_INVIDATE_MSG = "日期不合法";
	public static final String VISIT_VALIDITY_MSG = "非法访问，请从页面进行访问";
	public static final String PIC_UPLODE_MSG = "图片上传错误";

	public static final String PAY_INFO_EXIT= "1";
	public static final String PAY_INFO_NOTEXIT= "0";
	public static final String CHECK_ANOTHER= "检查第三方支付的返回值";



	public static final String ACCOUNT_LESS_ERR  = "可用资金不足";
	public static final String ACCOUNT_WARN_ERR  = "您已到警戒线，请补交保证金";
	public static final String ACCOUNT_COST_ERR  = "扣款失败";
	public static final String STOCK_ENTRUST_SAVE_ERR  = "保存委托单失败";
	public static final String ADD_PROMISE_MONEY_ERR  = "增加保证金失败";

	public static final String STOCK_ENTRUST_MAX = "该股超过最大购买";
	public static final String STOCK_ENTRUST_DANGER = "该股为危险股票";
	public static final String STOCK_ENTRUST_NULL = "申请表为空";
	public static final String STOCK_NULL_FIND = "未找到该股的交易记录";
	public static final String CONTRACT_NULL_FIND = "您尚未创建交易合约";
	public static final String STOCK_BAD_ERR = "此单是废单";
	public static final String STOCK_BUSINESS_ERR = "交易失败";
	public static final String UPDATE_ERR_MSG = "更新失败";
	public static final String UPDATE_ALREAD_MSG = "已有人在处理";

	public static final String STOCK_SELL_LESS_ERR = "您的股票数目不足";
	public static final String ENTRUST_IS_DEAL = "该单已完结";
	public static final String ENTRUST_WAIT_DEAL = "该单正在处理中，不可撤单";
	public static final String ENTRUST_BACK_ERR = "撤单失败";

	public static final String MOTHER_NULL_ERR = "母账号未空";
	public static final String STOCK_MSG_ERR = "证券无响应";
}
