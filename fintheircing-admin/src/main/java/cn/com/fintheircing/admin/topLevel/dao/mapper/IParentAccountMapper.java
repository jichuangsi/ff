package cn.com.fintheircing.admin.topLevel.dao.mapper;

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
            " t1.market_value as marketValue from exchange_ParentAccount t1</script>")
    List<ParentAccountModel> findAllParentAccount();

    /**
     * 开启母账户
     * @param id
     * @return
     */
    @Update("<script></script>")
    int openParentAccount(String id);
    /**
     * 关闭母账户
     * @param id
     * @return
     */
    @Update("<script></script>")
    int closeParentAccount(String id);
}
