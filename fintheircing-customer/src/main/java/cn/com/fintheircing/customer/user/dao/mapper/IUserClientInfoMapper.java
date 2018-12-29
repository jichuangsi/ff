package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import org.apache.ibatis.annotations.Select;

public interface IUserClientInfoMapper {

	@Select("<script>select t2.uuid as uuid,t2.role_grade as roleGrade,t2.role_name as roleName,t2.displayname as displayname,t2.phone as phone,t2.`status` as `status`,t2.user_name as userName from user_client_login_info t1 LEFT OUTER JOIN (select t3.uuid,t4.role_grade,t4.role_name,t3.displayname,t3.phone,t3.`status`,t3.user_name from user_client_info t3 LEFT JOIN admin_role t4 on t3.role_grade=t4.role_grade ) t2 on t1.client_info_id=t2.uuid " +
			" where t1.login_name=#{loginName} and t1.pwd=#{pwd}</script>")
	UserTokenInfo find(UserTokenInfo model);

}
