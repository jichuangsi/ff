/**
 * lv2股票行情服务
 */

package cn.com.fintheircing.stockprice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import sun.rmi.runtime.Log;

@Service
public class StockPriceLv2Service implements InitializingBean {

	private ITdxL2HqLibrary tdxLibrary = (ITdxL2HqLibrary) Native.loadLibrary("TradeX2-M", ITdxL2HqLibrary.class);
	private int connId;
	private ScheduledExecutorService threadPool;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Boolean isConnectOk;
	private String curIp;

	@Value("${custom.stockMarketLv2replySeconds}")
	private int replySeconds = 60;// 心跳间隔

	@Value("${custom.stockMarketLv2Ips}")
	private String[] stockMarketLv2Ips;
	@Value("${custom.stockMarketLv2UserName}")
	private String userName;
	@Value("${custom.stockMarketLv2Pwd}")
	private String pwd;

	@Value("${custom.stockMarketMaxCount}")
	private int maxCount = 50;

	/**
	 * lv2股票行情服务
	 */
	public synchronized List<QuotesTenModel> getQuotesTen(char[] markets, String[] stockCodes) {
		byte[] errInfo = new byte[256];
		byte[] result = new byte[2048 * maxCount];
		if (markets.length > maxCount) {
			throw new RuntimeException("一次最多查询" + maxCount + "支股票");
		}

		// 检查连接是否可用并自动重连
		if (!checkAndReconnect()) {
			throw new RuntimeException("连接检测到不可用且自动重连失败");
		}

		ShortByReference count = new ShortByReference((short) markets.length);
		boolean flag = tdxLibrary.TdxL2Hq_GetSecurityQuotes10(getConnId(), markets, stockCodes, count, result, errInfo);

		String errMsg = Native.toString(errInfo, "GBK");
		String resultMsg = Native.toString(result, "GBK");
		if (!flag || !StringUtils.isEmpty(errMsg) || StringUtils.isEmpty(resultMsg)) {
			isConnectOk = false;// 仅当此方法加了synchronized，可以这样，要不有同步问题
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
		if (!connect()) {
			throw new RuntimeException("初始化lv行情链接失败，请检查custom.stockMarketLv2设置");
		}
		// 加入心跳
		if (0 < replySeconds) {
			this.threadPool = Executors.newScheduledThreadPool(1);
			threadPool.scheduleAtFixedRate(() -> {
				synchronized (this) {
					logger.debug("心跳维持连接" + replySeconds + "s一次...");
					char[] markets = { 1 };
					String[] stockCodes = { "601318" };
					try {
						List<QuotesTenModel> list = getQuotesTen(markets, stockCodes);
						if (null == list || list.size() < 1) {
							isConnectOk = false;
						} else {
							isConnectOk = true;
						}
					} catch (Exception e) {
						isConnectOk = false;
						e.printStackTrace();
					}

				}

			}, 1, replySeconds, TimeUnit.SECONDS);
		}

	}

	private synchronized boolean connect() {
		String[] infos;
		byte[] errInfo;
		byte[] result = new byte[256];
		logger.info("链接行情服务器...");
		for (String ipStr : stockMarketLv2Ips) {

			infos = ipStr.split(":");
			if (infos[0].equals(curIp)) {
				continue;
			}

			errInfo = new byte[256];
			this.connId = tdxLibrary.TdxL2Hq_Connect(infos[0], Short.parseShort(infos[1]), userName, pwd, result,
					errInfo);
			String errMsg = Native.toString(errInfo, "GBK");
			if (0 > connId || !StringUtils.isEmpty(errMsg)) {
				logger.info("链接行情服务器失败：" + infos[0] + ":" + infos[1] + "--"
						+ EncodeUtils.getUTF8StringFromGBKString(errMsg));
				tdxLibrary.TdxL2Hq_Disconnect(this.connId);
				this.isConnectOk = false;
				continue;
			} else {
				logger.info("链接行情服务器成功：" + infos[0] + ":" + infos[1]);
				curIp = infos[0];
				this.isConnectOk = true;
				return true;
			}
		}
		return false;
	}

	private boolean checkAndReconnect() {
		if (!isConnectOk) {

			synchronized (this) {
				int i = 0;
				// 双重检查，防止重复执行
				while (!isConnectOk && i < 5) {
					logger.info("尝试断开连接并重连");
					tdxLibrary.TdxL2Hq_Disconnect(getConnId());
					if (connect()) {
						break;
					}
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						i++;
					}
				}
			}
		}
		return isConnectOk;

	}

}
