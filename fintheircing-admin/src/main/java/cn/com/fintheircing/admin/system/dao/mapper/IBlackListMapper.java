package cn.com.fintheircing.admin.system.dao.mapper;

import cn.com.fintheircing.admin.system.model.black.BlackModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IBlackListMapper {

    @Select("<script>select uuid as id,ip_address as ipAdress,cause as remarks,created_time as createdTime from system_black_list where delete_flag=\"0\"</script>")
    List<BlackModel> getPageBlack();
}
