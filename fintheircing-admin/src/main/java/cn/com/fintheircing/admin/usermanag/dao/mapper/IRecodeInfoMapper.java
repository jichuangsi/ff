package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IRecodeInfoMapper {
    @Select("<script>selcet t1.uuid as RecodeInfoId, t1.user_id as userId,t1.opera_name as userName,t2.ip_address as ipAddress," +
            " t2.login_time as loginTime,t2.logout_time as lastTime  " +
            " t1.operat_way as operating from recode_info t1 ,user_client_login_info t2 " +
            " <where><if test=\"opeart!=null and opeart!=''\"> and t1.operat_way =#{opeart}</if>" +
            " <if test=\"userName!=null and userName!=''\"> and t1.opera_name =#{userName}</if>" +
            " and t1.user_id= t2.user_id</where>  </script>")
    List<OnlineUserInfo> findAllRecode(Map<String, Object> parms);
    @Update("<script> update recode_info t1 set t1.delete_flag=1 where t1.uuid=#{recodeInfoId}</script>")
    int deleteRecoding(String recodeInfoId);
}
