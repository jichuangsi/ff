package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import org.mapstruct.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface userMapper {

    List<AdminClientInfoModel> findAll(@Param("queryModel") AdminClientInfoModel queryModel);
}
