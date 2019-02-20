package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.ContractControlModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IBusinessContractControlMapper {

    @Select("<script>select t1.uuid as id,t2.contract_num as contractNo,t2.phone as phone,t2.displayname as name,t2.chose_way as productNum,t1.first_interest as firstMoney,t1.borrow_time as borrowTime,t1.borrow_rate as borrowRate,t1.warnning_line as warnningLine,t1.abort_line as abortLine,t1.control_type as controlNum,t1.created_time as createdTime,t1.borrow_money as borrowMoney,t1.promised_money as promisedMoney,t1.pick_up_money as gainMoney,t1.wind_up_money as endMoney,t1.verify_status as verifyStatus,t1.less_money as lessMoney,t2.product_id as productId from business_control_contract t1 left join (select t3.uuid,t3.contract_num,t4.phone,t4.displayname,t3.chose_way,t3.first_interest,t3.expired_time,t3.once_server_money,t3.warning_line,t3.abort_line,t3.product_id from (select t5.uuid,t5.contract_num,t5.chose_way,t6.warning_line,t6.abort_line,t5.once_server_money,t5.expired_time,t5.first_interest,t5.user_id,t5.product_id from business_contract t5 LEFT JOIN business_contract_risk t6 on t5.risk_id=t6.uuid) t3 LEFT JOIN user_client_info t4 on t3.user_id=t4.uuid) t2 on t1.contract_id=t2.uuid" +
            "<where>" +
            "<if test='controlNum!=null'> and t1.control_type=#{controlNum}</if>" +
            "<if test='productNum!=null'> and t2.chose_way=#{productNum}</if>" +
            "<if test='id!=null and id!=\"\"'> and t1.contract_id=#{id}</if>" +
            "<if test='contractNo!=null and contractNo!=\"\"'> and t2.contract_num like concat(\"%\",#{contractNo},\"%\")</if>" +
            "<if test='phone!=null and phone!=\"\"'> and t2.phone like concat(\"%\",#{phone},\"%\")</if>" +
            "<if test='name!=null and name!=\"\"'> and t2.displayname like concat(\"%\",#{name},\"%\")</if>" +
            "</where></script>")
    List<ContractControlModel> getContractControls(ContractControlModel model);
}
