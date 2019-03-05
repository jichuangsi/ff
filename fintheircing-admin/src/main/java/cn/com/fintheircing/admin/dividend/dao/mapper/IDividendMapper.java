package cn.com.fintheircing.admin.dividend.dao.mapper;

import cn.com.fintheircing.admin.dividend.model.DividendControlModel;
import cn.com.fintheircing.admin.dividend.model.DividendModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IDividendMapper {

    @Select("<script>select t2.amount as dividendAmount,t2.happen_time as happenTime,t2.uuid as id,t1.stock_num as stockCode,t1.stock_name as stockName,t1.proxy_num as crossNum,t2.contract_num as contractNo,t2.user_name as userName,t1.created_time as createdTime,t2.contract_id as contractId,t1.stock_id as stockId,t2.ten_stock_cost as cost,t2.ten_stock_money as money,t2.ten_stock_amount as amount,t1.uuid as dividendId from (select t3.*,t4.stock_num,t4.stock_name from (select t5.*,t6.proxy_num from dividend t5 left join admin_client_info t6 on t5.creator_id=t6.uuid where t5.`status`=\"0\") t3 LEFT JOIN admin_transaction_summary t4 on t3.stock_id=t4.id) t1 right join (select t7.*,t8.contract_num,t8.user_name from dividend_relation t7 LEFT JOIN (select t9.uuid,t10.user_name,t9.contract_num from business_contract t9 left join user_client_info t10 on t9.user_id=t10.uuid where t9.contract_status=\"1\") t8 on t7.contract_id=t8.uuid) t2 on t1.uuid=t2.dividend_id <where> t1.delete_flag=\"0\" and t1.`status`=\"0\" " +
            "<if Test='status!=null and status!=\"\"'> and t2.validate_status=#{status}</if>" +
            "<if Test='dividendStatus!=null and dividendStatus!=\"\"'> and t2.dividend_status=#{dividendStatus}</if></where> ORDER BY t1.created_time</script>")
    List<DividendModel> getDividendByStatus(@Param("status") String status,@Param("dividendStatus") String dividendStatus);

    @Select("<script>select t1.uuid as id,t1.validate_time as validateTime,t1.stock_num as stockCode,t1.stock_name as stockName,t2.contract_num as contractNo,t2.user_name as userName,t1.happen_time as happenTime,t1.validate_status as validateStatus,t2.chose_way as choseWay,t1.amount as holding,t1.ten_stock_money as money,t1.ten_stock_amount as amount,t1.ten_stock_cost as cost from (select t5.*,t6.stock_name,t6.stock_num from dividend_relation t5 left join admin_transaction_summary t6 on t5.stock_id=t6.id where t5.validate_status=\"0\") t1 left join (select t3.*,t4.user_name from business_contract t3 left join user_client_info t4 on t3.user_id=t4.uuid) t2 on t1.contract_id=t2.uuid" +
            "<where><if Test='begin!=null and begin!=\"\"'> and t1.validate_time &gt;= #{beginTime}</if>" +
            "<if Test='end!=null and end!=\"\"'> and t1.validate_time &lt;= #{EndTime}</if></where></script>")
    List<DividendControlModel> getDividendControl(DividendControlModel model);
}
