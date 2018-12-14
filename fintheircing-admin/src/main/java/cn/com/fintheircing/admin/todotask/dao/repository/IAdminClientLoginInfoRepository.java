package cn.com.fintheircing.admin.todotask.dao.repository;

import cn.com.fintheircing.admin.todotask.entity.AdminClientLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminClientLoginInfoRepository extends JpaRepository<AdminClientLoginInfo,String> {
}
