package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessStockEntrust;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBusinessStockEntrustRepository extends JpaRepository<BusinessStockEntrust,String> {

    BusinessStockEntrust findByDeleteFlagAndUuid(String delete,String uuid);

    BusinessStockEntrust findByDeleteFlagAndUuidAndUserId(String delete,String uuid,String userId);

    BusinessStockEntrust findByDeleteFlagAndMontherAccountAndDealNo(String delete,String motherAccount,String dealNo);

    List<BusinessStockEntrust> findByDeleteFlagAndContractId(String delete,String contractId);
}
