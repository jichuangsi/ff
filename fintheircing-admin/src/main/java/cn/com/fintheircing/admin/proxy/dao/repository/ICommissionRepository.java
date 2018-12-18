package cn.com.fintheircing.admin.proxy.dao.repository;

import cn.com.fintheircing.admin.proxy.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommissionRepository extends JpaRepository<Commission,String> {

    Commission findCommissionBySalemanId(String saleId);
}
