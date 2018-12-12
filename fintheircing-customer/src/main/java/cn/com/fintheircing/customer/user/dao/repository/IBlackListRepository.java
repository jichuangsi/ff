package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlackListRepository extends JpaRepository<BlackList,String> {
    int countBlackListByIpAddress(String ip);
}
