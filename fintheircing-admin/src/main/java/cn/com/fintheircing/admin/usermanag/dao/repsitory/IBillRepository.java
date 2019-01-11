package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.usermanag.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepository extends JpaRepository<Bill,String> {
}
