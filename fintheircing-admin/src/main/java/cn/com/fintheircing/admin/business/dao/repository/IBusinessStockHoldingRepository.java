package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessStockHolding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBusinessStockHoldingRepository
        extends JpaRepository<BusinessStockHolding,String> {

}
