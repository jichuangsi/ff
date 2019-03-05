package cn.com.fintheircing.admin.system.dao.mapper;

import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface ISystemPhotoMapper {

    @Update("<script>UPDATE system_photo SET  `delete_flag` = '1', " +
            " `update_user_id` = #{id}, `update_user_name` = #{name}," +
            "   `updated_time` = #{time}  WHERE `uuid` in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "    #{item}   </foreach></script>")
    int deletePhoto(Map<String, Object> params);
}
