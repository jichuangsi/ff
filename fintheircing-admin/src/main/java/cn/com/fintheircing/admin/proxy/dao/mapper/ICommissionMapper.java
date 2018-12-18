package cn.com.fintheircing.admin.proxy.dao.mapper;

import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import org.apache.ibatis.annotations.Update;

public interface ICommissionMapper {


    @Update("<script>UPDATE `commission` SET " +
            "`back_commission` = #{backCommission}, `day_commission` = #{dayCommission}," +
            " `month_commission` = #{monthCommission}  WHERE `saleman_id` = #{proxyId}</script>")
    int updateCommission(ProxyModel proxyModel);
}
