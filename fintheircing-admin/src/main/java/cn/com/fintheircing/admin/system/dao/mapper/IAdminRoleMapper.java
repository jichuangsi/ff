package cn.com.fintheircing.admin.system.dao.mapper;

import cn.com.fintheircing.admin.common.model.RoleModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAdminRoleMapper {

    @Select("<script>select count(1) from admin_role</script>")
    int selectCountAll();


    @Select("<script>select role_name as name,role_grade as position,role_sign as sign,uuid as uuid from admin_role</script>")
    List<RoleModel> selectAllRole();
}
