package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.usermanag.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * IBankIdRepository class
 *
 * @author yaoxiong
 * @date
 */
@Repository
public interface IBankIdRepository extends JpaRepository<BankCard,String> {
    BankCard findOneByUuid(String id);
}
