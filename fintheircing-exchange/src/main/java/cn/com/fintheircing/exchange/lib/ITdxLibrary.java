///交易接口执行后，如果失败，则字符串ErrInfo保存了出错信息中文说明；
///如果成功，则字符串Result保存了结果数据,形式为表格数据，行数据之间通过\n字符分割，列数据之间通过\t分隔。
///Result是\n，\t分隔的中文字符串，比如查询股东代码时返回的结果字符串就是 

///"股东代码\t股东名称\t帐号类别\t保留信息\n
///0000064567\t\t0\t\nA000064567\t\t1\t\n
///2000064567\t\t2\t\nB000064567\t\t3\t"

///查得此数据之后，通过分割字符串， 可以恢复为几行几列的表格形式的数据

//2.API使用流程为: 应用程序先调用OpenTdx打开通达信实例，一个实例下可以同时登录多个交易账户，每个交易账户称之为ClientID.
//通过调用Logon获得ClientID，然后可以调用其他API函数向各个ClientID进行查询或下单; 应用程序退出时应调用Logoff注销ClientID, 最后调用CloseTdx关闭通达信实例. 
//OpenTdx和CloseTdx在整个应用程序中只能被调用一次.API带有断线自动重连功能，应用程序只需根据API函数返回的出错信息进行适当错误处理即可。

package cn.com.fintheircing.exchange.lib;

import com.sun.jna.Library;

/**
 * 
 *
 */
public interface ITdxLibrary extends Library {

	public static final String EXCHANGE_ID_SZ = "0";// 交易所ID， 深圳0(招商证券普通账户深圳是2)
	public static final String EXCHANGE_ID_SH = "1";// 交易所ID， 上海1，
	public static final String EXCHANGE_ID_SZ_ZS = "2";// 交易所ID，招商证券普通账户深圳是2

	/**
	 * 打开通达信实例
	 */
	public void OpenTdx();

	/**
	 * 关闭通达信实例
	 */
	public void CloseTdx();

	/**
	 * 交易账户登录
	 * 
	 * @param qsId
	 *            券商Id
	 * @param ip
	 *            券商交易服务器IP
	 * @param port
	 *            券商交易服务器端口
	 * @param version
	 *            设置通达信客户端的版本号，默认6.68
	 * @param ytbId
	 *            营业部代码，可设置为0
	 * @param accountType
	 *            登录账号类型，一般情况下,8-资金账号,9-客户号
	 * @param accountNo
	 *            完整的登录账号，券商一般使用资金帐户或客户号
	 * @param tradeAccount
	 *            交易账号，一般与登录帐号相同. 请登录券商通达信软件，查询股东列表，股东列表内的资金帐号就是交易帐号
	 * @param jyPassword
	 *            交易密码
	 * @param txPassword
	 *            通讯密码，部分券商没有通讯密码，可以为空串
	 * @param errInfo
	 *            此API执行返回后，如果出错，保存了错误信息说明。一般要分配256字节的空间。没出错时为空字符串。
	 * 
	 * @return 客户端ID，调用各个函数时需要使用到的clientId参数，失败时返回-1
	 */
	public int LogonEx(String qsId, String ip, short port, String version, short yybID, int accountType,
			String accountNo, String tradeAccount, String jyPassword, String txPassword, byte[] errInfo);

	/**
	 * 交易账户注销
	 * 
	 * @param clientId
	 *            客户端ID
	 */
	public void Logoff(int clientId);

	/**
	 * 查询各种交易数据
	 * 
	 * @param clientId
	 *            客户端ID
	 * @param category
	 *            表示查询信息的种类，0资金 1股份 2当日委托 3当日成交 4可撤单 5股东代码 6融资余额 7融券余额 8可融证券 9信用资产查询
	 * @param result
	 *            此API执行返回后，Result内保存了返回的查询数据,
	 *            形式为表格数据，行数据之间通过\n字符分割，列数据之间通过\t分隔。一般要分配1024*1024字节的空间。出错时为空字符串。
	 * @param errInfo
	 *            此API执行返回后，如果出错，保存了错误信息说明。一般要分配256字节的空间。没出错时为空字符串。
	 */
	public void QueryData(int clientId, int category, byte[] result, byte[] errInfo);

	/**
	 * 查询历史
	 * 
	 * @param clientId
	 *            客户端ID
	 * @param category
	 *            表示查询信息的种类，0 资金,1   交割单，2 历史委托，3 历史成交
	 * @param startDate
	 *            开始日期，整型，格式为20190101
	 * @param endDate
	 *            结束日期，整型，格式为20190101
	 * @param result
	 *            此API执行返回后，Result内保存了返回的查询数据,
	 *            形式为表格数据，行数据之间通过\n字符分割，列数据之间通过\t分隔。一般要分配1024*1024字节的空间。出错时为空字符串。
	 * @param errInfo
	 *            此API执行返回后，如果出错，保存了错误信息说明。一般要分配256字节的空间。没出错时为空字符串。
	 */
	public void QueryHisData(int clientId, int category, int startDate, int endDate, byte[] result, byte[] errInfo);

	/**
	 * 下委托交易证券
	 * 
	 * @param clientId
	 *            客户端ID
	 * @param category
	 *            表示委托的种类，0买入 1卖出 2融资买入 3融券卖出
	 * @param priceType
	 *            表示报价方式 0上海限价委托 深圳限价委托；1(市价委托)深圳对方最优价格；2(市价委托)深圳本方最优价格；
	 *            3(市价委托)深圳即时成交剩余撤销；4(市价委托)上海五档即成剩撤 深圳五档即成剩撤；5(市价委托)深圳全额成交或撤销；
	 *            6(市价委托)上海五档即成转限价
	 * @param gddm
	 *            股东代码, 交易上海股票填上海的股东代码；交易深圳的股票填入深圳的股东代码
	 * @param zqdm
	 *            证券代码
	 * @param price
	 *            委托价格
	 * @param quantity
	 *            委托数量，至少为100（一手）
	 * @param result
	 *            此API执行返回后，Result内保存了返回的查询数据,其中含有委托编号数据
	 *            形式为表格数据，行数据之间通过\n字符分割，列数据之间通过\t分隔。一般要分配1024*1024字节的空间。出错时为空字符串。
	 * @param errInfo
	 *            此API执行返回后，如果出错，保存了错误信息说明。一般要分配256字节的空间。没出错时为空字符串。
	 */
	public void SendOrder(int clientId, int category, int priceType, String gddm, String zqdm, float price,
			int quantity, byte[] result, byte[] errInfo);

	/**
	 * 撤委托
	 * 
	 * @param clientId
	 *            客户端ID
	 * @param exchangeId
	 *            交易所ID， 上海1，深圳0(招商证券普通账户深圳是2)
	 * @param pszStockAccount
	 *            股东代码, 交易上海股票填上海的股东代码；交易深圳的股票填入深圳的股东代码,
	 * @param pszStockCode
	 *            股票代码
	 * @param pszhth
	 *            表示要撤的目标委托的编号,SendOrder时可从result里面获取
	 * @param result
	 *            此API执行返回后，Result内保存了返回的查询数据,其中含有委托编号数据
	 *            形式为表格数据，行数据之间通过\n字符分割，列数据之间通过\t分隔。一般要分配1024*1024字节的空间。出错时为空字符串。
	 * @param errInfo
	 *            此API执行返回后，如果出错，保存了错误信息说明。一般要分配256字节的空间。没出错时为空字符串。
	 */
	public void CancelOrder(int clientId, String exchangeId, String pszStockAccount, String pszStockCode, String pszhth,
			byte[] result, byte[] errInfo);
}
