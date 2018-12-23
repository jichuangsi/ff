package cn.com.fintheircing.admin.proxy.dao.repository;

import cn.com.fintheircing.admin.login.entity.AdminClientLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminClientLoginInfoRepository extends JpaRepository<AdminClientLoginInfo,String> {
}
