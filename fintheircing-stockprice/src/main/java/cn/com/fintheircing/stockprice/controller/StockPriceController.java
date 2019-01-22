/**
 * 行情接口
 */
package cn.com.fintheircing.stockprice.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.fintheircing.stockprice.constant.ResultCode;
import cn.com.fintheircing.stockprice.controller.model.GetQuotesTenListRequestModel;
import cn.com.fintheircing.stockprice.controller.model.ResponseModel;
import cn.com.fintheircing.stockprice.model.QuotesTenModel;
import cn.com.fintheircing.stockprice.service.StockPriceLv2Service;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class StockPriceController {

	@Resource
	private StockPriceLv2Service stockPriceLv2Service;

	@ApiOperation(value = "批量查询10档报价", notes = "")
	@PostMapping("/getQuotesTenList")
	public ResponseModel<List<QuotesTenModel>> getQuotesTenList(@RequestBody GetQuotesTenListRequestModel model) {
		List<QuotesTenModel> result;
		List<String> markets = model.getMarkets();
		List<String> stockCodes = model.getStockCodes();
		if (null == markets || null == stockCodes || 0 == markets.size() || 0 == stockCodes.size()) {
			return new ResponseModel<List<QuotesTenModel>>("", ResultCode.PARAM_ERR, ResultCode.PARAM_ERR_MSG, null);
		}
		if (markets.size() != stockCodes.size()) {
			return new ResponseModel<List<QuotesTenModel>>("", ResultCode.PARAM_ERR, "列表数量不一致", null);
		}

		char[] marketsArr = new char[markets.size()];
		for (int i = 0; i < markets.size(); i++) {
			marketsArr[i] = markets.get(i).charAt(0);
		}

		try {
			String[] stockCodesArr = new String[markets.size()];
			result = stockPriceLv2Service.getQuotesTen(marketsArr, stockCodes.toArray(stockCodesArr));
			return ResponseModel.sucess("", result);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}

}
