package cn.com.fintheircing.admin.taxation.dao.repository;

import cn.com.fintheircing.admin.taxation.entity.BusinessTaxationRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBusinessTaxationRelationRepository
        extends JpaRepository<BusinessTaxationRelation,String>{


}
