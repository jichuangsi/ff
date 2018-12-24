package cn.com.fintheircing.admin.proxy.dao.mapper;

import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import org.apache.ibatis.annotations.Update;

public interface ICommissionMapper {


    @Update("<script>UPDATE proxy_commission SET " +
            "`back_commission` = #{backCommission}, `day_commission` = #{dayCommission}," +
            " `month_commission` = #{monthCommission},update_user_id = #{proxyId}," +
            "update_user_name = #{proxyName},updated_time=#{updateTime}  WHERE `saleman_id` = #{proxyId}  and delete_flag=0</script>")
    int updateCommission(ProxyModel proxyModel);
}
