package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ITransactionSunmmaryMapper {

    @Select("<script>select * from transaction_summary where delete_flag=#{delete} and stock_num = #{stockNum}</script>")
    TransactionSummary selectByStockNum(@Param("delete") String delete,@Param("stockNum") String stockNum);
}
