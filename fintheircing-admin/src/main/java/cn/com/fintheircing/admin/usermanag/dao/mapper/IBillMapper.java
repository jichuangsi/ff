package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.promise.CheckPromiseModel;
import cn.com.fintheircing.admin.usermanag.model.promise.PromiseModel;
import cn.com.fintheircing.admin.usermanag.model.result.TransListModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * IBillMapper class
 *
 * @author yaoxiong
 * @date 2019/1/8
 */

@Mapper
@Component
public interface IBillMapper {
    /**
     * 修改支付信息
     * @param model
     * @return
     */
    @Update("<script>update Bill set pay_status=\"1\",trans_amount=#{transAmount},trans_Time=#{transTime},version=version+1 " +
            "where order_id=#{orderId} and pay_status=#{payStatus}</script>")
    int updateBill(TransListModel model);
    /**
     * 把字段status改成1
     * @param model
     * @return
     */
    @Update("")
    int updatePayInfo(RecodeInfoPayModel model);

    /***
     * 查询checkStatus =0的
     * @return
     */
    @Select("")
    List<RecodeInfoPayModel> findAllPayInfo();

    /**
     * 根据用户id 和合同businessContractId 返回一个审核
     * @param parms
     * @return
     */
    @Select("")
    CheckPromiseModel findAllPromise(Map<String, Object> parms);

    /**
     * 查询申请保证金的基本信息
     * @return
     */
    @Select("<script>select t1.user_id as userId, t1.business_contract_id as businessContractId, t1.cost_count as cash, t1.remark as remark,t1.way as way, t2.contract_num as contractNum, t3.phone as phone " +
            "Recode_Info_Pay t1,Business_Contract t2,User_Client_Info t3 where t1.status=\"0\" " +
            "and t1.user_id=t3.uuid and t1.business_contract_id=t2.uuid</script>")
    List<PromiseModel> findAllApply();

    /**
     * 改变RecodeInfoPay的状态 status =1
     * @return
     */
    @Update("")
    int updateRecodeInfo(String id);
}
