package cn.com.fintheircing.admin.UserManag.dao.mapper;

import cn.com.fintheircing.admin.UserManag.model.AdminClientInfModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface userMapper {

    List<AdminClientInfModel> findAll(@Param("queryModel") AdminClientInfModel queryModel);
}
