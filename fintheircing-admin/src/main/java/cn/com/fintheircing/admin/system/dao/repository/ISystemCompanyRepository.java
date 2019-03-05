package cn.com.fintheircing.admin.system.dao.repository;

import cn.com.fintheircing.admin.system.entity.SystemCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISystemCompanyRepository extends JpaRepository<SystemCompany,String> {

    List<SystemCompany> findByDeleteFlag(String delete);

    SystemCompany findByUuid(String uuid);
}
