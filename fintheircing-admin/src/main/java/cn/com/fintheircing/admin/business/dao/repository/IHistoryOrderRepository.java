package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.order.HistoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoryOrderRepository extends JpaRepository<HistoryOrder,String>{
}
