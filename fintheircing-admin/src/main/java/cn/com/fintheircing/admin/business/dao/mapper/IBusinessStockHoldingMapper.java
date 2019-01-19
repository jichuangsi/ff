package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface IBusinessStockHoldingMapper {

    @Select("<script>select t1.uuid as id,t1.stock_id as stockId,t2.stock_num as stockNo,t2.stock_name as stockName,SUM(t1.amount) as amount,Avg(t1.cost_price) as costPrice" +
            ",sum(t1.current_worth) as currentWorth,sum(t1.can_sell) as canSell,sum(t1.float_money) as floatMoney,sum(t1.float_rate) as floatRate,SUM(t1.current_price) as currentPrice" +
            ",t1.mother_account as motherAccount" +
            " from business_stock_holding t1 LEFT JOIN admin_transaction_summary t2 on t1.stock_id=t2.id where t1.contract_id=#{contractId} and t2.stock_num=#{stockNum} and t1.delete_flag=\"0\" GROUP BY stock_num</script>")
    StockHoldingModel selectStockNum(@Param("contractId")String contractId,@Param("stockNum") String stockNum);
}
