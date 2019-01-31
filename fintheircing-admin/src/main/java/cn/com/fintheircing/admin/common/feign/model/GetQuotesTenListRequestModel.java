package cn.com.fintheircing.admin.common.feign.model;

import java.util.ArrayList;
import java.util.List;

public class GetQuotesTenListRequestModel {
	private List<String> markets = new ArrayList<String>();//对应stockCodes的市场代码
	private List<String> stockCodes = new ArrayList<String>();//股票代码

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
