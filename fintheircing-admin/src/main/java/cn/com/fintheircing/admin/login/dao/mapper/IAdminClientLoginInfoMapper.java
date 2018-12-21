package cn.com.fintheircing.admin.login.dao.mapper;

import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import org.apache.ibatis.annotations.Select;

public interface IAdminClientLoginInfoMapper {
    @Select("<script>select t1.position as position,t1.`status` as status," +
            "t1.user_name as userName,t1.role as role,t1.uuid as uuid from admin_client_info t1" +
            " LEFT JOIN admin_client_login_info t2 " +
            "on t1.uuid=t2.admin_client_id where t2.login_name=#{loginName}" +
            " AND t2.pwd=#{pwd}  and t1.delete_flag=0</script>")
    UserTokenInfo selectAdminLoginModel(UserTokenInfo adminLoginModel);
}
