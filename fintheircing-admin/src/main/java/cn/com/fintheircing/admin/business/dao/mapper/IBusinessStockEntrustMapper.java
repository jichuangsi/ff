package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IBusinessStockEntrustMapper {

    @Select("<script>select t1.uuid as id,t2.user_name as userName,t1.stock_num as stockNum,t1.stock_name as stockName,t1.business_to as business,t1.cancel_order as cancelOrder,t1.entrust_status as status,t1.business_amount as amount,t1.business_price as price,t1.deal_no as dealNum,t1.deal_time as dealTime" +
            ",t1.user_id as userId  from " +
            "(select t4.stock_num ,t3.uuid,t3.user_id,t3.delete_flag,t4.stock_name,t3.business_to,t3.cancel_order,t3.entrust_status,t3.business_amount,t3.business_price,t3.deal_no,t3.deal_time from business_stock_entrust t3 LEFT JOIN admin_transaction_summary t4 on t3.stock_id=t4.id)" +
            "t1 LEFT JOIN user_client_info t2 on t1.user_id=t2.uuid <where> t1.delete_flag=\"0\"" +
            "<if test=\"status!=null\"> and t1.entrust_status=#{status} </if></where> ORDER BY created_time</script>")
    List<StockEntrustModel> selectPageEntrusts(StockEntrustModel model);


    @Select("<script>select t1.created_time as ,t1.uuid as id,t1.stock_num as stockNum,t1.stock_name as stock_name,t1.business_amount as amount,t1.business_price as price,t1.business_to as business,t1.entrust_status as status from (select t3.created_time,t3.contract_id,t4.stock_num ,t3.uuid,t3.user_id,t3.delete_flag,t4.stock_name,t3.business_to,t3.cancel_order,t3.entrust_status,t3.business_amount,t3.business_price,t3.deal_no,t3.deal_time from business_stock_entrust t3 LEFT JOIN admin_transaction_summary t4 on t3.stock_id=t4.id where t3.entrust_status in (0,1,2)) t1 LEFT JOIN business_contract t2 on t1.contract_id=t2.uuid where t1.delete_flag=\"0\" and t1.user_id=#{userId} and t2.uuid=#{id}</script>")
    List<StockEntrustModel> selectCancelEntrust(ContractModel model);
}
