package cn.com.fintheircing.admin.common.feign.impl;

import cn.com.fintheircing.admin.common.feign.IStockPriceFeignService;
import cn.com.fintheircing.admin.common.feign.model.GetQuotesTenListRequestModel;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.model.ResponseModel;

import java.util.List;

public class StockPriceFeignServiceFallBack implements IStockPriceFeignService {

    @Override
    public ResponseModel<List<QuotesTenModel>> getQuotesTenList(GetQuotesTenListRequestModel model) {
        return ResponseModel.fail("");
    }

    @Override
    public ResponseModel<List<QuotesTenModel>> getQuotesTenListTest() {
        return ResponseModel.fail("");
    }
}
