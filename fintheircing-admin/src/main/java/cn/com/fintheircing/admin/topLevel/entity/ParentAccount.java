package cn.com.fintheircing.admin.topLevel.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * 母账户
 *
 * @author yaoxiong
 * @date 2019/1/18
 */
@Entity
@Table(name = "exchange_ParentAccount")
public class ParentAccount {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String tradeAccount;//交易账号
    private String passWord;
    private String jyPassword;//交易密码
    private String txPassword;// 通讯密码，部分券商没有通讯密码，可以为空串
    private String accountType;// 登录账号类型，一般情况下,8-资金账号,9-客户号
    private String tradeName;//交易持有人
    private double beginAmount;//初期金额
    private double promiseAmount;//保证金金额
    private double loanAmount;//借款金额
    private String Securities;//证券
    private Date createTime=new Date();
    private double amount;//账户资金
    private double marketValue;//持仓市值

    private String tradeCode;//交易代码
    private String qsId;// 券商ID
    private String ip;// 交易服务器IP
    private short port;// 交易服务器端口
    private String version;// 版本号
    private short yybId;// 营业部代码，可设置为0
    private String accountNo;// 完整的登录账号，券商一般使用资金帐户或客户号
    private String szAccout;// 深圳股东账户
    private String shAccout;// 上海股东账户
    private int status=0;

    public String getJyPassword() {
        return jyPassword;
    }

    public void setJyPassword(String jyPassword) {
        this.jyPassword = jyPassword;
    }

    public String getTxPassword() {
        return txPassword;
    }

    public void setTxPassword(String txPassword) {
        this.txPassword = txPassword;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getQsId() {
        return qsId;
    }

    public void setQsId(String qsId) {
        this.qsId = qsId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public short getYybId() {
        return yybId;
    }

    public void setYybId(short yybId) {
        this.yybId = yybId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSzAccout() {
        return szAccout;
    }

    public void setSzAccout(String szAccout) {
        this.szAccout = szAccout;
    }

    public String getShAccout() {
        return shAccout;
    }

    public void setShAccout(String shAccout) {
        this.shAccout = shAccout;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public double getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(double beginAmount) {
        this.beginAmount = beginAmount;
    }

    public double getPromiseAmount() {
        return promiseAmount;
    }

    public void setPromiseAmount(double promiseAmount) {
        this.promiseAmount = promiseAmount;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getSecurities() {
        return Securities;
    }

    public void setSecurities(String securities) {
        Securities = securities;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
