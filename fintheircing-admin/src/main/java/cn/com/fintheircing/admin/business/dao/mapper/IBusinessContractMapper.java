package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.policy.model.TranferContractModel;
import cn.com.fintheircing.admin.policy.model.TranferControlContractModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IBusinessContractMapper {

    @Select("<script>select count(1) from business_contract t1 LEFT JOIN systemdetect_product t2 on t1.product_id=t2.id " +
            "<where>" +
            " t1.user_id=#{userId}" +
            "<if test='productNo!=null'> and t2.allot=#{productNo}  and not(t1.contract_status=2)  </if>" +
            "</where></script>")
    int countSameContract(Map<String, Object> params);

    //策略
    @Select("<scirpt></script>")
    List<TranferContractModel> selectPageContracts(TranferContractModel contractModel);

    @Select("<script>select t1.version as version,t1.uuid as id,t1.borrow_money as borrowMoney,t2.customer_max_account as customerMaxAccount,t2.hold_over_currency as holdOverCurrency,t2.hold_over_five_avg as holdOverFiveAvg,t2.stock_shut_down as stockShutDown,t2.ventur_edition_max_account as venturEditionMaxAccount,t1.available_money as  canUseMoney,t1.promised_money as promisedMoney,t2.abort_line as abortLine,t2.warning_line as warningLine,t1.worth as worth,t1.cold as coldCash,t1.moneyInDeal as businessRate,t1.buyRate as buyRate,t1.overRate as overRate,t1.wornRate as wornRate,t1.downRate as downRate from (select t3.uuid,t4.worth,t3.borrow_money,t3.available_money,t3.promised_money,t3.risk_id,t3.cold_money as cold,t3.money_in_deal as moneyInDeal,t3.version,t3.buyRate,t3.overRate,t3.wornRate,t3.downRate " +
            " from (select t5.uuid,t5.delete_flag,t5.borrow_money,t5.available_money,t5.promised_money,t5.risk_id,t5.cold_money,t6.money_in_deal,t5.version,t6.entry_amount as buyRate,t6.liquidation as overRate,t6.worn_line as wornRate,t6.out_amount as downRate,t5.user_id from business_contract t5 LEFT JOIN systemdetect_product t6 on t5.product_id=t6.id where t5.delete_flag=\"0\" and not(t5.contract_status=2)) t3 LEFT JOIN (select contract_id,sum(current_worth) as worth from business_stock_holding where delete_flag=\"0\" group BY contract_id) t4 on t3.uuid = t4.contract_id where t3.delete_flag=\"0\" and t3.user_id=#{userId}) t1 LEFT JOIN business_contract_risk t2 on  t1.risk_id=t2.uuid where t1.uuid = #{id} and t2.delete_flag=\"0\"</script>")
    ContractModel selectContract(@Param("id") String id,@Param("userId") String userId);

    @Select("<scirpt></script>")
    List<TranferControlContractModel> selectPageControl(TranferControlContractModel model);

    @Select("<script>select t2.floatMoney as floatMoney,t2.worth as worth,t1.money_in_contact as moneyInContract,t1.created_time as createdTime,t1.contract_num as contractNum,t1.lever_rate as productRate,t1.available_money as canUseMoney,t1.financing_time as productTime,t1.customer_max_account customerMaxAccount,t1.hold_over_currency as holdOverCurrency,t1.hold_over_five_avg as holdOverFiveAvg,t1.stock_shut_down as stockShutDown,t1.ventur_edition_max_account as venturEditionMaxAccount,t1.warning_line as warnRate,t1.abort_line as downRate,t1.promised_money as promisedMoney,t1.contractid as id,t1.chose_way as choseWay,t1.cold_money as coldCash,t1.once_server_money onceGetServer,t1.displayname as displayName,t1.id_card as idCard from " +
            " (select t4.money_in_contact,t3.created_time,t3.contract_num,t4.lever_rate,t3.available_money,t4.financing_time,t3.customer_max_account,t3.hold_over_currency,t3.hold_over_five_avg,t3.stock_shut_down,t3.ventur_edition_max_account,t3.warning_line,t3.abort_line,t3.promised_money,t3.contractid,t3.chose_way,t3.cold_money,t4.once_server_money,t3.displayname,t3.id_card from " +
            " (select t6.customer_max_account,t6.hold_over_currency,t6.hold_over_five_avg,t6.stock_shut_down,t6.ventur_edition_max_account,t6.warning_line,t6.abort_line,t5.created_time,t5.contract_num,t5.available_money,t5.product_id,t5.promised_money,t5.contractid,t5.chose_way,t5.cold_money,t5.displayname,t5.id_card from " +
            " (select t8.displayname,t8.id_card,t7.created_time,t7.contract_num,t7.available_money,t7.product_id,t7.promised_money,t7.uuid as contractid,t7.chose_way,t7.cold_money,t7.user_id,t7.delete_flag,t7.risk_id from business_contract t7 left join user_client_info t8 on t7.user_id=t8.uuid)" +
            " t5 left join business_contract_risk t6 on t5.risk_id=t6.uuid where t5.user_id=#{userId} and t5.delete_flag=\"0\")" +
            " t3 LEFT JOIN systemdetect_product t4 on t3.product_id=t4.id)" +
            " t1 LEFT JOIN (" +
            " select contract_id,sum(float_money) as floatMoney,sum(current_worth) as worth from business_stock_holding where contract_id in (select uuid from business_contract where user_id=#{userId}  and not(contract_status=2))  and delete_flag=\"0\" GROUP BY contract_id" +
            " ) t2 on t1.contractid=t2.contract_id</script>")
    List<ContractModel> selectCurrentContract(@Param("userId") String userId);

    /**
     * 追加保证金
     */
    int addPromiseMoney(@Param("cash") double cash);

    @Select("<script>select t1.uuid as id,t1.promised_money as promisedMoney,t1.borrow_money as borrowMoney,t1.available_money as canUseMoney,t1.cold_money as coldCash,t2.abort_line as abortLine,t1.worth as worth,t2.warning_line as warningLine from (select t3.promised_money,t3.borrow_money,t3.available_money,t3.cold_money,t4.worth,t3.risk_id,t3.uuid from business_contract t3 left join ( " +
            "select contract_id,sum(float_money) as floatMoney,sum(current_worth) as worth from business_stock_holding GROUP BY contract_id) t4 on t3.uuid=t4.contract_id where t3.delete_flag=\"0\" and not(t3.contract_status=2)) t1 left join business_contract_risk t2 on t1.risk_id=t2.uuid</script>")
    List<ContractModel> selectContractRisk();
}
