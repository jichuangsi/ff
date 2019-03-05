package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * IUserMapper class
 *
 * @author yaoxiong
 * @date 2019/1/11
 */
public interface IUserMapper {
    /**
     * 查询非冻结用户
     * @param queryModel
     * @return
     */
    @Select("<script>select t1.uuid as userId,t1.phone as phoneNo,t1.user_name as userName,t1.created_time as createTime,t1.cer as cer,t1.source as source,t2.proxy_num as proxyId,t2.user_name as proxyName" +
            ",t2.boss_id as bossId  from user_client_info t1 left join admin_client_info t2 on t1.inviter_id =t2.uuid <where>" +
            " <if Test=\"createTime!=null and createTime!=''\"> t1.created_time &lt;= STR_TO_DATE('${createTime}','%Y-%m-%d %H:%i:%S')</if>" +
            " <if Test=\"endtime!=null and endtime!=''\">and t1.created_time &gt;= STR_TO_DATE('${endtime}','%Y-%m-%d %H:%i:%S')</if>" +
            " <if Test=\"userId!=null and userId!=''\">and t1.uuid=#{userId}</if>" +
            " <if Test=\"phoneNo!=null and phoneNo!=''\"> and t1.phone=#{phoneNo}</if>" +
            " <if Test=\"userName!=null and userName!=''\"> and t1.user_name=#{userName}</if>" +
            "  <if Test=\"source!=null and source!=''\"> and t1.source#{source}</if>" +
            "  <if Test=\"proxyId!=null and proxyId!=''\"> and t2.proxy_num =#{proxyId}</if> " +
            "  <if Test=\"emplooyeeId!=null and emplooyeeId!=''\">and t2.uuid=#{emplooyeeId}</if>" +
            "and t1.status=\"0\"</where> </script>")
    List<AdminClientInfoModel> findAll( AdminClientInfoModel queryModel);

    /**
     * 查询冻结用户
     * @param queryModel
     * @return
     */
    @Select("<script>select t1.uuid as userId,t1.phone as phoneNo,t1.user_name as userName,t1.created_time as createTime,t1.cer as cer,t1.source as source,t2.proxy_num as proxyId,t2.user_name as proxyName " +
            ",t2.boss_id as bossId  from user_client_info t1 left join admin_client_info t2 on t1.inviter_id =t2.uuid <where> " +
            " <if Test=\"createTime!=null and createTime!=''\"> t1.created_time &lt;= STR_TO_DATE('${createTime}','%Y-%m-%d %H:%i:%S')</if> " +
            " <if Test=\"endtime!=null and endtime!=''\">and t1.created_time &gt;= STR_TO_DATE('${endtime}','%Y-%m-%d %H:%i:%S')</if> "+
           " <if Test=\"userId!=null and userId!=''\">and t1.uuid=#{userId}</if> "  +
           " <if Test=\"phoneNo!=null and phoneNo!=''\"> and t1.phone=#{phoneNo}</if> " +
            " <if Test=\"userName!=null and userName!=''\"> and t1.user_name=#{userName}</if> " +
            "  <if Test=\"source!=null and source!=''\"> and t1.source#{source}</if> " +
            "  <if Test=\"proxyId!=null and proxyId!=''\"> and t2.proxy_num =#{proxyId}</if> "  +
            "  <if Test=\"emplooyeeId!=null and emplooyeeId!=''\">and t2.uuid=#{emplooyeeId}</if> " +
            "and t1.status=\"1\"</where> </script>")
    List<AdminClientInfoModel> findAllUseless(@Param("queryModel") AdminClientInfoModel queryModel);

    @Update("<script>update user_client_info t1 set t1.inviter_id=#{proxyId} where t1.uuid=#{userId}</script>")
    int changeProxyNum(Map<String,Object> pamrs);

    /**
     * 冻结状态
     */
    @Update("<script>update User_Client_Info t1 set t1.status=\"1\" where t1.uuid=#{id}</script>")
    int  updateStatus(String id);
    /**
     * 解冻状态
     */
    @Update("<script>update User_Client_Info t1 set t1.status=\"0\" where t1.uuid=#{id}</script>")
    int  restoreStatus(String id);

    /**
     * 根据Id查询详细情况
     * @param id
     * @return
     */
    @Select("<script>SELECT t1.uuid AS userId,t1.user_name AS userName,t1.phone AS phoneNo,t1.id_card AS idCard " +
            "t1.status as accountStatus,t1.inviter_id AS proxyId,t3.proxy_num AS emplooyeeId,t2.account AS balanceMoney,t2.frezze_amount AS frezzeAmount,t1.remark AS remark,t1.cer AS cer " +
            "FROM user_client_info t1 LEFT JOIN user_account t2 ON t1.uuid=t2.user_id LEFT JOIN admin_client_info t3 ON t1.inviter_id=t3.uuid  where t1.uuid=#{id}</script>")
    List<AdminClientInfoModel> findAllDetails(String id);
    @Update("<script> update user_account t1 set t1.account =#{amount} where t1.user_id=#{userId}</script>")
    int changeAmount(Map<String,Object> parms);
}
