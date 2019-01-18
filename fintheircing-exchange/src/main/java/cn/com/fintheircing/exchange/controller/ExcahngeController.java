/**
 * 交易接口
 */
package cn.com.fintheircing.exchange.controller;

import java.util.List;

import javax.annotation.Resource;

import cn.com.fintheircing.exchange.constant.AccountStatus;
import cn.com.fintheircing.exchange.dao.repository.IParentAccountRepository;
import cn.com.fintheircing.exchange.entity.SecuritiesInfo;
import cn.com.fintheircing.exchange.model.*;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.com.fintheircing.exchange.constant.ResultCode;
import cn.com.fintheircing.exchange.controller.model.BuyOrderRequestModel;
import cn.com.fintheircing.exchange.controller.model.CancleOrderRequestModel;
import cn.com.fintheircing.exchange.controller.model.SellOrderRequestModel;
import cn.com.fintheircing.exchange.exception.ExchangeException;
import cn.com.fintheircing.exchange.service.ExchangeServiceRouter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@Api("ExcahngeController（第三方交易接口）相关的api")
public class ExcahngeController {
	@Resource
	private ExchangeServiceRouter exchangeService;
	@Resource
	private IParentAccountRepository iParentAccountRepository;

	@ApiOperation(value = "查询当天成交", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "motnerAccount", value = "母账户帐号（资金帐号）", required = true, dataType = "String") })
	@GetMapping("/getTodayAcceptOrderList/{motnerAccount}")
	public ResponseModel<List<TodayAcceptOrder>> getTodayAcceptOrderList(@PathVariable String motnerAccount) {
		List<TodayAcceptOrder> result;
		try {
			result = exchangeService.getTodayAcceptOrderList(motnerAccount);
			return ResponseModel.sucess("", result);
		} catch (ExchangeException e) {
			e.printStackTrace();
			return new ResponseModel<List<TodayAcceptOrder>>("", ResultCode.SYS_EXCHANEG_ERR, e.getMessage(), null);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}

	@ApiOperation(value = "查询当天委托", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "motnerAccount", value = "母账户帐号（资金帐号）", required = true, dataType = "String") })
	@GetMapping("/getTodayOrderList/{motnerAccount}")
	public ResponseModel<List<TodayOrder>> getTodayOrderList(@PathVariable String motnerAccount) {
		List<TodayOrder> result;
		try {
			result = exchangeService.getTodayOrderList(motnerAccount);
			return ResponseModel.sucess("", result);
		} catch (ExchangeException e) {
			e.printStackTrace();
			return new ResponseModel<List<TodayOrder>>("", ResultCode.SYS_EXCHANEG_ERR, e.getMessage(), null);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}

	@ApiOperation(value = "查询历史成交（数据不含当天，注意接口数量最大约2K条，因此最好不要查询超过三天）", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "motnerAccount", value = "母账户帐号（资金帐号）", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "path", name = "startDate", value = "开始日期，格式为20190101", required = true, dataType = "num"),
			@ApiImplicitParam(paramType = "path", name = "endDate", value = "开始日期，格式为20190101", required = true, dataType = "num") })
	@GetMapping("/getHistoryAcceptOrderList/{motnerAccount}/{startDate}/{endDate}")
	public ResponseModel<List<HistoryAcceptOrder>> getHistoryAcceptOrderList(@PathVariable String motnerAccount,
			@PathVariable int startDate, @PathVariable int endDate) {
		List<HistoryAcceptOrder> result;
		try {
			result = exchangeService.getHistoryAcceptOrderList(motnerAccount, startDate, endDate);
			return ResponseModel.sucess("", result);
		} catch (ExchangeException e) {
			e.printStackTrace();
			return new ResponseModel<List<HistoryAcceptOrder>>("", ResultCode.SYS_EXCHANEG_ERR, e.getMessage(), null);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}

	@ApiOperation(value = "买入委托", notes = "")
	@ApiResponse(code = 200, message = "data里面返回的是委托单号")
	@PostMapping("/sendBuyOrder")
	public ResponseModel<String> sendBuyOrder(@Validated @RequestBody BuyOrderRequestModel buyOrderRequestModel) {
		BuyOrderResult result;

		// 除买入价格外和买入数量，其余通过@Validated校验
		if (buyOrderRequestModel.getPrice() < 0) {
			return new ResponseModel<>("", ResultCode.SYS_EXCHANEG_ERR, "买入价格必须大于0", null);
		}
		if (0 != buyOrderRequestModel.getQuantity() % 100) {
			return new ResponseModel<>("", ResultCode.SYS_EXCHANEG_ERR, "买入数必须为100的整数倍", null);
		}
		try {
			result = exchangeService.sendBuyOrder(buyOrderRequestModel.getMotnerAccount(),
					buyOrderRequestModel.getExchangeId(), buyOrderRequestModel.getPszStockCode(),
					buyOrderRequestModel.getPrice(), buyOrderRequestModel.getQuantity());

			if (null == result) {
				return ResponseModel.fail("", ResultCode.SYS_EXCHANEG_ERR);
			}
			if (result.isSucess()) {
				return ResponseModel.sucess("", result.getOrderNum());
			} else {
				return ResponseModel.fail("", result.getMsg());
			}

		} catch (ExchangeException e) {
			e.printStackTrace();
			return new ResponseModel<>("", ResultCode.SYS_EXCHANEG_ERR, e.getMessage(), null);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}

	@ApiOperation(value = "卖出委托", notes = "")
	@ApiResponse(code = 200, message = "data里面返回的是委托单号")
	@PostMapping("/sendSellOrder")
	public ResponseModel<String> sendBuyOrder(@Validated @RequestBody SellOrderRequestModel sellOrderRequestModel) {
		SellOrderResult result;
		// 除买入价格外和买入数量，其余通过@Validated校验
		if (sellOrderRequestModel.getPrice() <= 0) {
			return new ResponseModel<>("", ResultCode.SYS_EXCHANEG_ERR, "卖出价格必须大于0", null);
		}
		if (0 != sellOrderRequestModel.getQuantity() % 100) {
			return new ResponseModel<>("", ResultCode.SYS_EXCHANEG_ERR, "卖出数必须为100的整数倍", null);
		}
		try {
			result = exchangeService.sendSellOrder(sellOrderRequestModel.getMotnerAccount(),
					sellOrderRequestModel.getExchangeId(), sellOrderRequestModel.getPszStockCode(),
					sellOrderRequestModel.getPrice(), sellOrderRequestModel.getQuantity());

			if (null == result) {
				return ResponseModel.fail("", ResultCode.SYS_EXCHANEG_ERR);
			}
			if (result.isSucess()) {
				return ResponseModel.sucess("", result.getOrderNum());
			} else {
				return ResponseModel.fail("", result.getMsg());
			}

		} catch (ExchangeException e) {
			e.printStackTrace();
			return new ResponseModel<>("", ResultCode.SYS_EXCHANEG_ERR, e.getMessage(), null);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}
	
	@ApiOperation(value = "查询可撤销委托单", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "motnerAccount", value = "母账户帐号（资金帐号）", required = true, dataType = "String") })
	@GetMapping("/getCanCancleOrderList/{motnerAccount}")
	public ResponseModel<List<CanCancleOrder>> getCanCancleOrderList(@PathVariable String motnerAccount) {
		List<CanCancleOrder> result;
		try {
			result = exchangeService.getCanCancleOrderList(motnerAccount);
			return ResponseModel.sucess("", result);
		} catch (ExchangeException e) {
			e.printStackTrace();
			return new ResponseModel<List<CanCancleOrder>>("", ResultCode.SYS_EXCHANEG_ERR, e.getMessage(), null);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}

	@ApiOperation(value = "撤单", notes = "")
	@ApiResponse(code = 200, message = "data返回需要撤销的委托单的单号")
	@PostMapping("/cancelOrder")
	public ResponseModel<String> cancelOrder(@Validated @RequestBody CancleOrderRequestModel cancleOrderRequestModel) {
		CancleOrderResult result;
		try {
			result = exchangeService.cancelOrder(cancleOrderRequestModel.getMotnerAccount(),
					cancleOrderRequestModel.getExchangeId(), cancleOrderRequestModel.getPszStockCode(),
					cancleOrderRequestModel.getOrderNum());
			if (null == result) {
				return ResponseModel.fail("", ResultCode.SYS_EXCHANEG_ERR);
			}
			if (result.isSucess()) {
				return ResponseModel.sucess("", cancleOrderRequestModel.getOrderNum());
			} else {
				return ResponseModel.fail("", result.getMsg());
			}

		} catch (ExchangeException e) {
			e.printStackTrace();
			return new ResponseModel<>("", ResultCode.SYS_EXCHANEG_ERR, e.getMessage(), null);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseModel.fail("", e1.getMessage());
		}
	}

}
