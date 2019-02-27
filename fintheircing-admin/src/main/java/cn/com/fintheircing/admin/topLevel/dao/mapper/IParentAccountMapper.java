package cn.com.fintheircing.admin.topLevel.dao.mapper;

import cn.com.fintheircing.admin.topLevel.model.HodingStockModel;
import cn.com.fintheircing.admin.topLevel.model.ParentAccountModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * IParentAccountMapper
 *
 * @author yaoxiong
 * @date 2019/1/18
 */
public interface IParentAccountMapper {
    /**
     * 查询所有的母账户
     * @return
     */
    @Select("<script>select t1.trade_account as tradeAccount,t1.trade_name as tradeName,t1.begin_amount as beginAmount,t1.Securities as Securities,t1.create_time as createTime, t1.amount as amount " +
            " t1.market_value as marketValue from exchange_parent_account t1</script>")
    List<ParentAccountModel> findAllParentAccount();

    /**
     * 开启母账户
     * @param id
     * @return
    */
    @Update("<script>update exchange_parent_account t1 set t1.status=0 where t1.uuid=#{id}</script>")
    int openParentAccount(String id);

    /**
     * 关闭母账户
     * @param id
     * @return
     */
    @Update("<script>update exchange_parent_account t1 set t1.status=1 where t1.uuid=#{id}</script>")
    int closeParentAccount(String id);

    /**
     * 持仓差异表,查询product ,transactionSummary
     * @param stockCode
     */
    @Select("<script> select t1.stock_num as stockNum , t1.stock_name as stockName,t2.account_id as parentId," +
            " t2.trade_account as parentAccountId " +
            " from admin_Transaction_Summary t1 left join exchange_ParentAccount t2 on and t1.parent_id=t2.uuid" +
            "where t1.stock_num=#{stockCode} </script>")
    HodingStockModel QueryHodingAndAccount(String stockCode);
}
