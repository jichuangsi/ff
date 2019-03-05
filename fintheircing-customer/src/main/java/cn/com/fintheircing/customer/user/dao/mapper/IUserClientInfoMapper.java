package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.model.OnlineUserInfo;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IUserClientInfoMapper {

	@Select("<script>select t2.uuid as uuid,t2.role_grade as roleGrade,t2.role_name as roleName,t2.displayname as displayname,t2.phone as phone,t2.`status` as `status`,t2.user_name as userName from user_client_login_info t1 LEFT OUTER JOIN (select t3.uuid,t4.role_grade,t4.role_name,t3.displayname,t3.phone,t3.`status`,t3.user_name from user_client_info t3 LEFT JOIN admin_role t4 on t3.role_grade=t4.role_grade ) t2 on t1.client_info_id=t2.uuid " +
			" where t1.login_name=#{loginName} and t1.pwd=#{pwd}</script>")
	UserTokenInfo find(UserTokenInfo model);

	@Select("<script>select t2.uuid as uuid,t2.role_grade as roleGrade,t2.role_name as roleName,t2.displayname as displayname,t2.phone as phone,t2.`status` as `status`,t2.user_name as userName from user_client_login_info t1 LEFT OUTER JOIN (select t3.uuid,t4.role_grade,t4.role_name,t3.displayname,t3.phone,t3.`status`,t3.user_name from user_client_info t3 LEFT JOIN admin_role t4 on t3.role_grade=t4.role_grade ) t2 on t1.client_info_id=t2.uuid " +
			" where t2.uuid=#{id}</script>")
	UserTokenInfo findByUuid(@Param("id") String id);

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
	List<OnlineUserInfo> findAllRecoding(Map<String, Object> params);

	@Update("<script> update User_Info_Recoding t1 set t1.delete_flag=\"0\" where t1.login_id=#{userId}</script>")
	int deleteRecoding(Map<String, Object> params);


	@Select("<script>select inviter_id as inviterId from user_client_info where uuid=#{id}</script>")
	UserClientInfo selectSaleMan(Map<String, Object> params);
	@Select("<script>selcet t2.login_time as loginTime,t2.logout_time as logoutTime,t1.uuid as,t1.user_name as userName ,t2.ip_address as ipAddress ,t2.status as status　" +
			" from user_client_info t1,user_client_login_info t2 " +
			"where t1.uuid=t2.client_info_id and t1.uuid=#{id}</script>")
	OnlineUserInfo findAllOnline(String id);

	/**
	 * 修改头像
	 * @param parms
	 */
	@Update("<script>update user_client_login_info t1 set t1.photo=#{bytes} where t1.client_info_id=#{uuid}</script>")
	int updateAvatar(Map<String, Object> parms);
	@Update("<script>update user_client_info t1 set t1.tx_password =#{txPassword} where t1.uuid=#{uuid}</script>")
    int addOrChangePassword( Map<String,Object> parms );
}
