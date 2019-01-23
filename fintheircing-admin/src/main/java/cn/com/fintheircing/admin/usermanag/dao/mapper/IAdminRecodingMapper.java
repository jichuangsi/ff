package cn.com.fintheircing.admin.usermanag.dao.mapper;


import cn.com.fintheircing.admin.usermanag.model.MesModel;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


public interface IAdminRecodingMapper {
    @Select("<script>  SELECT t1.uuid AS userId, t1.user_name AS userName, t1.phone AS phone, t2.is_sucess AS isSucess, t2.task_type,t2.task_type AS taskType, t2.content AS content,t2.create_time AS createTime " +
            " FROM User_Client_Info t1,Recoding t2 " +
            " WHERE t1.uuid =t2.user_id</script>")
    List<MesModel> findAllMes();

    /**
     * 根据用户Id查询全部短信
     * @return
     */
    @Select("<script>select t2.is_sucess AS isSucess,t2.content AS content,t2.create_time AS createTime from Recoding t2 where t2.user_id=#{id}</script>")
    List<MesModel> findAllMesByUserId(String id);
}
