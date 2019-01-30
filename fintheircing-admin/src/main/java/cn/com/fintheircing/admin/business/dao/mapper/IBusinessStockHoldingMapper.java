package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IBusinessStockHoldingMapper {

    @Select("<script>select t1.uuid as id,t1.stock_id as stockId,t2.stock_num as stockNo,t2.stock_name as stockName,SUM(t1.amount) as amount,Avg(t1.cost_price) as costPrice" +
            ",sum(t1.current_worth) as currentWorth,sum(t1.can_sell) as canSell,sum(t1.float_money) as floatMoney,sum(t1.float_rate) as floatRate,SUM(t1.current_price) as currentPrice" +
            ",t1.mother_account as motherAccount" +
            " from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id <where> t1.contract_id=#{contractId}" +
            "<if test='stockNum!=null and stockNum!=\"\"'> and t2.stock_num=#{stockNum}</if> <if test='stockId!=null and stockId!=\"\"'> and t2.id=#{stockId}</if> and t1.delete_flag=\"0\" and (amount>0 or cold_amount>0)</where> GROUP BY t2.stock_num</script>")
    List<StockHoldingModel> selectStockNum(@Param("contractId")String contractId, @Param("stockNum") String stockNum,@Param("stockId") String stockId);


    @Select("<script>select count(1)" +
            "  from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id <where> t1.contract_id=#{contractId}" +
            " <if test='stockNum!=null and stockNum!=\"\"'> and t2.stock_num=#{stockNum}</if> and t1.delete_flag=\"0\" </where> GROUP BY t2.stock_num</script>")
    int countStockInContract(@Param("contractId")String contractId, @Param("stockNum") String stockNum);

    @Select("<script>select t2.stock_num as stockNo,t2.id as stockId from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id where t1.delete_flag=\"0\" group by t2.stock_num</script>")
    List<StockHoldingModel> selectHoldStockAll();

    @Update("<script>UPDATE business_stock_holding set current_price=#{currentPrice},current_worth=#{currentPrice}*amount,float_money=(#{currentPrice}-cost_price)*amount,float_rate=((#{currentPrice}/cost_price)-1)  where stock_id=#{stockId} and delete_flag=\"0\"</script>")
    int updateHoldingStockPrice(StockHoldingModel model);
}
