package cn.com.fintheircing.admin.account.dao.repository;

import cn.com.fintheircing.admin.account.entity.AdminClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAdminClientInfoRepository extends JpaRepository<AdminClientInfo, String> {

    List<AdminClientInfo> findAllByBossIdAndStatus(String BossId, String status);

    AdminClientInfo findOneByUuid(String id);

    int countByRoleGradeAndDeleteFlag(Integer role,String delete);
}
