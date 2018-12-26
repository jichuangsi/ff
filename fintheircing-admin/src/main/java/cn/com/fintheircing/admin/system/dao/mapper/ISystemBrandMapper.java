package cn.com.fintheircing.admin.system.dao.mapper;

import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface ISystemBrandMapper {

    @Update("<script>UPDATE system_brand SET  `delete_flag` = '1', " +
            " `update_user_id` = #{id}, `update_user_name` = #{name}," +
            "   `updated_time` = #{time}  WHERE `uuid` in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "    #{item}   </script>")
     int deleteBrands(Map<String,Object> params);
}
