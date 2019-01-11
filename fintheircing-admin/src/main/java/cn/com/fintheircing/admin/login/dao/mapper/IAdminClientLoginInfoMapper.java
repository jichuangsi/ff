package cn.com.fintheircing.admin.login.dao.mapper;

import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import org.apache.ibatis.annotations.Select;

public interface IAdminClientLoginInfoMapper {
    @Select("<script>select t1.`status` as status,t1.user_name as userName,t1.roleGrade as roleGrade," +
            "t1.roleName as roleName,t1.uuid as uuid from  (select t3.delete_flag as delete_flag,t3.`status` as `status`" +
            ",t3.user_name as user_name,t3.uuid as uuid,t4.role_grade as roleGrade,t4.role_name as roleName from admin_client_info t3" +
            " LEFT JOIN admin_role t4 on t3.role_grade=t4.role_grade) t1 LEFT JOIN admin_client_login_info t2 on t1.uuid=t2.admin_client_id" +
            "  where t2.login_name=#{loginName} AND t2.pwd=#{pwd}  and t1.delete_flag=0</script>")
    UserTokenInfo selectAdminLoginModel(UserTokenInfo adminLoginModel);
}
