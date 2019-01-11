package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.entity.AskMoneyInfo;
import cn.com.fintheircing.admin.usermanag.model.AskMoneyInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IFundsAskMapper {
    List<AskMoneyInfo> findAlluser(@Param("Model") AskMoneyInfoModel Model);
}
