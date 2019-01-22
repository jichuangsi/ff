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

package cn.com.fintheircing.stockprice.lib;

import com.sun.jna.Library;
import com.sun.jna.ptr.ShortByReference;

/**
 * 
 *
 */
public interface ITdxL2HqLibrary extends Library {

	public static final String EXCHANGE_ID_SZ = "0";// 交易所ID， 深圳0(招商证券普通账户深圳是2)
	public static final String EXCHANGE_ID_SH = "1";// 交易所ID， 上海1，
	public static final String EXCHANGE_ID_SZ_ZS = "2";// 交易所ID，招商证券普通账户深圳是2

	/**
	 * 打开通达信LV2行情
	 * 
	 * @return 行情链接代码，即其他接口用到的connId参数
	 */
	int TdxL2Hq_Connect(String ip, short port, String lv2UserName, String lv2Pwd, byte[] result, byte[] errInfo);

	/**
	 * 关闭通达信LV2行情
	 * 
	 * @param connId
	 *            行情链接代码，TdxL2Hq_Connect的返回值
	 */
	void TdxL2Hq_Disconnect(int connId);

	/**
	 * 批量某个市场（0 深圳，1 上海）的证券数量
	 * 
	 * @param connId
	 *            行情链接代码，TdxL2Hq_Connect的返回值
	 * @param markets
	 *            市场代码：0 深圳，1 上海；数组的第i个元素表示第i个证券的市场代码
	 * @param count
	 *            证券数（执行后，返回的证券数）
	 * 
	 */
	boolean TdxL2Hq_GetSecurityCount(int connId, char market, ShortByReference count, byte[] errInfo);

	/**
	 * 批量获取多个证券（最大100，不券商可能不同，需咨询券商）的5档报价
	 * 
	 * @param connId
	 *            行情链接代码，TdxL2Hq_Connect的返回值
	 * @param markets
	 *            市场代码：0 深圳，1 上海；数组的第i个元素表示第i个证券的市场代码
	 * @param stockCodes
	 *            证券代码数组
	 * @param count
	 *            证券代码数(证券代码数组的大小,注意不要超过100)
	 * 
	 */
	boolean TdxL2Hq_GetSecurityQuotes(int connId, char[] markets, String[] stockCodes, ShortByReference count,
			byte[] result, byte[] errInfo);

	/**
	 * 批量获取多个证券（最大100，不券商可能不同，需咨询券商）的10档报价
	 * 
	 * @param connId
	 *            行情链接代码，TdxL2Hq_Connect的返回值
	 * @param markets
	 *            市场代码：0 深圳，1 上海；数组的第i个元素表示第i个证券的市场代码
	 * @param stockCodes
	 *            证券代码数组
	 * @param count
	 *            证券代码数(证券代码数组的大小,注意不要超过100)
	 * 
	 */
	boolean TdxL2Hq_GetSecurityQuotes10(int connId, char[] markets, String[] stockCodes, ShortByReference count,
			byte[] result, byte[] errInfo);
}
