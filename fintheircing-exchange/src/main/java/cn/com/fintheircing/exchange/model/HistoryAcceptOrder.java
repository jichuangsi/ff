/**
 * 历史成交
 */
package cn.com.fintheircing.exchange.model;

import cn.com.fintheircing.exchange.util.ColUtils;

public class HistoryAcceptOrder extends AbstractOrder {
	private String acceptDateStr;// 成交日期
	private String acceptTimeStr;// 成交时间

	private String acceptNum;// 成交编号
	private float acceptTotalPrice;// 成交金额

	private float commission;// 净佣金
	private float stampTax;// 印花税
	private float transferFee;// 过户费
	private float otherFee;// 其他费
	private float transactionFee;// 交易规费

	private String note;// 备注

	public HistoryAcceptOrder(String lineStr) {
		try {
			String[] cols = lineStr.split("\t");
			setAcceptDateStr(ColUtils.colProcess(cols[0], String.class));
			setAcceptTimeStr(ColUtils.colProcess(cols[1], String.class));
			setStockCode(ColUtils.colProcess(cols[2], String.class));
			setStockName(ColUtils.colProcess(cols[3], String.class));
			setOperFlag(ColUtils.colProcess(cols[4], String.class));
			setOperName(ColUtils.colProcess(cols[5], String.class));
			setActPrice(ColUtils.colProcess(cols[6], Float.class));
			setActQuantity(ColUtils.colProcess(cols[7], Float.class));

			setAcceptNum(ColUtils.colProcess(cols[8], String.class));
			setOrderNumber(ColUtils.colProcess(cols[9], String.class));

			setShareholderCode(ColUtils.colProcess(cols[10], String.class));
			setShareholderCodeType(ColUtils.colProcess(cols[11], String.class));

			setAcceptTotalPrice(ColUtils.colProcess(cols[12], Float.class));
			setCommission(ColUtils.colProcess(cols[13], Float.class));
			setStampTax(ColUtils.colProcess(cols[14], Float.class));
			setTransferFee(ColUtils.colProcess(cols[15], Float.class));
			setOtherFee(ColUtils.colProcess(cols[16], Float.class));
			setNote(ColUtils.colProcess(cols[17], String.class));
			setTransactionFee(ColUtils.colProcess(cols[18], Float.class));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("转换历史成交信息异常:" + e.getMessage());
		}
	}

	public String getAcceptDateStr() {
		return acceptDateStr;
	}

	public void setAcceptDateStr(String acceptDateStr) {
		this.acceptDateStr = acceptDateStr;
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

	public float getCommission() {
		return commission;
	}

	public void setCommission(float commission) {
		this.commission = commission;
	}

	public float getStampTax() {
		return stampTax;
	}

	public void setStampTax(float stampTax) {
		this.stampTax = stampTax;
	}

	public float getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(float transferFee) {
		this.transferFee = transferFee;
	}

	public float getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(float otherFee) {
		this.otherFee = otherFee;
	}

	public float getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(float transactionFee) {
		this.transactionFee = transactionFee;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
