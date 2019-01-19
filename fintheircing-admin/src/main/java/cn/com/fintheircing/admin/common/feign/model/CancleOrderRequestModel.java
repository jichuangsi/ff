package cn.com.fintheircing.admin.common.feign.model;

import javax.validation.constraints.NotBlank;

public class CancleOrderRequestModel {

	@NotBlank(message = "母帐号不能为空")
	private String motnerAccount;
	@NotBlank(message = "交易所ID不能为空,0 深圳，1 上海")
	private String exchangeId;
	@NotBlank(message = "股票代码不能为空")
	private String pszStockCode;
	@NotBlank(message = "委托单号不能为空")
	private String orderNum;

	public String getMotnerAccount() {
		return motnerAccount;
	}

	public void setMotnerAccount(String motnerAccount) {
		this.motnerAccount = motnerAccount;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getPszStockCode() {
		return pszStockCode;
	}

	public void setPszStockCode(String pszStockCode) {
		this.pszStockCode = pszStockCode;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
