package cn.com.fintheircing.admin.proxy.dao.mapper;

import cn.com.fintheircing.admin.proxy.model.EmployeeModel;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAdminClientInfoMapper {

    @Select("<script>select uuid as id,`name` as  name,user_name as userName,position as position" +
            ",`status` as status from admin_client_info where boss_id=#{proxyId}</script>")
    List<EmployeeModel> selectEmp(ProxyModel model);
}
