package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.UserMesInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserMesInfoRepository extends JpaRepository<UserMesInfo,String> {
    List<UserMesInfo> findAllByIsRead(Integer a);
}
