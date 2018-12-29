package cn.com.fintheircing.admin.login.dao.repository;

import cn.com.fintheircing.admin.common.entity.SystemBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlackListRepository extends JpaRepository<SystemBlackList,String> {

}
