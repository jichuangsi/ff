/**
 * 
 */
package cn.com.fintheircing.exchange.model;

public abstract class AbstractOrder {

	public static final String OPER_FLAG_BUY = "0";
	public static final String OPER_FLAG_SELL = "1";

	public static final String ORDER_TYPE_BUY = "0";
	public static final String ORDER_TYPE_SELL = "1";
	public static final String ORDER_TYPE_BUY_LOAN = "2";
	public static final String ORDER_TYPE_SELL_LOAN = "3";

	public static final String EXCHANGE_ID_SZ = "0";// 交易所ID， 深圳0(招商证券普通账户深圳是2)
	public static final String EXCHANGE_ID_SH = "1";// 交易所ID， 上海1，
	public static final String EXCHANGE_ID_SZ_ZS = "2";// 交易所ID，招商证券普通账户深圳是2

	protected String orderNumber;// 委托单号（委托编号）
	protected String operFlag;// 买卖标志130,0 买,1 卖
	protected String operName;// 买卖标志131
	protected String orderType;// 委托种类（委托方式） 0买入 1卖出 2融资买入 3融券卖出

	protected String stockCode;// 证券代码
	protected String stockName;// 证券名称
	protected String orderTypeName;// 委托类别（中文）
	protected String status;// 委托类别
	protected float orderPrice;// 委托价格
	protected float orderQuantity;// 委托数量
	protected float actPrice;// 成交价格
	protected float actQuantity;// 成交数量
	protected String quoteName;// 报价方式
	protected String shareholderCode;// 股东代码
	protected String shareholderCodeType;// 帐号类别（与交易所代码一致）
	protected String exchangeId;// 交易所代码

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}

	public float getActPrice() {
		return actPrice;
	}

	public void setActPrice(float actPrice) {
		this.actPrice = actPrice;
	}

	public String getQuoteName() {
		return quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	public String getShareholderCode() {
		return shareholderCode;
	}

	public void setShareholderCode(String shareholderCode) {
		this.shareholderCode = shareholderCode;
	}

	public String getShareholderCodeType() {
		return shareholderCodeType;
	}

	public void setShareholderCodeType(String shareholderCodeType) {
		this.shareholderCodeType = shareholderCodeType;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public float getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(float orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public float getActQuantity() {
		return actQuantity;
	}

	public void setActQuantity(float actQuantity) {
		this.actQuantity = actQuantity;
	}

}
