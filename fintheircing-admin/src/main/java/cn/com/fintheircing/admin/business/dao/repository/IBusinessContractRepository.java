package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IBusinessContractRepository extends JpaRepository<BusinessContract,String>{


    @Modifying
    @Query(value = "update BusinessContract set availableMoney=availableMoney-:coldMoney" +
            ",coldMoney=coldMoney+:coldMoney,version=:version+1 where uuid=:contractId and deleteFlag='0'  and version=:version")
    int updateColdMoney(@Param("contractId") String contractId,
                        @Param("coldMoney") Double coldMoney, @Param("version") Integer version);

    BusinessContract findByUuid(String id);
}
