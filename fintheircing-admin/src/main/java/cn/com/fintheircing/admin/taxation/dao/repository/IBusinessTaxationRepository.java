package cn.com.fintheircing.admin.taxation.dao.repository;

import cn.com.fintheircing.admin.taxation.entity.BusinessTaxation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBusinessTaxationRepository
        extends JpaRepository<BusinessTaxation,String>{

    List<BusinessTaxation> findByDeleteFlagAndBsuinessTo(String delete,String businessTo);

    int countByLabelName(String labelName);

    List<BusinessTaxation> findByDeleteFlagAndUuid(String delete,String uuid);

    List<BusinessTaxation> findByDeleteFlag(String delete);
}
