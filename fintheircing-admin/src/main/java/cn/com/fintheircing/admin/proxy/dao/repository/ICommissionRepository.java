package cn.com.fintheircing.admin.proxy.dao.repository;

import cn.com.fintheircing.admin.proxy.entity.ProxyCommission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommissionRepository extends JpaRepository<ProxyCommission,String> {

    ProxyCommission findCommissionBySalemanId(String saleId);
}
