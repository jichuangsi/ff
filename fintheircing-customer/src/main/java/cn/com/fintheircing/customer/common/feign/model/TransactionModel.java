package cn.com.fintheircing.customer.common.feign.model;


import cn.com.fintheircing.customer.common.constant.Status;

import java.util.Date;

/**
 * Demo class
 *
 * @author 姚雄
 * @date 2018/12/27
 */
public class TransactionModel {
    private String id;
    private String stockNum;
    private String stockName;
    private String alphabetCapitalization;
    private String martTemplate;
    private Date joinTime;
    private String remake;
    private Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getAlphabetCapitalization() {
        return alphabetCapitalization;
    }

    public void setAlphabetCapitalization(String alphabetCapitalization) {
        this.alphabetCapitalization = alphabetCapitalization;
    }

    public String getMartTemplate() {
        return martTemplate;
    }

    public void setMartTemplate(String martTemplate) {
        this.martTemplate = martTemplate;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
