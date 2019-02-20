package cn.com.fintheircing.admin.risk.dao.mapper;

import cn.com.fintheircing.admin.risk.entity.BusinessContractRisk;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface IBusinessContractRiskMapper {

    @Select("<script>select * from business_contract_risk where contract_id=#{contractId}</script>")
    BusinessContractRisk selectByContractId(@Param("contractId") String contractId);
}
