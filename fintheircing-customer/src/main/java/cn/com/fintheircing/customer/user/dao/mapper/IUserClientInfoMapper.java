package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import org.apache.ibatis.annotations.Select;

public interface IUserClientInfoMapper {

	@Select("<script>select t2.uuid as uuid,t2.role as role,t2.displayname as displayname,t2.phone as phone,t2.`status` as `status` from user_client_login_info t1 " +
			"LEFT OUTER JOIN user_client_info t2 on t1.client_info_id=t2.uuid " +
			"where t1.login_name=#{loginName} and t1.pwd=#{pwd}</script>")
	UserTokenInfo find(UserTokenInfo model);

}
