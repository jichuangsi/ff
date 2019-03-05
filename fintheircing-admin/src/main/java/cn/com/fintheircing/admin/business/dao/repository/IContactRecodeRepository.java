package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.record.ContactRecode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContactRecodeRepository extends JpaRepository<ContactRecode,String> {
    List<ContactRecode> findAllByCheckStatus(int checkStatus);
}
