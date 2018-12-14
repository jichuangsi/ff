package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.UserBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlackListRepository extends JpaRepository<UserBlackList,String> {
    int countBlackListByIpAddress(String ip);
}
