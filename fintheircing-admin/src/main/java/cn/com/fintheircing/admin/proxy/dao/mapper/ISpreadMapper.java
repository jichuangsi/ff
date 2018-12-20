package cn.com.fintheircing.admin.proxy.dao.mapper;

import org.apache.ibatis.annotations.Select;

public interface ISpreadMapper {


    @Select("<script>select count(1) from spread where invite_code=#{code}</script>")
    int countInvit(String code);
}
