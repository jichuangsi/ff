package cn.com.fintheircing.admin.useritem.dao.repository;

import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary,String> {

    TransactionSummary findByDeleteFlagAndStockNum(String delete,String StockNum);

    List<TransactionSummary> findByDeleteFlag(String delete);
}
