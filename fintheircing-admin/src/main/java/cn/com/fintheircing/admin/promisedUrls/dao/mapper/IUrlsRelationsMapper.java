package cn.com.fintheircing.admin.promisedUrls.dao.mapper;

import cn.com.fintheircing.admin.promisedUrls.model.UrlsModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IUrlsRelationsMapper {


    @Select("<script>select t1.uuid as id,t1.position as position," +
            "t1.role as role,t2.url_name as urlName," +
            "t2.url as url from urls_relations t1 " +
            "LEFT JOIN urls_permised t2 on t1.url_id=t2.uuid " +
            "where t1.delete_flag=\"0\"</script>")
    List<UrlsModel> selectAllRelations();

    @Update("<script>UPDATE urls_relations SET  `delete_flag` = '1', " +
            "`update_user_id` = #{id}, `update_user_name` = #{name}," +
            " `updated_time` = #{time}  WHERE `uuid` in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">  " +
            "  #{item}   </foreach></script>")
    int updateDeleteFlag(Map<String,Object> maps);

}
