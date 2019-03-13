package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferEntrustModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IBusinessStockEntrustMapper {

    @Select("<script>select t1.uuid as id,t2.user_name as userName,t1.stock_num as stockNum,t1.stock_name as stockName,t1.business_to as business,t1.cancel_order as cancelOrder,t1.entrust_status as status,t1.business_amount as amount,t1.business_price as price,t1.deal_no as dealNum,t1.deal_time as dealTime" +
            ",t1.user_id as userId  from " +
            "(select t4.stock_num ,t3.uuid,t3.user_id,t3.delete_flag,t4.stock_name,t3.business_to,t3.cancel_order,t3.entrust_status,t3.business_amount,t3.business_price,t3.deal_no,t3.deal_time,t3.contract_id from business_stock_entrust t3 LEFT JOIN admin_transaction_summary t4 on t3.stock_id=t4.id)" +
            "t1 LEFT JOIN user_client_info t2 on t1.user_id=t2.uuid <where> t1.delete_flag=\"0\"" +
            "<if test=\"status!=null\"> and t1.entrust_status=#{status} </if>" +
            "<if test='list.size > 0'> and ,t1.contract_id in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "    #{item}   </foreach></if></where> ORDER BY created_time</script>")
    List<StockEntrustModel> selectPageEntrusts(Map<String,Object> params);

    @Select("<script>select t1.created_time as createdTime,t1.uuid as id,t1.stock_num as stockNum,t1.stock_name as stockName,t1.business_amount as amount,t1.business_price as price,t1.business_to as business,t1.entrust_status as status,t1.deal_no as dealNo from (select t3.created_time,t3.contract_id,t4.stock_num ,t3.uuid,t3.user_id,t3.delete_flag,t4.stock_name,t3.business_to,t3.cancel_order,t3.entrust_status,t3.business_amount,t3.business_price,t3.deal_no,t3.deal_time from business_stock_entrust t3 LEFT JOIN admin_transaction_summary t4 on t3.stock_id=t4.id where t3.entrust_status in (0,1,2) and t3.deal_from=\"0\") t1 LEFT JOIN business_contract t2 on t1.contract_id=t2.uuid where t1.delete_flag=\"0\" and t1.user_id=#{userId} and t2.uuid=#{id}</script>")
    List<StockEntrustModel> selectCancelEntrust(ContractModel model);

    @Select("<script>select t1.business_to as business,t1.created_time as createdTime,t1.business_amount as amount,t1.deal_num as dealNum,t1.deal_price as dealPrice,t1.contract_num as contractNum,t1.entrust_status as status,t1.deal_time as dealTime ,t2.stock_num as stockNum,t2.stock_name as stockName,t1.business_price as price from (select t4.stock_id,t4.business_to,t4.created_time,t4.business_amount,t4.deal_num,t4.deal_price,t3.contract_num,t4.entrust_status,t4.deal_time,t4.business_price from  business_contract t3 right JOIN business_stock_entrust t4 on t3.uuid=t4.contract_id where t3.uuid=#{contractId} and t3.delete_flag=\"0\" and t4.delete_flag=\"0\") t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id" +
            "<where>" +
            "<if test='stockNum!=null and stockNum!=\"\"'>  t2.stock_num like CONCAT(\"%\",#{stockNum},\"%\")" +
            " or t2.stock_name like concat(\"%\",#{stockNum},\"%\")</if>" +
            "</where> order by t1.created_time</script>")
    List<StockEntrustModel> getContractEntrusts(StockEntrustModel model);

    @Select("<script>select t1.uuid as id,t1.contract_id as contractId,t1.deal_no as dealNo,t2.stock_num as stockNum  from business_stock_entrust t1 left join admin_transaction_summary t2 on t1.stock_id=t2.id where t1.contract_id=#{contractId} and t1.entrust_status in (0,1,2) and t1.deal_from=\"0\" and t1.delete_flag=\"0\" and t1.creator_id not like CONCAT(\"%\",\"admin\",\"%\")</script>")
    List<StockEntrustModel> getCancelEntrust(@Param("contractId") String contractId);

    @Select("<script>select t6.stock_num as stockNum,t6.stock_name as stockName,t5.business_amount as amount,t5.business_money as businessMoney,t5.business_price as  price,t5.business_to as business,t5.created_time as createdTime,t5.deal_no as dealNo,t5.deal_num as dealNum,t5.deal_price as dealPrice,t5.deal_time as dealTime,t5.entrust_status as status,t5.monther_account as motherAccount,t5.taxation_money as taxationMoney,t5.contract_num as contractNum,t5.phone as userPhone,t5.displayname as name,t5.chose_way as choseWay,t5.contractId as contractId,t5.uuid as id,t5.user_name as userName,t5.productId from (select t2.*,t1.stock_id,t1.business_to,t1.business_price,t1.business_amount,t1.deal_no,t1.deal_num,t1.deal_price,t1.deal_time,t1.created_time,t1.monther_account,t1.entrust_status,t1.business_money,t1.taxation_money,t1.uuid,t2.product_id as productId from business_stock_entrust t1 left join (select t3.uuid as contractId,t3.contract_num,t4.phone,t4.displayname,t3.chose_way,t4.user_name,t3.product_id,t4.uuid as userId from business_contract t3 left join user_client_info t4 on t3.user_id=t4.uuid) t2 on t1.contract_id=t2.contractId) t5 LEFT JOIN admin_transaction_summary t6 on t5.stock_id=t6.id" +
            " <where><if test='stockNum!=null and stockNum!=\"\"'> and t6.stock_num like concat(\"%\",#{stockNum},\"%\")</if>" +
            "<if test='stockName!=null and stockName!=\"\"'> and t6.stock_name like concat(\"%\",#{stockName},\"%\")</if>" +
            "<if test='contractNum!=null and contractNum!=\"\"'> and t5.contract_num like concat(\"%\",#{contractNum},\"%\")</if>" +
            "<if test='userName!=null and userName!=\"\"'> and t5.user_name like concat(\"%\",#{userName},\"%\")</if>" +
            "<if test='userPhone!=null and userPhone!=\"\"'> and t5.phone like concat(\"%\",#{userPhone},\"%\")</if>" +
            "<if test='name!=null and name!=\"\"'> and t5.displayname like concat(\"%\",#{name},\"%\")</if>" +
            "<if test='choseWay!=null'> and t5.chose_way = #{choseWay}</if>" +
            "<if test='status!=null'> and t5.entrust_status = #{status}</if>" +
            "<if test='entrustBegin!=null'> and TO_DAYS(#{entrustBegin}) - TO_DAYS(t5.created_time) &lt;= 0 </if>" +
            "<if test='entrustEnd!=null'> and TO_DAYS(#{entrustEnd}) - TO_DAYS(t5.created_time) &gt;= 0 </if>" +
            "<if test='dealBegin!=0'> and #{dealBegin} - t5.deal_time &lt;= 0 </if>" +
            "<if test='dealEnd!=0'> and #{dealEnd} - t5.deal_time &gt;= 0 </if>" +
            "<if test='dealFlag==2'> and t5.deal_time &gt; 0 </if>" +
            "<if test='userId!=null and userId!=\"\"'> and t5.userId =#{userId} </if>" +
            "</where>" +
            " ORDER BY t5.created_time</script>")
    List<TranferEntrustModel> getAllEntrusts(TranferEntrustModel model);
}
