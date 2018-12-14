package cn.com.fintheircing.admin.todotask.dao.repository;

import cn.com.fintheircing.admin.todotask.entity.AdminClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminClientInfoRepository extends JpaRepository<AdminClientInfo,String> {
}
