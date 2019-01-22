package cn.com.fintheircing.exchange.dao.mapper;


import cn.com.fintheircing.exchange.model.MotherAccount;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * IParentAccountMapper
 *
 * @author yaoxiong
 * @date 2019/1/18
 */
public interface IParentAccountMapper {
    /**
     * 返回所有启用的母账户
     * @return
     */
    @Select("<script>select t1.qs_id as qsId,t1.ip as ip,t1.port as port t1.version as version,t1.yyb_id as yybId,t1.account_type as accountType,t1.account_no as accountNo,t1.trade——account as tradeAccount,t1.jy_password as jyPassword,t1.tx_password as txPassword,t1.sz_accout as szAccout,t1.sh_accout as shAccout from" +
            " exchange_ParentAccount t1 where t1.status =0</script>")
    List<MotherAccount> findAllParentAccount();
}
