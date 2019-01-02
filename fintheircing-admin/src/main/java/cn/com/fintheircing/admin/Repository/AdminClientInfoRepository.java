package cn.com.fintheircing.admin.Repository;

import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminClientInfoRepository extends JpaRepository<AdminClientInfo,String> {

    List<AdminClientInfo> findAllByBossIdAndStatus(String BossId, String status);
    AdminClientInfo findOneByUuid(String id);
}


