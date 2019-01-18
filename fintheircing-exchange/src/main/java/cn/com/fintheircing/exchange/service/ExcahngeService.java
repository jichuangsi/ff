/**
 * 交易服务，每一个母账户对应一个ExcahngeService实例
 */
package cn.com.fintheircing.exchange.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Native;

import cn.com.fintheircing.exchange.exception.ExchangeException;
import cn.com.fintheircing.exchange.lib.ITdxLibrary;
import cn.com.fintheircing.exchange.model.BuyOrderResult;
import cn.com.fintheircing.exchange.model.CanCancleOrder;
import cn.com.fintheircing.exchange.model.CancleOrderResult;
import cn.com.fintheircing.exchange.model.HistoryAcceptOrder;
import cn.com.fintheircing.exchange.model.HistoryOrder;
import cn.com.fintheircing.exchange.model.MotherAccount;
import cn.com.fintheircing.exchange.model.SellOrderResult;
import cn.com.fintheircing.exchange.model.TodayAcceptOrder;
import cn.com.fintheircing.exchange.model.TodayOrder;
import cn.com.fintheircing.exchange.util.EncodeUtils;

public class ExcahngeService {
	private final ITdxLibrary tdxLibrary = (ITdxLibrary) Native.loadLibrary("TradeX2-M", ITdxLibrary.class);
	private int clientId;// 客户端ID
	private final ScheduledExecutorService threadPool;
	private final MotherAccount motherAccount;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param motherAccount
	 *            交易母账户信息（调用前注意检查数据完整性）
	 * @param replySeconds
	 *            保持连接的心跳时间（秒），小于等于0时表示不使用心跳
	 */
	public ExcahngeService(MotherAccount motherAccount, int replySeconds) {
		this.motherAccount = motherAccount;
		logger.info("打开通达信");
		tdxLibrary.OpenTdx();
		byte[] errorInfo = new byte[256];
		logger.info("登录帐号：" + motherAccount.getAccountNo());
		this.clientId = tdxLibrary.Logon(Integer.parseInt(motherAccount.getQsId()), motherAccount.getIp(),
				motherAccount.getPort(), motherAccount.getVersion(), motherAccount.getYybId(),
				motherAccount.getAccountType(), motherAccount.getAccountNo(), motherAccount.getTradeAccount(),
				motherAccount.getJyPassword(), motherAccount.getTxPassword(), errorInfo);
		if (-1 == clientId) {
			String msg = EncodeUtils.getUTF8StringFromGBKString(Native.toString(errorInfo, "GBK"));
			logger.error(msg);
			tdxLibrary.CloseTdx();
			throw new RuntimeException(msg);
		}
		// 加入定时线程，维持连接
		if (0 < replySeconds) {
			this.threadPool = Executors.newScheduledThreadPool(1);
			threadPool.scheduleAtFixedRate(() -> {
				byte[] errorInfo1 = new byte[256];
				logger.info(motherAccount.getAccountNo() + " 心跳维持连接" + replySeconds + "s一次...");
				if (!tdxLibrary.IsConnectOK(clientId)) {
					logger.info("链接已断开，再次登录");
					synchronized (this) {
						try {
							this.clientId = tdxLibrary.Logon(Integer.parseInt(motherAccount.getQsId()),
									motherAccount.getIp(), motherAccount.getPort(), motherAccount.getVersion(),
									motherAccount.getYybId(), motherAccount.getAccountType(),
									motherAccount.getAccountNo(), motherAccount.getTradeAccount(),
									motherAccount.getJyPassword(), motherAccount.getTxPassword(), errorInfo1);
							if (-1 == this.clientId) {
								logger.error("心跳重连异常："
										+ EncodeUtils.getUTF8StringFromGBKString(Native.toString(errorInfo1, "GBK")));
								logger.info("尝试关闭通达信...");
								tdxLibrary.CloseTdx();
								logger.info("尝试重新打开通达信...");
								tdxLibrary.OpenTdx();
								logger.info("等待下一次心跳...");
							}
						} catch (Exception e) {
							logger.error("交易服务异常：" + e.getMessage());
							e.printStackTrace();
						}
					}
				}

			}, 1, replySeconds, TimeUnit.SECONDS);
		} else {
			this.threadPool = null;
		}
	}

	/**
	 * 查询当天委托
	 */
	public List<TodayOrder> getTodayOrderList() throws ExchangeException {
		byte[] result = new byte[1024 * 1024];
		byte[] errorInfo = new byte[256];
		tdxLibrary.QueryData(getClientId(), 2, result, errorInfo);
		String errMsg = Native.toString(errorInfo, "GBK");
		String resultMsg = Native.toString(result, "GBK");
		if (!StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
			throw new ExchangeException("查询当天委托异常：" + EncodeUtils.getUTF8StringFromGBKString(errMsg));
		}

		String[] lines = resultMsg.split("\n");
		TodayOrder order;
		List<TodayOrder> resultList = new ArrayList<>(lines.length - 1);
		for (int i = 0; i < lines.length; i++) {
			// 跳过表头
			if (i > 0) {
				order = new TodayOrder(lines[i]);
				resultList.add(order);
			}
		}
		return resultList;
	}

	/**
	 * 查询当天成交
	 */
	public List<TodayAcceptOrder> getTodayAcceptOrderList() throws ExchangeException {
		byte[] result = new byte[1024 * 1024];
		byte[] errorInfo = new byte[256];
		tdxLibrary.QueryData(getClientId(), 3, result, errorInfo);
		String errMsg = Native.toString(errorInfo, "GBK");
		String resultMsg = Native.toString(result, "GBK");
		if (!StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
			throw new ExchangeException("查询当天成交异常：" + EncodeUtils.getUTF8StringFromGBKString(errMsg));
		}

		String[] lines = resultMsg.split("\n");
		TodayAcceptOrder order;
		List<TodayAcceptOrder> resultList = new ArrayList<>(lines.length - 1);
		for (int i = 0; i < lines.length; i++) {
			// 跳过表头
			if (i > 0) {
				order = new TodayAcceptOrder(lines[i]);
				resultList.add(order);
			}
		}
		return resultList;
	}

	/**
	 * 查询历史委托（查不到当天的）
	 * 
	 * @param startDate
	 *            开始日期，整型，格式为20190101
	 * @param endDate
	 *            结束日期，整型，格式为20190101
	 */
	public List<HistoryOrder> getHistoryOrderList(int startDate, int endDate) throws ExchangeException {
		byte[] result = new byte[1024 * 1024];
		byte[] errorInfo = new byte[256];
		tdxLibrary.QueryHistoryData(getClientId(), 0, Integer.toString(startDate), Integer.toString(endDate), result,
				errorInfo);
		String errMsg = Native.toString(errorInfo, "GBK");
		String resultMsg = Native.toString(result, "GBK");
		if (!StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
			throw new ExchangeException("查询历史委托异常：" + EncodeUtils.getUTF8StringFromGBKString(errMsg));
		}

		String[] lines = resultMsg.split("\n");
		HistoryOrder order;
		List<HistoryOrder> resultList = new ArrayList<>(lines.length - 1);
		for (int i = 0; i < lines.length; i++) {
			// 跳过表头
			if (i > 0) {
				order = new HistoryOrder(lines[i]);
				resultList.add(order);
			}
		}
		return resultList;
	}

	/**
	 * 查询历史成交（查不到当天的）
	 * 
	 * @param startDate
	 *            开始日期，整型，格式为20190101
	 * @param endDate
	 *            结束日期，整型，格式为20190101
	 */
	public List<HistoryAcceptOrder> getHistoryAcceptOrderList(int startDate, int endDate) throws ExchangeException {
		byte[] result = new byte[1024 * 1024];
		byte[] errorInfo = new byte[256];
		tdxLibrary.QueryHistoryData(getClientId(), 1, Integer.toString(startDate), Integer.toString(endDate), result,
				errorInfo);
		String errMsg = Native.toString(errorInfo, "GBK");
		String resultMsg = Native.toString(result, "GBK");
		if (!StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
			throw new ExchangeException("查询历史委托异常：" + EncodeUtils.getUTF8StringFromGBKString(errMsg));
		}

		String[] lines = resultMsg.split("\n");
		HistoryAcceptOrder order;
		List<HistoryAcceptOrder> resultList = new ArrayList<>(lines.length - 1);
		for (int i = 0; i < lines.length; i++) {
			// 跳过表头
			if (i > 0) {
				order = new HistoryAcceptOrder(lines[i]);
				resultList.add(order);
			}
		}
		return resultList;
	}

	/**
	 * 买入委托
	 * 
	 * @param exchangeId
	 *            交易所ID， 上海1，深圳0(招商证券普通账户深圳是2)
	 * @param pszStockCode
	 *            股票代码
	 * @param price
	 *            买入价格（RMB元）
	 * @param quantity
	 *            委托数量，至少为100（一手）
	 */
	public BuyOrderResult sendBuyOrder(String exchangeId, String pszStockCode, float price, int quantity) {
		BuyOrderResult buyOrderResult = new BuyOrderResult();
		try {
			byte[] result = new byte[256];
			byte[] errorInfo = new byte[256];
			String gddm = getGddm(exchangeId);
			if (StringUtils.isEmpty(gddm)) {
				buyOrderResult.setSucess(false);
				buyOrderResult.setMsg("交易所ID异常：" + exchangeId + "， 上海1，深圳0(招商证券普通账户深圳是2)");
				return buyOrderResult;
			}
			if (quantity < 100 || 0 >= quantity) {
				buyOrderResult.setSucess(false);
				buyOrderResult.setMsg("最少交易数量为100");
				return buyOrderResult;
			}
			if (0 >= price) {
				buyOrderResult.setSucess(false);
				buyOrderResult.setMsg("价格错误:" + price);
				return buyOrderResult;
			}
			// 执行dll调用
			tdxLibrary.SendOrder(getClientId(), 0, 0, gddm, pszStockCode, price, quantity, result, errorInfo);
			String errMsg = Native.toString(errorInfo, "GBK");
			String resultMsg = Native.toString(result, "GBK");
			if (!StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
				buyOrderResult.setSucess(false);
				buyOrderResult.setMsg(EncodeUtils.getUTF8StringFromGBKString(errMsg));
				return buyOrderResult;
			} else {
				String[] lines = resultMsg.split("\n");
				for (int i = 0; i < lines.length; i++) {
					// 跳过表头
					if (i > 0) {
						buyOrderResult.setOrderNum(lines[i].split("\t")[0]);// 第二行第一列为委托单号
						break;
					}
				}
				buyOrderResult.setSucess(true);
				return buyOrderResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("交易服务异常：" + e.getMessage());
			buyOrderResult.setSucess(false);
			buyOrderResult.setMsg("交易系统忙，请稍后再试");
			return buyOrderResult;
		}

	}

	/**
	 * 卖出委托
	 * 
	 * @param exchangeId
	 *            交易所ID， 上海1，深圳0(招商证券普通账户深圳是2)
	 * @param pszStockCode
	 *            股票代码
	 * @param price
	 *            买入价格（RMB元）
	 * @param quantity
	 *            委托数量，至少为100（一手）
	 */
	public SellOrderResult sendSellOrder(String exchangeId, String pszStockCode, float price, int quantity) {
		SellOrderResult sellOrderResult = new SellOrderResult();
		try {
			byte[] result = new byte[256];
			byte[] errorInfo = new byte[256];
			String gddm = getGddm(exchangeId);
			if (StringUtils.isEmpty(gddm)) {
				sellOrderResult.setSucess(false);
				sellOrderResult.setMsg("交易所ID异常：" + exchangeId + "， 上海1，深圳0(招商证券普通账户深圳是2)");
				return sellOrderResult;
			}
			if (quantity < 100 || 0 >= quantity) {
				sellOrderResult.setSucess(false);
				sellOrderResult.setMsg("最少交易数量为100");
				return sellOrderResult;
			}
			if (0 >= price) {
				sellOrderResult.setSucess(false);
				sellOrderResult.setMsg("价格错误:" + price);
				return sellOrderResult;
			}
			// 执行dll调用
			tdxLibrary.SendOrder(getClientId(), 1, 0, gddm, pszStockCode, price, quantity, result, errorInfo);
			String errMsg = Native.toString(errorInfo, "GBK");
			String resultMsg = Native.toString(result, "GBK");
			if (!StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
				sellOrderResult.setSucess(false);
				sellOrderResult.setMsg(EncodeUtils.getUTF8StringFromGBKString(errMsg));
				return sellOrderResult;
			} else {
				String[] lines = resultMsg.split("\n");
				for (int i = 0; i < lines.length; i++) {
					// 跳过表头
					if (i > 0) {
						sellOrderResult.setOrderNum(lines[i].split("\t")[0]);// 第二行第一列为委托单号
						break;
					}
				}
				sellOrderResult.setSucess(true);
				return sellOrderResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("交易服务异常：" + e.getMessage());
			sellOrderResult.setSucess(false);
			sellOrderResult.setMsg("交易系统忙，请稍后再试");
			return sellOrderResult;
		}
	}

	/**
	 * 查询所有可撤销的委托单
	 */
	public List<CanCancleOrder> getCanCancleOrderList() throws ExchangeException {
		byte[] result = new byte[1024 * 1024];
		byte[] errorInfo = new byte[256];
		tdxLibrary.QueryData(getClientId(), 4, result, errorInfo);
		String errMsg = Native.toString(errorInfo, "GBK");
		String resultMsg = Native.toString(result, "GBK");
		if (!StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
			throw new ExchangeException("查询可撤单记录异常：" + EncodeUtils.getUTF8StringFromGBKString(errMsg));
		}

		String[] lines = resultMsg.split("\n");
		CanCancleOrder order;
		List<CanCancleOrder> resultList = new ArrayList<>(lines.length - 1);
		for (int i = 0; i < lines.length; i++) {
			// 跳过表头
			if (i > 0) {
				order = new CanCancleOrder(lines[i]);
				resultList.add(order);
			}
		}
		return resultList;
	}

	/**
	 * 撤单
	 * 
	 * @param exchangeId
	 *            交易所ID， 上海1，深圳0(招商证券普通账户深圳是2)
	 * @param pszStockCode
	 *            股票代码
	 * @param orderNum
	 *            委托单号
	 */
	public CancleOrderResult cancelOrder(String exchangeId, String pszStockCode, String orderNum) {
		CancleOrderResult cancleOrderResult = new CancleOrderResult();
		try {
			byte[] result = new byte[256];
			byte[] errorInfo = new byte[256];
			String pszStockAccount = getGddm(exchangeId);
			if (StringUtils.isEmpty(pszStockAccount)) {
				cancleOrderResult.setSucess(false);
				cancleOrderResult.setMsg("交易所ID异常：" + exchangeId + "， 上海1，深圳0(招商证券普通账户深圳是2)");
				return cancleOrderResult;
			}
			// 调用dll执行
			tdxLibrary.CancelOrder(getClientId(), exchangeId.charAt(0), orderNum, result, errorInfo);
			String errMsg = Native.toString(errorInfo, "GBK");
			if (!StringUtils.isEmpty(errMsg)) {
				cancleOrderResult.setSucess(false);
				cancleOrderResult.setMsg(EncodeUtils.getUTF8StringFromGBKString(errMsg));
				return cancleOrderResult;
			} else {
				cancleOrderResult.setSucess(true);
				return cancleOrderResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("交易服务异常：" + e.getMessage());
			cancleOrderResult.setSucess(false);
			cancleOrderResult.setMsg("交易系统忙，请稍后再试");
			return cancleOrderResult;
		}
	}

	private String getGddm(String exchangeId) {
		if (ITdxLibrary.EXCHANGE_ID_SZ.equals(exchangeId) || ITdxLibrary.EXCHANGE_ID_SZ_ZS.equals(exchangeId)) {
			return motherAccount.getSzAccout();
		} else if (ITdxLibrary.EXCHANGE_ID_SH.equals(exchangeId)) {
			return motherAccount.getShAccout();
		} else {
			return null;
		}
	}

	public synchronized int getClientId() {
		return clientId;
	}

	public MotherAccount getMotherAccount() {
		return motherAccount;
	}

}
