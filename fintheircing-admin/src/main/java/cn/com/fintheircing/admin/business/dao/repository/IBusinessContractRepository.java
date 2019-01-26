package cn.com.fintheircing.admin.business.dao.repository;

import cn.com.fintheircing.admin.business.entity.BusinessContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBusinessContractRepository extends JpaRepository<BusinessContract,String>{


    @Modifying
    @Query(value = "update BusinessContract set availableMoney=availableMoney-:coldMoney" +
            ",coldMoney=coldMoney+:coldMoney,version=:version+1 where uuid=:contractId and deleteFlag='0'  and version=:version")
    int updateAvailableMoney(@Param("contractId") String contractId,
                        @Param("coldMoney") Double coldMoney,@Param("version") Integer version);


    @Modifying
    @Query(value = "update BusinessContract set coldMoney=coldMoney-:coldMoney,version=:version+1 where uuid=:contractId and deleteFlag='0'  and version=:version")
    int updateColdMoney(@Param("contractId") String contractId,
                        @Param("coldMoney") Double coldMoney, @Param("version") Integer version);


    BusinessContract findByUuid(String id);

    int countByDeleteFlagAndUuidAndUserId(String delete,String contractId,String userId);

    /*@Query(value = "select t1.version as version,t1.uuid as id,t1.borrow_money as borrowMoney,t2.customer_max_account as customerMaxAccount,t2.hold_over_currency as holdOverCurrency,t2.hold_over_five_avg as holdOverFiveAvg,t2.shock_shut_down as shockShutDown,t2.ventur_edition_max_account as venturEditionMaxAccount,t1.available_money as  canUseMoney,t1.promised_money as promisedMoney,t2.abort_line as abortLine,t2.warning_line as warningLine,t1.worth as worth,t1.cold as coldCash,t1.moneyInDeal as businessRate from (select t3.uuid,t4.worth,t3.borrow_money,t3.available_money,t3.promised_money,t3.risk_id,t3.cold_money as cold," +
            "t3.money_in_deal as moneyInDeal,t3.version" +
            " from (select t5.uuid,t5.delete_flag,t5.borrow_money,t5.available_money,t5.promised_money,t5.risk_id,t5.cold_money,t6.money_in_deal,t5.version from business_contract t5 LEFT JOIN systemdetect_product t6 on t5.product_id=t6.id) t3 LEFT JOIN (" +
            "select contract_id,sum(current_worth) as worth from business_stock_holding where delete_flag='0' group BY contract_id" +
            ") t4 on t3.uuid = t4.contract_id where t3.delete_flag='0') t1 LEFT JOIN business_contract_risk t2 on  t1.risk_id=t2.uuid where t1.uuid = :contractId and t2.delete_flag='0'",nativeQuery = true)
    ContractModel findBycontractId(@Param("contractId") String contractId);*/


    List<BusinessContract> findByDeleteFlag(String delete);
}
