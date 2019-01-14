package cn.com.fintheircing.exchange.controller.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class BuyOrderRequestModel {

	@NotBlank(message = "母帐号不能为空")
	private String motnerAccount;
	@NotBlank(message = "交易所ID不能为空,0 深圳，1 上海")
	private String exchangeId;
	@NotBlank(message = "股票代码不能为空")
	private String pszStockCode;
	private float price;
	@DecimalMin(value = "100",message = "买入数量最小为100")
	private int quantity;

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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
