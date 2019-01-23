package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
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
            " from User_Client_Info t1 left join Admin_Client_Info t2 on t1.inviter_id =t2.uuid where t1.status=\"0\"</script>")
    List<AdminClientInfoModel> findAll( AdminClientInfoModel queryModel);

    /**
     * 查询冻结用户
     * @param queryModel
     * @return
     */
    @Select("<script>select t1.uuid as userId,t1.phone as phoneNo,t1.user_name as userName,t1.created_time as createTime,t1.cer as cer,t1.source as source,t2.proxy_num as proxyId,t2.user_name as proxyName" +
            " from User_Client_Info t1 left join Admin_Client_Info t2 on t1.inviter_id =t2.uuid where t1.status=\"1\"</script>")
    List<AdminClientInfoModel> findAllUseless(@Param("queryModel") AdminClientInfoModel queryModel);
    @Update("<script></script>")
    int changeProxyNum(@Param("queryModel") AdminClientInfoModel queryModel);

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
}
