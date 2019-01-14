/**
 * 交易服务路由，由于每一个母账户对应一个ExcahngeService实例，所以由这个类出初始化并决定调用哪个服务实例
 */
package cn.com.fintheircing.exchange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import cn.com.fintheircing.exchange.exception.ExchangeException;
import cn.com.fintheircing.exchange.model.BuyOrderResult;
import cn.com.fintheircing.exchange.model.CanCancleOrder;
import cn.com.fintheircing.exchange.model.CancleOrderResult;
import cn.com.fintheircing.exchange.model.HistoryAcceptOrder;
import cn.com.fintheircing.exchange.model.MotherAccount;
import cn.com.fintheircing.exchange.model.SellOrderResult;
import cn.com.fintheircing.exchange.model.TodayAcceptOrder;
import cn.com.fintheircing.exchange.model.TodayOrder;

@Service
public class ExchangeServiceRouter implements InitializingBean {

	private Map<String, ExcahngeService> servicesMap = new HashMap<>();
	private int replySeconds = 120;// 心跳时间
	@Resource
	private MotherAccountQueryService motherAccountQueryService;

	/**
	 * 查询当天成交
	 * 
	 * @param motnerAccount
	 *            母账户帐号（资金帐号）
	 */
	public List<TodayAcceptOrder> getTodayAcceptOrderList(String motnerAccount) throws ExchangeException {
		ExcahngeService service = selectService(motnerAccount);
		return service.getTodayAcceptOrderList();
	}

	/**
	 * 查询当天委托
	 * 
	 * @param motnerAccount
	 *            母账户帐号（资金帐号）
	 */
	public List<TodayOrder> getTodayOrderList(String motnerAccount) throws ExchangeException {
		ExcahngeService service = selectService(motnerAccount);
		return service.getTodayOrderList();
	}

	/**
	 * 查询历史成交（查不到当天的）
	 * 
	 * @param startDate
	 *            开始日期，整型，格式为20190101
	 * @param endDate
	 *            结束日期，整型，格式为20190101
	 */
	public List<HistoryAcceptOrder> getHistoryAcceptOrderList(String motnerAccount, int startDate, int endDate)
			throws ExchangeException {
		ExcahngeService service = selectService(motnerAccount);
		return service.getHistoryAcceptOrderList(startDate, endDate);
	}

	/**
	 * 买入
	 * 
	 * @param motnerAccount
	 *            母账户帐号（资金帐号）
	 * @param exchangeId
	 *            交易所ID
	 * @param pszStockCode
	 *            交易所股票代码
	 * @param price
	 *            买入价格
	 * @param quantity
	 *            买入数量
	 * @throws ExchangeException
	 */
	public BuyOrderResult sendBuyOrder(String motnerAccount, String exchangeId, String pszStockCode, float price,
			int quantity) throws ExchangeException {
		ExcahngeService service = selectService(motnerAccount);
		return service.sendBuyOrder(exchangeId, pszStockCode, price, quantity);
	}

	/**
	 * 买入
	 * 
	 * @param motnerAccount
	 *            母账户帐号（资金帐号）
	 * @param exchangeId
	 *            交易所ID
	 * @param pszStockCode
	 *            交易所股票代码
	 * @param price
	 *            卖出价格
	 * @param quantity
	 *            卖出数量
	 * @throws ExchangeException
	 */
	public SellOrderResult sendSellOrder(String motnerAccount, String exchangeId, String pszStockCode, float price,
			int quantity) throws ExchangeException {
		ExcahngeService service = selectService(motnerAccount);
		return service.sendSellOrder(exchangeId, pszStockCode, price, quantity);
	}
	
	/**
	 * 查询所有可撤销的委托单
	 */
	public List<CanCancleOrder> getCanCancleOrderList(String motnerAccount) throws ExchangeException{
		ExcahngeService service = selectService(motnerAccount);
		return service.getCanCancleOrderList();
	}

	/**
	 * 撤单
	 * 
	 * @param motnerAccount
	 *            母账户帐号（资金帐号）
	 * @param exchangeId
	 *            交易所ID
	 * @param pszStockCode
	 *            交易所股票代码
	 * @param orderNum
	 *            需要撤销的委托单单号
	 * @throws ExchangeException
	 */
	public CancleOrderResult cancelOrder(String motnerAccount, String exchangeId, String pszStockCode,
			String orderNum) throws ExchangeException {
		ExcahngeService service = selectService(motnerAccount);
		return service.cancelOrder(exchangeId, pszStockCode, orderNum);
	}

	// 初始化，获取母账户列表，初始化服务实例
	@Override
	public void afterPropertiesSet() throws Exception {
		List<MotherAccount> accountList = motherAccountQueryService.getAllAviable();
		ExcahngeService service;
		for (MotherAccount account : accountList) {
			service = new ExcahngeService(account, replySeconds);
			servicesMap.put(account.getAccountNo(), service);
		}
	}

	// 根据母账户选择具体服务
	private ExcahngeService selectService(String motnerAccount) throws ExchangeException {
		ExcahngeService service = servicesMap.get(motnerAccount);
		if (null == motnerAccount) {
			throw new ExchangeException("母账户：" + motnerAccount + " 未启用");
		}
		return service;
	}

}
