package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAccountRepository extends JpaRepository<UserAccount,String>{
}
