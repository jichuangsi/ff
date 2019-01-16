package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * IUserMapper class
 *
 * @author yaoxiong
 * @date 2019/1/11
 */
@Mapper
@Component
public interface IUserMapper {
    /**
     * 查询非冻结用户
     * @param queryModel
     * @return
     */
    @Select("<script>select t1.uuid as userId,t1.phone as phoneNo ,t1.user_name as userName,t1.created_time as createTime,t1.cer as cer,t1.source as source,t2.proxy_num as proxyId,t2.user_name as proxyName ,t2.uuid as　emplooyeeId" +
            "from User_Client_Info t1 left join Admin_Client_Info t2 on t1.inviter_id =t2.uuid" +
            "<where>" +
            "t1.status=\"0\" " +
            "<if test=\"startTime!=null and startTime!=''\">and t1.created_time &gt;=#{startTime}</if>" +
            "<if test=\"endTime!=null and endTime!=''\"> and t1.created_time &lt;=${endTime}</if>" +
            "<if test=\"proxyId!=null and proxyId!=''\">and t2.proxy_num LIKE CONCAT('%',#{proxyId},'%')</if>" +
            "<if test=\"userId !=null and userId !=''\"> and t1.uuid like concat('%',#{userId},'%')</if>" +
            "<if test=\"phoneNo!=null and phoneNo!=''\">and t1.phone like concat('%',#{phoneNo},'%')</if>" +
            "<if test=\"userName!=null and userName!=''\"> and t1.user_name like concat('%',#{userName},'%')</if>" +
            "<if test=\"source!=null and source!=''\"> and t1.source like concat('%',#{source},'%')</if>" +
            "<if test=\"emplooyeeId!=null and emplooyeeId!=''\">and t1.inviter_id like concat('%',#{emplooyeeId},'%')</if></where></script>")
    List<AdminClientInfoModel> findAll(@Param("queryModel") AdminClientInfoModel queryModel);

    /**
     * 查询冻结用户
     * @param queryModel
     * @return
     */
    List<AdminClientInfoModel> findAllUseless(@Param("queryModel") AdminClientInfoModel queryModel);
    int changeProxyNum(@Param("queryModel") AdminClientInfoModel queryModel);
}
