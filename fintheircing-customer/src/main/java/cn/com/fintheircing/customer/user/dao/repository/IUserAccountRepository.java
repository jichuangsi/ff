package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserAccountRepository extends JpaRepository<UserAccount,String>{


    @Query(value = "select account from user_account where user_id=:id for update",nativeQuery = true)
    Double findAccountByUserId(@Param("id") String id);

    @Modifying
    @Query(value = "update user_account set account=account-:money WHERE user_id=:id",nativeQuery = true)
    int updatePayAccount(@Param("money") Double promisedMoney, @Param("id") String userId);
}
