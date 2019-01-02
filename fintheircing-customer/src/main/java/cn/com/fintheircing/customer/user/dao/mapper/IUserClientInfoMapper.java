package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IUserClientInfoMapper {

	@Select("<script>select t2.uuid as uuid,t2.role_grade as roleGrade,t2.role_name as roleName,t2.displayname as displayname,t2.phone as phone,t2.`status` as `status`,t2.user_name as userName from user_client_login_info t1 LEFT OUTER JOIN (select t3.uuid,t4.role_grade,t4.role_name,t3.displayname,t3.phone,t3.`status`,t3.user_name from user_client_info t3 LEFT JOIN admin_role t4 on t3.role_grade=t4.role_grade ) t2 on t1.client_info_id=t2.uuid " +
			" where t1.login_name=#{loginName} and t1.pwd=#{pwd}</script>")
	UserTokenInfo find(UserTokenInfo model);

	@Select("<script>select t2.uuid as userId" +
			",t1.login_Name as userName" +
			",t1.ip_Adress as ipAdress" +
			",t2.login_Time as loginTime" +
			",t2.last_Time as lastTime" +
			",t2.method as method" +
			",t2.usage as usage," +
			"t2.`operating` as `operating`" +
			" from user_client_login_info t1 " +
			"LEFT OUTER JOIN User_Info_Recoding t2 on t1.client_info_id=t2.login_id " +
			"<where> <if test= \"operating!=null and operating!=''\">t2.operating LIKE CONCAT('%',#{operating},'%') </if> <if test= \"loginName'!=null and loginName!=''\">and t1.login_name LIKE CONCAT('%',#{loginName},'%') </if>  </where>" +
			"and where t2.delete_flag=\"0\"  </script>")
	List<OnlineUserInfo> findAllRecoding(String operating, String loginName);
	@Update("<script> update User_Info_Recoding t1 set t1.delete_flag=\"0\" where t1.login_id=#{userId}</script>")
	int deleteRecoding(String userId);
}
