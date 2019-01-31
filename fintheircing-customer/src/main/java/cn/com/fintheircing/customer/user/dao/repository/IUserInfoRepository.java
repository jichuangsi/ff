package cn.com.fintheircing.customer.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.fintheircing.customer.user.entity.UserClientInfo;

public interface IUserInfoRepository extends JpaRepository<UserClientInfo,String>{
	
	UserClientInfo findOneByUserName(String userName);

	UserClientInfo findByUuid(String uuid);

	int countByDeleteFlagAndIdCard(String delete,String idCard);

}
