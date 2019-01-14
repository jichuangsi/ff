/**
 * 历史委托信息
 */
package cn.com.fintheircing.exchange.model;

import cn.com.fintheircing.exchange.util.ColUtils;

/**
 * @author huangjiajun
 *
 */
public class HistoryOrder extends AbstractOrder {
	private String timeStr;
	private String dateStr;
	
	public HistoryOrder(String lineStr) {
		try {
			String[] cols = lineStr.split("\t");
			setTimeStr(ColUtils.colProcess(cols[0], String.class));
			setDateStr(ColUtils.colProcess(cols[1], String.class));
			setStockCode(ColUtils.colProcess(cols[2], String.class));
			setStockName(ColUtils.colProcess(cols[3], String.class));
			setOperFlag(ColUtils.colProcess(cols[4], String.class));
			setOperName(ColUtils.colProcess(cols[5], String.class));
			setStatus(ColUtils.colProcess(cols[6], String.class));
			setOrderPrice(ColUtils.colProcess(cols[7], Float.class));
			setOrderQuantity(ColUtils.colProcess(cols[8], Float.class));
			setOrderNumber(ColUtils.colProcess(cols[9], String.class));
			setActPrice(ColUtils.colProcess(cols[10], Float.class));
			setActQuantity(ColUtils.colProcess(cols[11], Float.class));

			setOrderType(ColUtils.colProcess(cols[12], String.class));
			setQuoteName(ColUtils.colProcess(cols[13], String.class));
			setShareholderCode(ColUtils.colProcess(cols[14], String.class));
			setShareholderCodeType(ColUtils.colProcess(cols[15], String.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("转换历史委托单信息异常:"+e.getMessage());
		}
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

}
