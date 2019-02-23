package cn.com.fintheircing.admin.account.dao.mapper;

import cn.com.fintheircing.admin.proxy.model.EmployeeModel;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAdminClientInfoMapper {

    @Select("<script>select uuid as id,`name` as  name,user_name as userName,role_grade as position" +
            ",remarks as remarks from admin_client_info" +
            "<where>" +
            " boss_id=#{id} and role_grade=3" +
            "<if test='userName!=null and userName!=\"\"'> and user_name like concat('%',#{userName},'%')</if>" +
            "</where></script>")
    List<EmployeeModel> selectEmp(EmployeeModel model);

    @Select("<script> select uuid as proxyId,boss_id as bossId,proxy_num as proxyNum ,user_name as proxyName, name as linkMan,role_grade as roleGrade, phone as linkPhone,`status` as `status`,remarks as remarks,created_time as createdTime ,updated_time as updatedTime  from admin_client_info " +
            "<where>" +
            " role_grade in (1,2)  and delete_flag=0 " +
            "<if test='roleGrade!=0'> and boss_id=#{proxyId} </if>"+
            "<if test='proxyNum!=null and proxyNum!=\"\"'> and proxy_num like concat('%',#{proxyNum},'%')</if>" +
            "<if test='beginTime!=null and beginTime!=\"\"'> and DATE_FORMAT(created_time,'%Y-%m-%d')=#{beginTime}</if>" +
            "</where> order by created_time desc" +
            "</script>")
    List<ProxyModel> selectProxy(ProxyModel model);

}
