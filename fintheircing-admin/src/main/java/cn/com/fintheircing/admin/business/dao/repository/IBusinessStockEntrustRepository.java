package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessStockEntrust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBusinessStockEntrustRepository extends JpaRepository<BusinessStockEntrust,String> {

    BusinessStockEntrust findByDeleteFlagAndUuid(String delete,String uuid);

    BusinessStockEntrust findByDeleteFlagAndUuidAndUserId(String delete,String uuid,String userId);

    BusinessStockEntrust findByDeleteFlagAndMontherAccountAndDealNo(String delete,String motherAccount,String dealNo);

    @Query(value = "select * from business_stock_entrust where delete_flag='0' and contract_id=:contractId and entrust_status not in (3,4,7)",nativeQuery = true)
    List<BusinessStockEntrust> findByContractId(@Param("contractId") String contractId);

    int countByDeleteFlagAndContractIdAndEntrustStatus(String delete,String contractId,Integer entrustStatus);

    List<BusinessStockEntrust> findByDeleteFlagAndContractId(String delete,String contractId);
}
