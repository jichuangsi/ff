package cn.com.fintheircing.admin.UserManag.dao.mapper;

import cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel;
import cn.com.fintheircing.admin.common.entity.AskMoneyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface fundsAskMapper {
    List<AskMoneyInfo> findAlluser(@Param("Model") AskMoneyInfoModel Model);
}
