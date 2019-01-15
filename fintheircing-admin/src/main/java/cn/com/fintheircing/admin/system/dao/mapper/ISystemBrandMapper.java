package cn.com.fintheircing.admin.system.dao.mapper;

import cn.com.fintheircing.admin.system.model.brand.BrandModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ISystemBrandMapper {

    @Update("<script>UPDATE system_brand SET  `delete_flag` = '1', " +
            " `update_user_id` = #{id}, `update_user_name` = #{name}," +
            "   `updated_time` = #{time}  WHERE `uuid` in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "    #{item}   </foreach></script>")
     int deleteBrands(Map<String, Object> params);



    @Select("<script>select uuid as uuid,brand_name as `name`,apply_on as applyOn," +
            "created_time as beiginTime,updated_time as modifyTime " +
            "from system_brand WHERE delete_flag=\"0\" ORDER BY created_time</script>")
    List<BrandModel> selectBrand();
}
