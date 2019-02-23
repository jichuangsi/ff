package cn.com.fintheircing.admin.system.dao.repository;

import cn.com.fintheircing.admin.system.entity.SystemBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlackListRepository extends JpaRepository<SystemBlackList,String> {

    List<SystemBlackList> findByDeleteFlag(String delete);

    SystemBlackList findByUuid(String id);

}
