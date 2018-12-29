package cn.com.fintheircing.admin.proxy.dao.mapper;

import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface ICommissionMapper {


    @Update("<script>UPDATE proxy_commission SET " +
            "`back_commission` = #{backCommission}, `day_commission` = #{dayCommission}," +
            " `month_commission` = #{monthCommission},update_user_id = #{updateId}," +
            "update_user_name = #{updateName},updated_time=#{updateTime}  WHERE `saleman_id` = #{saleId}  and delete_flag=0</script>")
    int updateCommission(Map<String,Object> params);
}
