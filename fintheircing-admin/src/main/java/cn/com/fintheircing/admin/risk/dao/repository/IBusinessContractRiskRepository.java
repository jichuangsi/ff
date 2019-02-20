package cn.com.fintheircing.admin.risk.dao.repository;

import cn.com.fintheircing.admin.risk.entity.BusinessContractRisk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBusinessContractRiskRepository extends JpaRepository<BusinessContractRisk,String> {

    BusinessContractRisk findBusinessContractRiskByContractId(String contractId);
}
