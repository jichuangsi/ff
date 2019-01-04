package cn.com.fintheircing.admin.useritem.entity;

import cn.com.fintheircing.admin.common.entity.AbstractEntity;
import cn.com.fintheircing.admin.useritem.common.Status;
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
    private String stockId;
    private String stockName;
    private String alphabetCapitalization;
    private String martTemplate;
    private Date   joinTime;
    private String remake;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
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
