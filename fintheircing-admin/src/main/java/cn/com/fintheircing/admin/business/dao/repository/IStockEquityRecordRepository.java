package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.record.StockEquityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockEquityRecordRepository extends JpaRepository<StockEquityRecord,String>{

    StockEquityRecord findByUuidAndDeleteFlag(String uuid, String delete);
}
