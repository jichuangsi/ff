package cn.com.fintheircing.admin.system.dao.repository;

import cn.com.fintheircing.admin.system.entity.SystemBankCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISystemBankCardRepository extends JpaRepository<SystemBankCard,String> {

    SystemBankCard findByUuid(String uuid);

    List<SystemBankCard> findByDeleteFlag(String delete);
}
