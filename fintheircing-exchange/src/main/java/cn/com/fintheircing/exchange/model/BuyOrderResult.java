/**
 *买入委托结果
 *
 */

package cn.com.fintheircing.exchange.model;

public class BuyOrderResult extends AbstractExchangeResult {
	private String orderNum;// 委托单号

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
