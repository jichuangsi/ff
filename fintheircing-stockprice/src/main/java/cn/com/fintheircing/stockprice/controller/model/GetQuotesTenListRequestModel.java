package cn.com.fintheircing.stockprice.controller.model;

import java.util.List;

public class GetQuotesTenListRequestModel {
	private List<String> markets;//对应stockCodes的市场代码
	private List<String> stockCodes;//股票代码

	public List<String> getMarkets() {
		return markets;
	}

	public void setMarkets(List<String> markets) {
		this.markets = markets;
	}

	public List<String> getStockCodes() {
		return stockCodes;
	}

	public void setStockCodes(List<String> stockCodes) {
		this.stockCodes = stockCodes;
	}

}
