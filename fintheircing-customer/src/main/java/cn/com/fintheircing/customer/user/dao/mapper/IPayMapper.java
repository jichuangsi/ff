package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
    @Select("<script>select t1.add_count as addCount, t1.cost_count as costCount,t1.remark as remark,t1.way as way" +
            " t1.create_time as createTime,t1.update_time as updateTime,t1.task_type as taskType,t1.task_id as taskId " +
            " t2.account as blance,t1.ex_blance as exBlance  from recode_info_pay t1,user_account t2 where t1.user_id=t2.user_id=#{id}  </script>")
    List<RecodeInfoPayModel> findAllRecodeInfo(String id);

    void checkContact(String uuid);
}
