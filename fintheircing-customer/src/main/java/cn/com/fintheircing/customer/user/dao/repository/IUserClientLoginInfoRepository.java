package cn.com.fintheircing.customer.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;

public interface IUserClientLoginInfoRepository extends JpaRepository<UserClientLoginInfo,String>{

}
