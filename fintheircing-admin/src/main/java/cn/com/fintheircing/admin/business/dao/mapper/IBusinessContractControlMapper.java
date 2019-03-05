package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.ContractControlModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IBusinessContractControlMapper {

    @Select("<script>select t1.uuid as id,t2.contract_num as contractNo,t2.phone as phone," +
            "t2.displayname as name,t2.chose_way as productNum,t1.first_interest as firstMoney," +
            "t1.borrow_time as borrowTime,t1.borrow_rate as borrowRate,t1.warnning_line as warnningLine," +
            "t1.abort_line as abortLine,t1.control_type as controlNum,t1.created_time as createdTime," +
            "t1.borrow_money as borrowMoney,t1.promised_money as promisedMoney,t1.pick_up_money as gainMoney," +
            "t1.wind_up_money as endMoney,t1.verify_status as verifyStatus,t1.less_money as lessMoney," +
            "t2.product_id as productId from business_control_contract t1 left join " +
            "(select t3.uuid,t3.contract_num,t4.phone,t4.displayname,t3.chose_way,t3.first_interest,t3.expired_time," +
            "t3.once_server_money,t3.warning_line,t3.abort_line,t3.product_id from " +
            "(select t5.uuid,t5.contract_num,t5.chose_way,t6.warning_line,t6.abort_line,t5.once_server_money," +
            "t5.expired_time,t5.first_interest,t5.user_id,t5.product_id from business_contract t5 LEFT JOIN " +
            "business_contract_risk t6 on t5.risk_id=t6.uuid) t3 LEFT JOIN user_client_info t4 on t3.user_id=t4.uuid) t2 on t1.contract_id=t2.uuid" +
            "<where>" +
            "<if test='controlNum!=null'> and t1.control_type=#{controlNum}</if>" +
            "<if test='productNum!=null'> and t2.chose_way=#{productNum}</if>" +
            "<if test='id!=null and id!=\"\"'> and t1.contract_id=#{id}</if>" +
            "<if test='contractNo!=null and contractNo!=\"\"'> and t2.contract_num like concat(\"%\",#{contractNo},\"%\")</if>" +
            "<if test='phone!=null and phone!=\"\"'> and t2.phone like concat(\"%\",#{phone},\"%\")</if>" +
            "<if test='name!=null and name!=\"\"'> and t2.displayname like concat(\"%\",#{name},\"%\")</if>" +
            "</where></script>")
    List<ContractControlModel> getContractControls(ContractControlModel model);

    @Select("<script>select t4.uuid as businessControlContractId, t1.user_id as userId,t6.phone as phone,t6.user_name as name" +
            " ,t1.uuid as BusinessContractId,t1.cold_money as coldMoney,t5.user_name as proxyName," +
            " t5.proxy_num as proxyNum, t1.first_interest as firstMoney ," +
            " t1.contract_num as contractNo,t1.updated_time as createdTime,t2.product_name as productStr, " +
            " t1.borrow_money as borrowMoney ,t1.promised_money as promisedMoney ,t4.warnning_line as warnningLine," +
            " t4.abort_line as abortLine,t1.available_money as lessMoney ,t3.current_worth as currentWorth ,t3.float_money as gainMoney ," +
            " t1.borrow_time as borrowTime,t1.expired_time as expiredTime,t1.contract_status as verifyStatus " +
            "FROM business_contract t1 LEFT JOIN business_stock_holding t3 ON t3.contract_id=t1.uuid LEFT JOIN systemdetect_product " +
            " t2 ON t1.product_id=t2.id LEFT JOIN business_control_contract t4 " +
            "  ON t4.contract_id=t1.uuid left join admin_client_info t5 on t5.user_client_info_id=t1.user_id left join" +
            " user_client_info t6 on t6.uuid=t1.user_id" +
            " <where>" +
            " <if test= \"productStr!=null\"> and t2.product_name =#{productStr}</if>" +
            " and t1.delete_flag=\"0\" and t3.delete_flag=\"0\" and t4.delete_flag=\"0\"" +
            " and t5.delete_flag=\"0\" and t6.delete_flag=\"0\"</where></script>")
    List<ContractControlModel> findAllContact(String productStr);

    @Select("<script>select t1.user_id as userId,t1.contract_id as contractId,t2.uuid as stockId, t2.stock_num as stockNum,stock_name as stockName,t1.deal_num as dealNum,t1.deal_price as dealPrice," +
            " t1.deal_time as buyTime, t1.uuid as BusinessStockEntrustId ,t3.account as userfulMoney," +
            " t3.frezze_amount as codeMoney" +
            " from business_stock_entrust t1 left join admin_transaction_summary t2 on t1.stock_id =t2.id left join" +
            " user_account t3 on t1.user_id =t3.user_id" +
            " where t1.stock_id =t2.uuid and t1.entrust_status=3 and t1.business_to=0" +
            " and t1.delete_falg=\"0\" and t2.delete_falg=\"0\" " +
            "   </script>")
    List<StockEntrustModel> findAllStock(String contractId);

    /**
     * 根据用户ID查询用户余额
     *
     * @param userId
     * @return
     */
    @Select("<script>select t1.account from user_account t1 where t1.user_id=#{userId}</script>")
    double findUserAmountByUserId(String userId);

    /**
     * 查询冻结资金
     *
     * @param userId
     * @return
     */
    @Select("<script>select t1.frezze_amount from user_account t1 where t1.user_id=#{userId}</script>")
    double findUserfrezzeAmountByUserId(String userId);

    /**
     * 查询名字
     *
     * @param userId
     * @return
     */
    @Select("<script>select t1.user_name from user_client_info t1 where t1.uuid=#{userId}</script>")
    String findNameByUserId(String userId);
}