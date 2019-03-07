package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.order.AcceptOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAcceptOrderRepository extends JpaRepository<AcceptOrder,String>{
}
