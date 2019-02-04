package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessStockHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface IBusinessStockHoldingRepository
        extends JpaRepository<BusinessStockHolding,String> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select t from BusinessStockHolding t where t.deleteFlag=:delete and t.uuid=:uuid ")
    BusinessStockHolding findByDeleteFlagAndUuid(@Param("delete") String delete, @Param("uuid") String uuid);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select t from BusinessStockHolding t where t.deleteFlag=:delete and t.stockId=:stock and t.contractId=:contractId ")
   BusinessStockHolding findBusinessStockHoldingByDeleteFlagAndStockIdAndContractId(@Param("delete") String delete,@Param("stock") String stockId,@Param("contractId") String contractId);

    List<BusinessStockHolding> findByDeleteFlagAndContractId(String delete,String contractId);

    List<BusinessStockHolding> findByDeleteFlag(String delete);
}
