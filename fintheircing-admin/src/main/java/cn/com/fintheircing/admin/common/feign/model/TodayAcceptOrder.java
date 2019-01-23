package cn.com.fintheircing.admin.common.feign.model;

import cn.com.fintheircing.admin.common.feign.utils.ColUtils;

public class TodayAcceptOrder extends AbstractOrder {

	private String acceptTimeStr;// 成交时间
	private String acceptNum;//成交编号
	private float acceptTotalPrice;//成交金额

	public TodayAcceptOrder(){}

	public TodayAcceptOrder(String lineStr) {
		try {
			String[] cols = lineStr.split("\t");
			setAcceptTimeStr(ColUtils.colProcess(cols[0], String.class));
			setStockCode(ColUtils.colProcess(cols[1], String.class));
			setStockName(ColUtils.colProcess(cols[2], String.class));
			setOperFlag(ColUtils.colProcess(cols[3], String.class));
			setOperName(ColUtils.colProcess(cols[4], String.class));
			setActPrice(ColUtils.colProcess(cols[5], Float.class));
			setActQuantity(ColUtils.colProcess(cols[6], Float.class));
			setAcceptTotalPrice(ColUtils.colProcess(cols[7], Float.class));
			setAcceptNum(ColUtils.colProcess(cols[8], String.class));
			setOrderNumber(ColUtils.colProcess(cols[9], String.class));

			setShareholderCode(ColUtils.colProcess(cols[10], String.class));
			setShareholderCodeType(ColUtils.colProcess(cols[11], String.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("转换当日成交息异常:" + e.getMessage());
		}
	}

	public String getAcceptTimeStr() {
		return acceptTimeStr;
	}

	public void setAcceptTimeStr(String acceptTimeStr) {
		this.acceptTimeStr = acceptTimeStr;
	}

	public String getAcceptNum() {
		return acceptNum;
	}

	public void setAcceptNum(String acceptNum) {
		this.acceptNum = acceptNum;
	}

	public float getAcceptTotalPrice() {
		return acceptTotalPrice;
	}

	public void setAcceptTotalPrice(float acceptTotalPrice) {
		this.acceptTotalPrice = acceptTotalPrice;
	}

}
