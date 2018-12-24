package cn.com.fintheircing.admin.proxy.dao.mapper;

import cn.com.fintheircing.admin.proxy.model.SpreadModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ISpreadMapper {


    @Select("<script>select count(1) from proxy_spread where invite_code=#{code}</script>")
    int countInvit(String code);

    @Select("<script>select t2.user_name as proxyName,t2.proxy_num as proxyNum,t1.invite_code as spreadNum,t1.spread_link as spreadLink" +
            " ,t1.spread_code_pic as qRcode from proxy_spread t1 RIGHT JOIN(" +//
            " select uuid,proxy_num,user_name,boss_id from admin_client_info " +
            " where not(uuid=#{id}) " +
            "and not(position=3) and not(position=0) and delete_flag=0" +
            ") t2" +
            " on t1.saleman_id=t2.uuid   " +
            "<where> " +
            " t1.delete_flag=0" +
            "<if test='position!=0'> and t2.boss_id=#{id} </if>" +
            "<if test='proxyNum!=null and proxyNum!=\"\"'>and  t2.proxy_num like CONCAT(\"%\",#{proxyNum},\"%\")</if>" +
            "<if test='proxyName!=null and proxyName!=\"\"'> and t2.user_name like CONCAT(\"%\",#{proxyName},\"%\")</if>" +
            "</where>" +
            " order by t1.created_time </script>")
    List<SpreadModel> getSpreadProxy(SpreadModel spreadModel);




    @Select("<script>select t4.empName as empName,t4.empNum as empNum,t4.proxyName as proxyName,t4.proxyNum as proxyNum,t1.invite_code as spreadNum," +
            "t1.spread_link as spreadLink,t1.spread_code_pic as qRcode from spread t1 RIGHT JOIN (" +//
            "SELECT t3.proxy_num as proxyNum,t3.user_name as proxyName,t2.proxy_num as empNum," +
            "t2.user_name as empName,t2.uuid as empid,t3.uuid as bossid FROM admin_client_info t2 " +
            "LEFT JOIN admin_client_info t3 on t2.boss_id=t3.uuid where t2.position=3" +
            ") t4 on t1.saleman_id=t4.empid  " +
            " <where>" +
            " t1.delete_flag=0" +
            "<if test='position!=0'> and t4.bossid=#{id} </if>" +
            "<if test='proxyName!=null and proxyName!=\"\"'> and t4.proxyName like CONCAT(\"%\",#{proxyName},\"%\") </if>" +
            "<if test='proxyNum!=null and proxyNum!=\"\"'> and t4.proxyNum like CONCAT(\"%\",#{proxyNum},\"%\") </if>" +
            "</where>" +
            "  order by t1.created_time</script>")
    List<SpreadModel> getSpreadEmp(SpreadModel spreadModel);
}
