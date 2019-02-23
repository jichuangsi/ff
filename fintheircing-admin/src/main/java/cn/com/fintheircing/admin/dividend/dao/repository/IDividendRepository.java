package cn.com.fintheircing.admin.dividend.dao.repository;

import cn.com.fintheircing.admin.dividend.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDividendRepository extends JpaRepository<Dividend,String>{

    List<Dividend> findByStatus(String status);

    Dividend findByUuid(String uuid);
}
