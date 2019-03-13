package cn.com.fintheircing.admin.risk.dao.repository;

import cn.com.fintheircing.admin.risk.entity.SystemRisk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISystemRiskRepository extends JpaRepository<SystemRisk,String>{

    int countAllBy();

    SystemRisk findAllBy();
}
