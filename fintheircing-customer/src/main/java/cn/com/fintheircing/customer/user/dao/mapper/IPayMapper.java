package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.contract.AccountInfoModel;
import cn.com.fintheircing.customer.user.model.contract.contactModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * PayInfoMapper接口 interface
 *
 * @author yaoxiong
 * @date 2019/1/15
 */
public interface IPayMapper {
    /**
     * 查询所有支付信息
     * */
    @Select("<script></script>")
    List<RecodeInfoPayModel> findAllPayInfo();

    /**
     * 更新字段
     * @param model
     * @return
     */
    @Update("<script></script>")
   int updatePayInfo(RecodeInfoPayModel model);
    @Select("<script>select t1.created_time as createdTime,t1.amount as amount,t1.payway as payway,t1.available_money as lessMoney," +
            " t1.cold_money as coldMoney  from customer_recharge_recode t1 <where><if test= \"week!=null and week!=''\">" +
            " and t1.created_time &gt;= STR_TO_DATE('${week}','%Y-%m-%d %H:%i:%S')</if> and t1.user_id=#{userId} and t1.pay_status=1 </where></script>")
    List<AccountInfoModel> QueryListForAccountInfo(Map<String, Object> parms);
}
