package cn.com.fintheircing.exchange.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 证券实体类
 *
 * @author yaoxiong
 * @date 2019/1/18
 */
@Entity
@Table(name ="exchange_ParentAccount")
public class SecuritiesInfo {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String uuid;
    private String qsId;// 券商ID
    private String ip;// 交易服务器IP
    private short port;// 交易服务器端口
    private String version;// 版本号
    private short yybId;// 营业部代码，可设置为0
    private String accountType;// 登录账号类型，一般情况下,8-资金账号,9-客户号
    private String accountNo;// 完整的登录账号，券商一般使用资金帐户或客户号
    private String tradeAccount;// 交易账号，一般与登录帐号相同. 请登录券商通达信软件，查询股东列表，股东列表内的资金帐号就是交易帐号
    private String jyPassword;// 交易密码
    private String txPassword;// 通讯密码，部分券商没有通讯密码，可以为空串
    private String szAccout;// 深圳股东账户
    private String shAccout;// 上海股东账户

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

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
}
