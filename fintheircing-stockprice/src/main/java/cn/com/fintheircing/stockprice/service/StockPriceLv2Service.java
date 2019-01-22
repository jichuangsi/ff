/**
 * lv2股票行情服务
 */

package cn.com.fintheircing.stockprice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

import cn.com.fintheircing.stockprice.lib.ITdxL2HqLibrary;
import cn.com.fintheircing.stockprice.model.QuotesTenModel;
import cn.com.fintheircing.stockprice.util.EncodeUtils;

@Service
public class StockPriceLv2Service implements InitializingBean {

	private ITdxL2HqLibrary tdxLibrary = (ITdxL2HqLibrary) Native.loadLibrary("TradeX2-M", ITdxL2HqLibrary.class);

	private int connId;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${custom.stockMarketLv2Ips}")
	private String[] stockMarketLv2Ips;
	@Value("${custom.stockMarketLv2UserName}")
	private String userName;
	@Value("${custom.stockMarketLv2Pwd}")
	private String pwd;

	private int maxCount = 50;

	/**
	 * lv2股票行情服务
	 */
	public List<QuotesTenModel> getQuotesTen(char[] markets, String[] stockCodes) {
		byte[] errInfo = new byte[256];
		byte[] result = new byte[2048 * maxCount];
		if (markets.length > maxCount) {
			throw new RuntimeException("一次最多查询" + maxCount + "支股票");
		}

		ShortByReference count = new ShortByReference((short) markets.length);
		boolean flag = tdxLibrary.TdxL2Hq_GetSecurityQuotes10(connId, markets, stockCodes, count, result, errInfo);

		String errMsg = Native.toString(errInfo, "GBK");
		String resultMsg = Native.toString(result, "GBK");
		if (!flag || !StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
			throw new RuntimeException("查询十档行情异常:" + EncodeUtils.getUTF8StringFromGBKString(errMsg));
		}

		String[] lines = resultMsg.split("\n");
		List<QuotesTenModel> resultList = new ArrayList<>(lines.length - 1);
		QuotesTenModel model;
		for (int i = 0; i < lines.length; i++) {
			// 跳过表头
			if (i > 0) {
				model = new QuotesTenModel(lines[i]);
				resultList.add(model);
			}
		}

		return resultList;

	}

	public synchronized int getConnId() {
		return connId;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String[] infos;
		byte[] errInfo = new byte[256];
		byte[] result = new byte[256];
		boolean flag = false;
		logger.info("链接行情服务器...");
//		this.connId = tdxLibrary.TdxL2Hq_Connect("120.77.76.34", (short) 7709, "DDP18566375526", "11223344", result,
//				errInfo);
		for (String ipStr : stockMarketLv2Ips) {
			infos = ipStr.split(":");
			this.connId = tdxLibrary.TdxL2Hq_Connect(infos[0], Short.parseShort(infos[1]), userName, pwd, result,
					errInfo);
			if (0 > connId || !StringUtils.isEmpty(Native.toString(errInfo, "GBK"))) {
				logger.info("链接行情服务器失败：" + infos[0] + ":" + infos[1]);
				continue;
			} else {
				flag = true;
				logger.info("链接行情服务器成功：" + infos[0] + ":" + infos[1]);
				break;
			}
		}
		if (!flag) {
			throw new RuntimeException("初始化lv行情链接失败，请检查custom.stockMarketLv2设置");
		}

	}

}
