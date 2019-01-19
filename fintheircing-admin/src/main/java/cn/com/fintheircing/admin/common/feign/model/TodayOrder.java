/**
 * 当天委托
 */
package cn.com.fintheircing.admin.common.feign.model;

import cn.com.fintheircing.admin.common.feign.utils.ColUtils;

public class TodayOrder extends AbstractOrder {

	private String timeStr;// 委托时间
	private Float cancleQuantity;// 撤单数量

	public TodayOrder(String lineStr) {
		try {
			String[] cols = lineStr.split("\t");
			setTimeStr(ColUtils.colProcess(cols[0], String.class));
			setStockCode(ColUtils.colProcess(cols[1], String.class));
			setStockName(ColUtils.colProcess(cols[2], String.class));
			setOperFlag(ColUtils.colProcess(cols[3], String.class));
			setOperName(ColUtils.colProcess(cols[4], String.class));
			setOrderTypeName(ColUtils.colProcess(cols[5], String.class));
			setStatus(ColUtils.colProcess(cols[6], String.class));
			setOrderPrice(ColUtils.colProcess(cols[7], Float.class));
			setOrderQuantity(ColUtils.colProcess(cols[8], Float.class));
			setOrderNumber(ColUtils.colProcess(cols[9], String.class));
			setActPrice(ColUtils.colProcess(cols[10], Float.class));
			setActQuantity(ColUtils.colProcess(cols[11], Float.class));

			setCancleQuantity(ColUtils.colProcess(cols[12], Float.class));
			setOrderType(ColUtils.colProcess(cols[13], String.class));
			setQuoteName(ColUtils.colProcess(cols[14], String.class));
			setShareholderCode(ColUtils.colProcess(cols[15], String.class));
			setShareholderCodeType(ColUtils.colProcess(cols[16], String.class));
			setExchangeId(ColUtils.colProcess(cols[17], String.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("转换当日委托单信息异常:" + e.getMessage());
		}
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public Float getCancleQuantity() {
		return cancleQuantity;
	}

	public void setCancleQuantity(Float cancleQuantity) {
		this.cancleQuantity = cancleQuantity;
	}

}
