package cn.com.fintheircing.customer.user.dao.mapper;


import org.apache.ibatis.annotations.Select;


import cn.com.fintheircing.customer.user.model.UserForLoginModel;

public interface IUserClientInfoMapper {

	@Select("<script>select * from user_client_login_info t1 "
			+ "LEFT OUTER JOIN user_client_info t2 on t1.client_info_id=t2.uuid "
			+ "where t1.login_name=#{loginName} and t1.pwd=#{pwd}</script>")
	UserForLoginModel find(UserForLoginModel model);

}
