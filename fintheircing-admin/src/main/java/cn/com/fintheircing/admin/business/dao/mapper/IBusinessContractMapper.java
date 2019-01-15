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
            "<if test='productNo!=null'> and t2.allot=#{productNo}  and not(t1.contract_status=2)</if>" +
            "</where></script>")
    int countSameContract(Map<String, Object> params);


    //策略
    @Select("<scirpt></script>")
    List<TranferContractModel> selectPageContracts(TranferContractModel contractModel);


    @Select("<script>select t1.uuid as id,t1.borrow_money as borrowMoney,t1.available_money as  canUseMoney,t1.promised_money as promisedMoney,t2.abort_line as abortLine,t2.warning_line as warningLine,t1.worth as worth,t1.cold as coldCash from (" +
            "select t3.uuid,t4.worth,t3.borrow_money,t3.available_money,t3.promised_money,t3.risk_id,t3.cold_money as cold" +
            "from business_contract t3 LEFT JOIN (select contract_id,sum(current_worth) as worth from business_stock_holding where delete_flag=\"0\" group BY contract_id) t4 on t3.uuid = t4.contract_id where t3.delete_flag=\"0\"" +
            ") t1 LEFT JOIN business_contract_risk t2 on  t1.risk_id=t2.uuid where t1.uuid = #{id} and t2.delete_flag=\"0\"</script>")
    ContractModel selectContract(@Param("id") String id);


    @Select("<scirpt></script>")
    List<TranferControlContractModel> selectPageControl(TranferControlContractModel model);


    @Select("<script>select t1.uuid as id,t1.chose_way as choseWay,t1.available_money as canUseMoney,t2.floatMoney as floatMoney,t1.cold_money as coldCash,t2.worth as worth from business_contract t1 LEFT JOIN (" +
            "select contract_id,sum(float_money) as floatMoney,sum(current_worth) as worth from business_stock_holding where contract_id in (" +
            "select uuid from business_contract where user_id=#{userId} and delete_flag=\"0\"" +
            ")  and delete_flag=\"0\" GROUP BY contract_id) t2 on t1.uuid=t2.contract_id where t1.user_id=#{userId} and delete_flag=\"0\"</script>")
    List<ContractModel> selectCurrentContract(@Param("userId") String userId);
}
