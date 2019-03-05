package cn.com.fintheircing.admin.system.dao.repository;

import cn.com.fintheircing.admin.system.entity.SystemAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISystemAgreementRepository extends JpaRepository<SystemAgreement,String>{

    SystemAgreement findByUuid(String uuid);

    List<SystemAgreement> findByDeleteFlag(String delete);
}
