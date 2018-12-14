package cn.com.fintheircing.admin.todotask.dao.repository;

import cn.com.fintheircing.admin.todotask.entity.AdminBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlackListRepository extends JpaRepository<AdminBlackList,String> {
    int countBlackListByIpAddress(String ip);
}
