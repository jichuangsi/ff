package cn.com.fintheircing.customer.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;

public interface IUserClientLoginInfoRepository extends JpaRepository<UserClientLoginInfo, String> {
    UserClientLoginInfo findOneByUuid(String id);

    UserClientLoginInfo findOneByAndClientInfoId(String userId);

    UserClientLoginInfo findByDeleteFlagAndLoginNameAndPwd(String delete, String loginName, String Pwd);

    UserClientLoginInfo findByDeleteFlagAndClientInfoId(String delete,String clientId);

}
