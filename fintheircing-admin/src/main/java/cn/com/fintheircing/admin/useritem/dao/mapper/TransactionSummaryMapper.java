package cn.com.fintheircing.admin.useritem.dao.mapper;

import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * mapper接口
 *
 * @author yaoxiong
 * @date 2016/12/27
 */
public interface TransactionSummaryMapper {
    /**
     * 查询全部
     * @param model
     * @return
     */
    @Select("<script> select t1.stock_num as stockNum" +
            ",t1.stock_name as stockName" +
            ",t1.alphabet_capitalization as alphabetCapitalization" +
            ",t1.mart_template as martTemplate" +
            ",t1.join_time as joinTime" +
            ",t1.remake as remake" +
            ",t1.status as status " +
            " from  admin_transaction_summary t1 " +
            "<where> t1.delete_flag=0  <if test= \"martTemplate!=null and martTemplate!=''\"> and t1.mart_template=#{martTemplate} </if> <if test= \"stockNum!=null and stockNum!=''\"> and t1.stock_num like CONCAT('%',#{stockNum},'%') </if>  and t1.status=0</where> " +
            " </script>")
    List<TransactionModel> findAllByTemplateAndStockName(TransactionModel model);

    /**
     * 更新字段
     * @param model
     * @return int
     */
/*
    @Update("<script>update admin_Transaction_Summary t1 set t1.remark=#{remark} where t1.stock_Id=#{stockId}</script>")
*/
    int updateRemark(TransactionModel model);

    /**
     * 假删除
     * @param id
     * @return int
     */
/*
    @Update("<script>update admin_Transaction_Summary t1 set t1.delete_Flag=\"1\" where t1.stock_Id=#{id}<</script>")
*/
    int updateTransactionSummary(@Param("id") String id);
}
