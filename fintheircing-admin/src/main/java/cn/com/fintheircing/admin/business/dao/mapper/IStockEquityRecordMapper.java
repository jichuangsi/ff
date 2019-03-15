package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.record.StockEquityModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IStockEquityRecordMapper {

    @Select("<script>select t6.proxy_num as submitterName,t5.balance as balance,t5.deal_price as dealPrice,t5.contract_num as contractNo,t5.user_code as userCode,t5.displayname as userName,t5.phone as userPhone,t5.apply_type as applyType,t5.created_time as createdTime,t5.remark as remarks,t5.stock_code as stockCode,t5.stock_name as stockName,t5.amount as amount,t5.contractId as contractId,t5.uuid as id,t5.srId as srId from (select t1.*,t2.*,t1.uuid as srId from stock_equity_record t1 LEFT JOIN (select t3.uuid as contractId,t3.contract_num,t4.displayname,t4.phone,t4.user_code from business_contract t3 left join user_client_info t4 on t3.user_id=t4.uuid) t2 on t1.contact_id=t2.contractId where t1.check_status=0) t5 LEFT JOIN admin_client_info t6 on t5.submitter_id=t6.uuid ORDER BY t5.created_time</script>")
    List<StockEquityModel> getEquityValidateList();

    @Update("<script>update stock_equity_record t1 set t1.check_status=1 where t1.uuid=#{srId}</script>")
    int agereeCreateStock(String srId);
    @Update("<script>update stock_equity_record t1 set t1.check_status=2 where t1.uuid=#{srId}</script>")
    int disAgereeCreateStock(String srId);
}
