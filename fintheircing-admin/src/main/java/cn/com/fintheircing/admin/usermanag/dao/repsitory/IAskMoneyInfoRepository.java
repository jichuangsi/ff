package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.usermanag.entity.AskMoneyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAskMoneyInfoRepository extends JpaRepository<AskMoneyInfo,String> {

//    List<AskMoneyInfo> findAllByBankCardAndUserIdAndPhoneAndUserName(String bankCard,String userId,String phone,String userName);
}
