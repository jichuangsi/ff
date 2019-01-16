package cn.com.fintheircing.admin.business.model;

public class StockModel {

    private String stockNo;     //股票编码
    private String stockName;      //股票名称
    private Double todayOpen;   //今日开市
    private Double yesterdayClose;      //昨日收市
    private Double nowPrice;    //现在价钱
    private Double todayMax;    //今日最高
    private Double todayMin;    //今日最低
    private Double competeBuy;      //竞买
    private Double competeSell;     //竞卖
    private Integer dealStockNum;   //成交股票数量
    private Integer dealStockMoney;     //成交金额

    private Integer buyOneNum;      //买一数量
    private Double buyOnePrice;     //买一单价
    private Integer buyTwoNum;
    private Double buyTwoPrice;
    private Integer buyThreeNum;
    private Double buyThreePrice;
    private Integer buyFourNum;
    private Double buyFourPrice;
    private Integer buyFiveNum;
    private Double buyFivePrice;

    private Integer sellOneNum;     //卖一数量
    private Double sellOnePrice;    //卖一单价
    private Integer sellTwoNum;
    private Double sellTwoPrice;
    private Integer sellThreeNum;
    private Double sellThreePrice;
    private Integer sellFourNum;
    private Double sellFourPrice;
    private Integer sellFiveNum;
    private Double sellFivePrice;

    private String date;    //日期
    private String dateTime;    //时间

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getTodayOpen() {
        return todayOpen;
    }

    public void setTodayOpen(Double todayOpen) {
        this.todayOpen = todayOpen;
    }

    public Double getYesterdayClose() {
        return yesterdayClose;
    }

    public void setYesterdayClose(Double yesterdayClose) {
        this.yesterdayClose = yesterdayClose;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Double getTodayMax() {
        return todayMax;
    }

    public void setTodayMax(Double todayMax) {
        this.todayMax = todayMax;
    }

    public Double getTodayMin() {
        return todayMin;
    }

    public void setTodayMin(Double todayMin) {
        this.todayMin = todayMin;
    }

    public Double getCompeteBuy() {
        return competeBuy;
    }

    public void setCompeteBuy(Double competeBuy) {
        this.competeBuy = competeBuy;
    }

    public Double getCompeteSell() {
        return competeSell;
    }

    public void setCompeteSell(Double competeSell) {
        this.competeSell = competeSell;
    }

    public Integer getDealStockNum() {
        return dealStockNum;
    }

    public void setDealStockNum(Integer dealStockNum) {
        this.dealStockNum = dealStockNum;
    }

    public Integer getDealStockMoney() {
        return dealStockMoney;
    }

    public void setDealStockMoney(Integer dealStockMoney) {
        this.dealStockMoney = dealStockMoney;
    }

    public Integer getBuyOneNum() {
        return buyOneNum;
    }

    public void setBuyOneNum(Integer buyOneNum) {
        this.buyOneNum = buyOneNum;
    }

    public Double getBuyOnePrice() {
        return buyOnePrice;
    }

    public void setBuyOnePrice(Double buyOnePrice) {
        this.buyOnePrice = buyOnePrice;
    }

    public Integer getBuyTwoNum() {
        return buyTwoNum;
    }

    public void setBuyTwoNum(Integer buyTwoNum) {
        this.buyTwoNum = buyTwoNum;
    }

    public Double getBuyTwoPrice() {
        return buyTwoPrice;
    }

    public void setBuyTwoPrice(Double buyTwoPrice) {
        this.buyTwoPrice = buyTwoPrice;
    }

    public Integer getBuyThreeNum() {
        return buyThreeNum;
    }

    public void setBuyThreeNum(Integer buyThreeNum) {
        this.buyThreeNum = buyThreeNum;
    }

    public Double getBuyThreePrice() {
        return buyThreePrice;
    }

    public void setBuyThreePrice(Double buyThreePrice) {
        this.buyThreePrice = buyThreePrice;
    }

    public Integer getBuyFourNum() {
        return buyFourNum;
    }

    public void setBuyFourNum(Integer buyFourNum) {
        this.buyFourNum = buyFourNum;
    }

    public Double getBuyFourPrice() {
        return buyFourPrice;
    }

    public void setBuyFourPrice(Double buyFourPrice) {
        this.buyFourPrice = buyFourPrice;
    }

    public Integer getBuyFiveNum() {
        return buyFiveNum;
    }

    public void setBuyFiveNum(Integer buyFiveNum) {
        this.buyFiveNum = buyFiveNum;
    }

    public Double getBuyFivePrice() {
        return buyFivePrice;
    }

    public void setBuyFivePrice(Double buyFivePrice) {
        this.buyFivePrice = buyFivePrice;
    }

    public Integer getSellOneNum() {
        return sellOneNum;
    }

    public void setSellOneNum(Integer sellOneNum) {
        this.sellOneNum = sellOneNum;
    }

    public Double getSellOnePrice() {
        return sellOnePrice;
    }

    public void setSellOnePrice(Double sellOnePrice) {
        this.sellOnePrice = sellOnePrice;
    }

    public Integer getSellTwoNum() {
        return sellTwoNum;
    }

    public void setSellTwoNum(Integer sellTwoNum) {
        this.sellTwoNum = sellTwoNum;
    }

    public Double getSellTwoPrice() {
        return sellTwoPrice;
    }

    public void setSellTwoPrice(Double sellTwoPrice) {
        this.sellTwoPrice = sellTwoPrice;
    }

    public Integer getSellThreeNum() {
        return sellThreeNum;
    }

    public void setSellThreeNum(Integer sellThreeNum) {
        this.sellThreeNum = sellThreeNum;
    }

    public Double getSellThreePrice() {
        return sellThreePrice;
    }

    public void setSellThreePrice(Double sellThreePrice) {
        this.sellThreePrice = sellThreePrice;
    }

    public Integer getSellFourNum() {
        return sellFourNum;
    }

    public void setSellFourNum(Integer sellFourNum) {
        this.sellFourNum = sellFourNum;
    }

    public Double getSellFourPrice() {
        return sellFourPrice;
    }

    public void setSellFourPrice(Double sellFourPrice) {
        this.sellFourPrice = sellFourPrice;
    }

    public Integer getSellFiveNum() {
        return sellFiveNum;
    }

    public void setSellFiveNum(Integer sellFiveNum) {
        this.sellFiveNum = sellFiveNum;
    }

    public Double getSellFivePrice() {
        return sellFivePrice;
    }

    public void setSellFivePrice(Double sellFivePrice) {
        this.sellFivePrice = sellFivePrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
