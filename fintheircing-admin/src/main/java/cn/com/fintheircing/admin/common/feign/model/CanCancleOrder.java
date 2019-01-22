/**
 * 可撤单的委托单信息
 *
 */

package cn.com.fintheircing.admin.common.feign.model;

import cn.com.fintheircing.admin.common.feign.utils.ColUtils;

public class CanCancleOrder extends AbstractOrder {

	private String timestamp;// 委托时间

	public CanCancleOrder() {
	}

	public CanCancleOrder(String lineStr) {
		try {
			String[] cols = lineStr.split("\t");
			setTimestamp(ColUtils.colProcess(cols[0], String.class));
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

			setOrderType(ColUtils.colProcess(cols[12], String.class));
			setQuoteName(ColUtils.colProcess(cols[13], String.class));
			setShareholderCode(ColUtils.colProcess(cols[14], String.class));
			setShareholderCodeType(ColUtils.colProcess(cols[15], String.class));
			setExchangeId(ColUtils.colProcess(cols[16], String.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("转换可撤销委托单信息异常:" + e.getMessage());
		}
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
