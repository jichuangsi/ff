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
    @Select("<script>select t1.stock_Id as stockId" +
            ",t1.stockName as stockName" +
            ",t1.alphabetCapitalization as alphabetCapitalization" +
            ",t1.martTemplate as martTemplate" +
            ",t1.joinTime as joinTime" +
            ",t1.remake as remake" +
            ",t1.status as status," +
            " from  Transaction_Summary t1" +
            "<where> t1.delete_flag=\"0\"  <if test= \"martTemplate!=null and martTemplate!=''\"> and t1.mart_Template=#{martTemplate} </if> <if test= \"stockId'!=null and stockId!=''\"> and t1.stock_Id LIKE CONCAT('%',#{stockId},'%') </if>  and t1.status=\"3\"</where>" +
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
