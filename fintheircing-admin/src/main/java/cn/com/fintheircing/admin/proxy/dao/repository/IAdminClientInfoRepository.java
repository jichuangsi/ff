package cn.com.fintheircing.admin.proxy.dao.repository;

import cn.com.fintheircing.admin.common.entity.userInfo.AdminClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminClientInfoRepository extends JpaRepository<AdminClientInfo,String> {

}
