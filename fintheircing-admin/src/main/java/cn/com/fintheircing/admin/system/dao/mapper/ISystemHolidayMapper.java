package cn.com.fintheircing.admin.system.dao.mapper;

import cn.com.fintheircing.admin.system.model.holiday.HolidayModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidaySearchModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ISystemHolidayMapper {


    @Update("<script>UPDATE system_holiday SET  `delete_flag` = '1', " +
            "`update_user_id` = #{id}, `update_user_name` = #{name}," +
            " `updated_time` = #{time}  WHERE `uuid` in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "   #{item}   </foreach></script>")
    int deleteHolidays(Map<String, Object> params);


    @Select("<script>select uuid as id ,begin_time as `start`" +
            ",end_time as `end`,remarks as remarks," +
            "`status` as `status` from system_holiday" +
            " <where> `delete_flag` = '0' " +
            "<if test='longtime!=0'> and begin_time &lt;= #{longtime} </if>" +
            "<if test='longtime!=0'> and end_time &gt;= #{longtime}   </if>" +
            "</where></script>")
    List<HolidayModel> selectHolidays(HolidaySearchModel model);


    @Select("<script>select count(1) from system_holiday where  begin_time &lt;= #{longtime} and  end_time &gt;= #{longtime}   </script>")
    int countInHoliday(@Param("longtime") long nowTime);
}
