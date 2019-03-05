package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.tranfer.TranferHoldingModel;
import cn.com.fintheircing.admin.dividend.model.DividendHoldingModel;
import cn.com.fintheircing.admin.risk.model.DangerousStockModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IBusinessStockHoldingMapper {

    @Select("<script>select t1.uuid as id,t1.stock_id as stockId,t2.stock_num as stockNo,t2.stock_name as stockName,t1.amount as amount,t1.cost_price as costPrice" +
            ",t1.current_worth as currentWorth,t1.can_sell as canSell,t1.float_money as floatMoney,t1.float_rate as floatRate,t1.current_price as currentPrice" +
            ",t1.mother_account as motherAccount,t1.rude_end as rudeEnd" +
            " from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id <where> " +
            "<if Test='contractId!=null and contractId!=\"\"'> t1.contract_id=#{contractId} </if>" +
            "<if Test='stockNum!=null and stockNum!=\"\"'> and t2.stock_num=#{stockNum}</if> <if Test='stockId!=null and stockId!=\"\"'> and t2.id=#{stockId}</if> and t1.delete_flag=\"0\" and (amount>0 or cold_amount>0)</where> GROUP BY t2.stock_num</script>")
    List<StockHoldingModel> selectStockNum(@Param("contractId")String contractId, @Param("stockNum") String stockNum,@Param("stockId") String stockId);

    @Select("<script>select count(1)" +
            "  from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id <where> t1.contract_id=#{contractId}" +
            " <if Test='stockNum!=null and stockNum!=\"\"'> and t2.stock_num=#{stockNum}</if> and t1.delete_flag=\"0\" </where></script>")
    int countStockInContract(@Param("contractId")String contractId, @Param("stockNum") String stockNum);

    @Select("<script>select t2.stock_num as stockNo,t2.id as stockId,t1.version as version from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id where t1.delete_flag=\"0\"</script>")
    List<StockHoldingModel> selectHoldStockAll();

    @Update("<script>UPDATE business_stock_holding set current_price=#{currentPrice},current_worth=#{currentPrice}*amount,float_money=(#{currentPrice}-cost_price)*amount,float_rate=((#{currentPrice}/cost_price)-1),version=#{version}+1  where stock_id=#{stockId} and delete_flag=\"0\" and version=#{version}</script>")
    int updateHoldingStockPrice(StockHoldingModel model);

    @Select("<script>select t1.uuid as id,t1.stock_id as stockId,t2.stock_num as stockNo,t2.stock_name as stockName,t1.amount as amount,t1.cost_price as costPrice,t1.current_worth as currentWorth,t1.can_sell as canSell,t1.float_money as floatMoney,t1.float_rate as floatRate,t1.current_price as currentPrice,t1.mother_account as motherAccount,t1.rude_end as rudeEnd,t1.contract_num as contractNum,t1.user_name as userName,t1.phone as userPhone,t1.displayname as name,t1.chose_way as choseWay from (select t3.contract_id,t3.uuid,t3.stock_id,t3.amount,t3.cost_price,t3.current_worth,t3.can_sell,t3.float_money,t3.float_rate,t3.current_price,t3.mother_account,t3.rude_end,t4.* from business_stock_holding t3 LEFT JOIN (select t6.user_name,t6.phone,t5.uuid as contractId,t6.displayname,t5.contract_num,t5.chose_way from business_contract t5 LEFT JOIN user_client_info t6 on t5.user_id=t6.uuid where t5.contract_status=1) t4 on t3.contract_id=t4.contractId) t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id" +
            "<where>" +
            "<if Test='choseWay!=null'> and chose_way=#{choseWay}</if>" +
            "<if Test='stockNo!=null and stockNo!=\"\"'> and stock_num like concat(\"%\",#{stockNo},\"%\")</if>" +
            "<if Test='stockName!=null and stockName!=\"\"'> and stock_name like concat(\"%\",#{stockName},\"%\")</if>" +
            "<if Test='contractNum!=null and contractNum!=\"\"'> and contract_num like concat(\"%\",#{contractNum},\"%\")</if>" +
            "<if Test='userName!=null and userName!=\"\"'> and user_name like concat(\"%\",#{userName},\"%\")</if>" +
            "<if Test='userPhone!=null and userPhone!=\"\"'> and phone like concat(\"%\",#{userPhone},\"%\")</if>" +
            "<if Test='name!=null and name!=\"\"'> and displayname like concat(\"%\",#{name},\"%\")</if>" +
            "</where></script>")
    List<TranferHoldingModel> getAllHolding(TranferHoldingModel model);

    @Select("<script>select t2.stock_num as stockCode,t2.stock_name as stockName,t2.status as stockStatus,t2.id as stockId,t1.created_time as createdTime,t1.uuid as holdingId,t1.amount as holdingAmount,t1.current_worth as holdingWorth from business_stock_holding t1  LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id <where>" +
            "<if Test='keyWord!=null and keyWord!=\"\"'> and t2.stock_name like CONCAT(\"%\",#{keyWord},\"%\")</if><if Test='list.size>0'> and t1.contract_id in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "            #{item}   </foreach></if></where></script>")
    List<DangerousStockModel> getDangerousStockModels(Map<String,Object> params);

    @Select("<script>select t2.stock_num as stockCode,t2.stock_name as stockName,t1.created_time as createdTime,t1.uuid as holdingId,sum(t1.amount) as holdingAmount,sum(t1.current_worth) as holdingWorth from business_stock_holding t1  LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id  group by t1.stock_id</script>")
    List<DangerousStockModel> getAllStockModles();

    @Select("<script>select t6.displayname as userName,t6.phone as phone,t5.contract_num as contractNo,t5.stock_num as stockNo,stock_name as stockName,t5.amount  as amount from (select t3.*,t4.contract_num,t4.user_id from (select t1.stock_num,t1.stock_name,t2.* from admin_transaction_summary t1 right join business_stock_holding t2 on t1.id=t2.stock_id) t3 left join business_contract t4 on t3.contract_id=t4.uuid) t5 LEFT JOIN user_client_info t6 on t5.user_id=t6.uuid " +
            "<where><if Test='keyWord!=null and keyWord!=\"\"'> and t5.stock_num like CONCAT(\"%\",#{keyWord},\"%\")</if></where> order by t5.stock_num</script>")
    List<DividendHoldingModel> getPageDividendHolding(@Param("keyWord") String keyWord);

    @Select("<script>select t1.uuid as id,t2.stock_num as stockNo,t2.stock_name as stockName,t1.amount as amount,t1.cost_price as costPrice,t1.created_time as createdTime from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id where t1.delete_flag=\"0\" and t1.contract_id=#{contractId}</script>")
    List<StockHoldingModel> getPageHolding(@Param("contractId") String contractId);
}
