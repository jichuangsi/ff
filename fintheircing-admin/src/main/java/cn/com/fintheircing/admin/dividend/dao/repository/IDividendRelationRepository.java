package cn.com.fintheircing.admin.dividend.dao.repository;

import cn.com.fintheircing.admin.dividend.entity.DividendRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDividendRelationRepository extends JpaRepository<DividendRelation,String> {

    DividendRelation findByUuid(String id);
}
