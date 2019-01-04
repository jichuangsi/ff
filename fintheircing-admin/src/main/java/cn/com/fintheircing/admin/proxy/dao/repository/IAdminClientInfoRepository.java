package cn.com.fintheircing.admin.proxy.dao.repository;

import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAdminClientInfoRepository extends JpaRepository<AdminClientInfo,String> {
    List<AdminClientInfo> findAllByBossIdAndStatus(String BossId, String status);
    AdminClientInfo findOneByUuid(String id);
}
