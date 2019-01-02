package cn.com.fintheircing.admin.Repository;

import cn.com.fintheircing.admin.common.entity.UserClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClientInfoRepository extends JpaRepository<UserClientInfo,String> {
    UserClientInfo findOneByUserId(String userId);
}
