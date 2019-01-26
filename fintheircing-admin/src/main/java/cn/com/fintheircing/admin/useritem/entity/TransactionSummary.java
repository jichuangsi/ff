package cn.com.fintheircing.admin.useritem.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Demo TransactionSummary
 *
 * @author 姚雄
 * @date 2018/12/27
 */
@Entity
@Table(name ="admin_Transaction_Summary")
public class TransactionSummary extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;
    private String stockNum;
    private String stockName;
    private String alphabetCapitalization;  //名称首字母大写
    private String martTemplate;    //市场模块
    private Date   joinTime=new Date();
    private String remake;
    private Integer status;

    private Double oneDay;
    private Double twoDay;
    private Double threeDay;
    private Double fourDay;
    private Double fiveDay;

    private Integer nowCursor;

    public Integer getNowCursor() {
        return nowCursor;
    }

    public void setNowCursor(Integer nowCursor) {
        this.nowCursor = nowCursor;
    }

    public Double getOneDay() {
        return oneDay;
    }

    public void setOneDay(Double oneDay) {
        this.oneDay = oneDay;
    }

    public Double getTwoDay() {
        return twoDay;
    }

    public void setTwoDay(Double twoDay) {
        this.twoDay = twoDay;
    }

    public Double getThreeDay() {
        return threeDay;
    }

    public void setThreeDay(Double threeDay) {
        this.threeDay = threeDay;
    }

    public Double getFourDay() {
        return fourDay;
    }

    public void setFourDay(Double fourDay) {
        this.fourDay = fourDay;
    }

    public Double getFiveDay() {
        return fiveDay;
    }

    public void setFiveDay(Double fiveDay) {
        this.fiveDay = fiveDay;
    }

    public TransactionSummary() {
    }

    public Integer getStatus() {
        return status;
    }

    public TransactionSummary(String stockNum, String stockName, String alphabetCapitalization, String martTemplate, Date joinTime, String remake, Integer status) {
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.alphabetCapitalization = alphabetCapitalization;
        this.martTemplate = martTemplate;
        this.joinTime = joinTime;
        this.remake = remake;
        this.status = status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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
}
