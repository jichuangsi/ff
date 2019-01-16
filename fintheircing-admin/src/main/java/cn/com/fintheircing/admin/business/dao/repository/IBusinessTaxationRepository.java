package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessTaxation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBusinessTaxationRepository
        extends JpaRepository<BusinessTaxation,String>{

    List<BusinessTaxation> findByDeleteFlagAndBsuinessTo(String delete,String businessTo);
}
