package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBankRepository extends JpaRepository<BankCard,String> {

}
