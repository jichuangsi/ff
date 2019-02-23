package cn.com.fintheircing.admin.useritem.dao.repository;

import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary,String> {

    TransactionSummary findByDeleteFlagAndStockNum(String delete,String StockNum);

    List<TransactionSummary> findByDeleteFlag(String delete);

    TransactionSummary findOneByStockNum(String stockNum);

    List<TransactionSummary> findAllByIdIn(List<String> ids);

    TransactionSummary findByStockNameContainingAndStockNum(String stockName,String stockCode);

    TransactionSummary findOneById(String id);
}
