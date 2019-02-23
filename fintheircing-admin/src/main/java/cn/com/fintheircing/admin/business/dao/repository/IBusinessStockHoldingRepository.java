package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessStockHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBusinessStockHoldingRepository
        extends JpaRepository<BusinessStockHolding,String> {

    @Transactional
    @Query(value = "select * from business_stock_holding where delete_flag=:delete and uuid=:uuid FOR UPDATE ",nativeQuery = true)
    BusinessStockHolding findByDeleteFlagAndUuid(@Param("delete") String delete, @Param("uuid") String uuid);

    @Transactional
    @Query(value = "select * from business_stock_holding t where delete_flag=:delete and stock_id=:stock and contract_id=:contractId FOR UPDATE ",nativeQuery = true)
    BusinessStockHolding findBusinessStockHoldingByDeleteFlagAndStockIdAndContractId(@Param("delete") String delete,@Param("stock") String stockId,@Param("contractId") String contractId);

    List<BusinessStockHolding> findByDeleteFlagAndContractId(String delete,String contractId);

   // List<BusinessStockHolding> findByDeleteFlag(String delete);

    @Transactional
    @Query(value = "select * from business_stock_holding  where delete_flag=:delete and uuid=:uuid for UPDATE ",nativeQuery = true)
    BusinessStockHolding findByDeleteFlag(@Param("delete") String delete,@Param("uuid") String uuid);

    List<BusinessStockHolding> findByStockId(String stockId);

    BusinessStockHolding findByContractIdAndStockId(String contractId,String stockId);
}
