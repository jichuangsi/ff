/**
 * 10档行情报价
 */
package cn.com.fintheircing.admin.common.feign.model;

import cn.com.fintheircing.admin.common.feign.utils.ColUtils;

public class QuotesTenModel {
	private String exchangeId; // 0 深圳，1 上海
	private String stockCode;// 证券代码
	private String active;// 活跃度
	private float curPrice;// 现价
	private float closePrice;// 昨日收盘价
	private float openPrice;// 今天开盘价
	private float crestPrice;// 今天最高价
	private float bottomPrice;// 今天最低价

	private String timeStr;// 时间
	private String reserved1;// 保留1

	private int totalQuantity;// 总量
	private int curQuantity;// 现量
	private float amount;// 总金额
	private float inside;// 内盘
	private float outside;// 外盘

	private String reserved2;// 保留2
	private String reserved3;// 保留3

	private float[] buyTenPrice = new float[10];// 买一到买十价格，index为0是买一，index为1时为买二，依次类推
	private int[] buyTenQuantity = new int[10];// 买一到买十数量，index规则同上
	private float[] sellTenPrice = new float[10];// 卖一到卖十，index为0是卖一，index为1时为卖二，依次类推
	private int[] sellTenQuantity = new int[10];// 卖一到卖十数量，index规则同上

	private String reserved4;// 保留4
	private String transactionNumber;// 总笔数

	private float buyAvgPrice;// 买均价
	private float sellAvgPrice;// 卖均价

	private float buyAmount;// 总买
	private float sellAmount;// 总卖

	public QuotesTenModel(){}

	public QuotesTenModel(String lineStr) {
		try {
			String[] cols = lineStr.split("\t");
			setExchangeId(ColUtils.colProcess(cols[0], String.class));
			setStockCode(ColUtils.colProcess(cols[1], String.class));
			setActive(ColUtils.colProcess(cols[2], String.class));
			setCurPrice(ColUtils.colProcess(cols[3], Float.class));
			setClosePrice(ColUtils.colProcess(cols[4], Float.class));
			setOpenPrice(ColUtils.colProcess(cols[5], Float.class));
			setCrestPrice(ColUtils.colProcess(cols[6], Float.class));
			setBottomPrice(ColUtils.colProcess(cols[7], Float.class));

			setTimeStr(ColUtils.colProcess(cols[8], String.class));
			setReserved1(ColUtils.colProcess(cols[9], String.class));

			setTotalQuantity(ColUtils.colProcess(cols[10], Integer.class));
			setCurQuantity(ColUtils.colProcess(cols[11], Integer.class));
			setAmount(ColUtils.colProcess(cols[12], Float.class));
			setInside(ColUtils.colProcess(cols[13], Float.class));
			setOutside(ColUtils.colProcess(cols[14], Float.class));

			setReserved2(ColUtils.colProcess(cols[15], String.class));
			setReserved3(ColUtils.colProcess(cols[16], String.class));

			buyTenPrice[0] = ColUtils.colProcess(cols[17], Float.class);
			sellTenPrice[0] = ColUtils.colProcess(cols[18], Float.class);
			buyTenQuantity[0] = ColUtils.colProcess(cols[19], Integer.class);
			sellTenQuantity[0] = ColUtils.colProcess(cols[20], Integer.class);

			buyTenPrice[1] = ColUtils.colProcess(cols[21], Float.class);
			sellTenPrice[1] = ColUtils.colProcess(cols[22], Float.class);
			buyTenQuantity[1] = ColUtils.colProcess(cols[23], Integer.class);
			sellTenQuantity[1] = ColUtils.colProcess(cols[24], Integer.class);

			buyTenPrice[2] = ColUtils.colProcess(cols[25], Float.class);
			sellTenPrice[2] = ColUtils.colProcess(cols[26], Float.class);
			buyTenQuantity[2] = ColUtils.colProcess(cols[27], Integer.class);
			sellTenQuantity[2] = ColUtils.colProcess(cols[28], Integer.class);

			buyTenPrice[3] = ColUtils.colProcess(cols[29], Float.class);
			sellTenPrice[3] = ColUtils.colProcess(cols[30], Float.class);
			buyTenQuantity[3] = ColUtils.colProcess(cols[31], Integer.class);
			sellTenQuantity[3] = ColUtils.colProcess(cols[32], Integer.class);

			buyTenPrice[4] = ColUtils.colProcess(cols[33], Float.class);
			sellTenPrice[4] = ColUtils.colProcess(cols[34], Float.class);
			buyTenQuantity[4] = ColUtils.colProcess(cols[35], Integer.class);
			sellTenQuantity[4] = ColUtils.colProcess(cols[36], Integer.class);

			setReserved4(ColUtils.colProcess(cols[37], String.class));
			setTransactionNumber(ColUtils.colProcess(cols[38], String.class));

			buyTenPrice[5] = ColUtils.colProcess(cols[42], Float.class);
			sellTenPrice[5] = ColUtils.colProcess(cols[43], Float.class);
			buyTenQuantity[5] = ColUtils.colProcess(cols[44], Integer.class);
			sellTenQuantity[5] = ColUtils.colProcess(cols[45], Integer.class);

			buyTenPrice[6] = ColUtils.colProcess(cols[46], Float.class);
			sellTenPrice[6] = ColUtils.colProcess(cols[47], Float.class);
			buyTenQuantity[6] = ColUtils.colProcess(cols[48], Integer.class);
			sellTenQuantity[6] = ColUtils.colProcess(cols[49], Integer.class);

			buyTenPrice[7] = ColUtils.colProcess(cols[50], Float.class);
			sellTenPrice[7] = ColUtils.colProcess(cols[51], Float.class);
			buyTenQuantity[7] = ColUtils.colProcess(cols[52], Integer.class);
			sellTenQuantity[7] = ColUtils.colProcess(cols[53], Integer.class);

			buyTenPrice[8] = ColUtils.colProcess(cols[54], Float.class);
			sellTenPrice[8] = ColUtils.colProcess(cols[55], Float.class);
			buyTenQuantity[8] = ColUtils.colProcess(cols[56], Integer.class);
			sellTenQuantity[8] = ColUtils.colProcess(cols[57], Integer.class);

			buyTenPrice[9] = ColUtils.colProcess(cols[58], Float.class);
			sellTenPrice[9] = ColUtils.colProcess(cols[59], Float.class);
			buyTenQuantity[9] = ColUtils.colProcess(cols[60], Integer.class);
			sellTenQuantity[9] = ColUtils.colProcess(cols[61], Integer.class);

			setBuyAvgPrice(ColUtils.colProcess(cols[62], Float.class));
			setSellAvgPrice(ColUtils.colProcess(cols[63], Float.class));

			setBuyAmount(ColUtils.colProcess(cols[64], Float.class));
			setSellAmount(ColUtils.colProcess(cols[65], Float.class));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("0档行情报价信息异常:" + e.getMessage());
		}
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public float getCurPrice() {
		return curPrice;
	}

	public void setCurPrice(float curPrice) {
		this.curPrice = curPrice;
	}

	public float getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}

	public float getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}

	public float getCrestPrice() {
		return crestPrice;
	}

	public void setCrestPrice(float crestPrice) {
		this.crestPrice = crestPrice;
	}

	public float getBottomPrice() {
		return bottomPrice;
	}

	public void setBottomPrice(float bottomPrice) {
		this.bottomPrice = bottomPrice;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getCurQuantity() {
		return curQuantity;
	}

	public void setCurQuantity(int curQuantity) {
		this.curQuantity = curQuantity;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getInside() {
		return inside;
	}

	public void setInside(float inside) {
		this.inside = inside;
	}

	public float getOutside() {
		return outside;
	}

	public void setOutside(float outside) {
		this.outside = outside;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public float[] getBuyTenPrice() {
		return buyTenPrice;
	}

	public void setBuyTenPrice(float[] buyTenPrice) {
		this.buyTenPrice = buyTenPrice;
	}

	public int[] getBuyTenQuantity() {
		return buyTenQuantity;
	}

	public void setBuyTenQuantity(int[] buyTenQuantity) {
		this.buyTenQuantity = buyTenQuantity;
	}

	public float[] getSellTenPrice() {
		return sellTenPrice;
	}

	public void setSellTenPrice(float[] sellTenPrice) {
		this.sellTenPrice = sellTenPrice;
	}

	public int[] getSellTenQuantity() {
		return sellTenQuantity;
	}

	public void setSellTenQuantity(int[] sellTenQuantity) {
		this.sellTenQuantity = sellTenQuantity;
	}

	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public float getBuyAvgPrice() {
		return buyAvgPrice;
	}

	public void setBuyAvgPrice(float buyAvgPrice) {
		this.buyAvgPrice = buyAvgPrice;
	}

	public float getSellAvgPrice() {
		return sellAvgPrice;
	}

	public void setSellAvgPrice(float sellAvgPrice) {
		this.sellAvgPrice = sellAvgPrice;
	}

	public float getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(float buyAmount) {
		this.buyAmount = buyAmount;
	}

	public float getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(float sellAmount) {
		this.sellAmount = sellAmount;
	}

}
