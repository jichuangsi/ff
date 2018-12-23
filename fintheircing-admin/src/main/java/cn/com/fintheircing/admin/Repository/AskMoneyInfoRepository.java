package cn.com.fintheircing.admin.Repository;

import cn.com.fintheircing.admin.common.entity.AskMoneyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AskMoneyInfoRepository extends JpaRepository<AskMoneyInfo,String> {

    List<AskMoneyInfo> findAllByBankCardAndUserIdAndPhoneAndUserName(String bankCard,String userId,String phone,String userName);
}
