package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessStockEntrust;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBusinessStockEntrustRepository extends JpaRepository<BusinessStockEntrust,String> {

    BusinessStockEntrust findByDeleteFlagAndUuid(String delete,String uuid);

    BusinessStockEntrust findByDeleteFlagAndUuidAndUserId(String delete,String uuid,String userId);

}
