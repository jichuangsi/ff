package cn.com.fintheircing.admin.proxy.dao.mapper;

import cn.com.fintheircing.admin.proxy.model.SpreadModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ISpreadMapper {


    @Select("<script>select count(1) from proxy_spread where invite_code=#{code}</script>")
    int countInvit(String code);


    //改
    @Select("<script>select t2.user_name as proxyName,t2.proxy_num as proxyNum,t1.invite_code as spreadNum,t1.spread_link as spreadLink  ,t1.spread_code_pic as qRcode from proxy_spread t1 RIGHT JOIN(  select uuid,proxy_num,user_name,boss_id from admin_client_info   where not(uuid=#{id})  and role_grade in (1,2) and delete_flag=0 ) t2 on t1.saleman_id=t2.uuid  " +
            "<where> " +
            " t1.delete_flag=0" +
            "<if test='position!=0'> and t2.boss_id=#{id} </if>" +
            "<if test='proxyNum!=null and proxyNum!=\"\"'>and  t2.proxy_num like CONCAT(\"%\",#{proxyNum},\"%\")</if>" +
            "<if test='proxyName!=null and proxyName!=\"\"'> and t2.user_name like CONCAT(\"%\",#{proxyName},\"%\")</if>" +
            "</where>" +
            " order by t1.created_time </script>")
    List<SpreadModel> getSpreadProxy(SpreadModel spreadModel);



//改
    @Select("<script>select t4.empName as empName,t4.empNum as empNum,t4.proxyName as proxyName,t4.proxyNum as proxyNum,t1.invite_code as spreadNum, t1.spread_link as spreadLink,t1.spread_code_pic as qRcode from proxy_spread t1 RIGHT JOIN ( SELECT t3.proxy_num as proxyNum,t3.user_name as proxyName,t2.proxy_num as empNum, t2.user_name as empName,t2.uuid as empid,t3.uuid as bossid FROM admin_client_info t2  LEFT JOIN admin_client_info t3 on t2.boss_id=t3.uuid where t2.role_grade=3 ) t4 on t1.saleman_id=t4.empid  " +
            " <where>" +
            " t1.delete_flag=0" +
            "<if test='position!=0'> and t4.bossid=#{id} </if>" +
            "<if test='proxyName!=null and proxyName!=\"\"'> and t4.proxyName like CONCAT(\"%\",#{proxyName},\"%\") </if>" +
            "<if test='proxyNum!=null and proxyNum!=\"\"'> and t4.proxyNum like CONCAT(\"%\",#{proxyNum},\"%\") </if>" +
            "</where>" +
            "  order by t1.created_time</script>")
    List<SpreadModel> getSpreadEmp(SpreadModel spreadModel);

    //获取邀请人id
    @Select("<script>select saleman_id as id from proxy_spread WHERE invite_code=#{inviteCode}</script>")
    SpreadModel getSpreadId(@Param("inviteCode") String inviteCode);


    @Select("<script>select spread_link as spreadLink,spread_code_pic as qRcode,invite_code as spreadNum from proxy_spread where saleman_id=#{userId}</script>")
    SpreadModel getOwnSpread(@Param("userId")String userId);
}
