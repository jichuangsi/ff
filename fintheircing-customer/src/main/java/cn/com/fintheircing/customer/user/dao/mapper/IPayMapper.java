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
    @Select("<script>select t1.amount as amount,t1.payway as payway,t1.available_money as lessMoney," +
            " t1.coldMoney as coldMoney  from customer_recharge_recode t1 where t1.user_id=#{userId}</script>")
    List<AccountInfoModel> QueryListForAccountInfo(String userId);
}
