package cn.com.fintheircing.admin.usermanag.dao.mapper;


import cn.com.fintheircing.admin.usermanag.entity.MesInfo;
import cn.com.fintheircing.admin.usermanag.model.ｍes.MesInfoModel;
import cn.com.fintheircing.admin.usermanag.model.ｍes.MesModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select("<script>select t1.uuid as mesId ,t1.title as title,t1.content as content,t1.send_time as sendTime,t1.status as status from admin_mes_info t1 <where>" +
            " <if test=\"title!=null and title !=''\"> and t1.title=#{title}</if> and  t1.delete_flag=0</where></script>")
    List<MesInfoModel> findMesBymark(String title);
    @Update("<script>update admin_mes_info t1 set t1.delete_flag=1 where t1.uuid=#{mesId}</script>")
    int deleteMesInfo(String mesId);
    @Update("<script>update admin_mes_info t1 set t1.status=1 where t1.uuid=#{mesId} and t1.delete_flag=0</script>")
    int updateMesInfo(String mesId);
    @Insert("<script> insert into admin_mes_info(title,content) values(#{title},#{content})</script>")
    int saveRecodeInfoPay(MesInfo info);
    @Update("<script>update admin_mes_info t1 set t1.title=#{title},t1.content=#{content} where t1.uuid=#{uuid}</script>")
    int resetMes(MesInfoModel mesInfoModel);

}
