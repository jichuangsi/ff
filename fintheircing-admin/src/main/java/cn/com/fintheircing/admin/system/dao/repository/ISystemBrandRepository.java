package cn.com.fintheircing.admin.system.dao.repository;

import cn.com.fintheircing.admin.system.entity.SystemBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISystemBrandRepository  extends JpaRepository<SystemBrand,String>{

    SystemBrand findByUuid(String uuid);
}
