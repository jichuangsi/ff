package cn.com.fintheircing.admin.topLevel.dao.repository;


import cn.com.fintheircing.admin.topLevel.entity.ParentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * SecuritiesInfo 的JPA
 *
 * @author yaoxiong
 * @date 2019/1/18
 */
public interface IParentAccountRepository extends JpaRepository<ParentAccount,String> {
    ParentAccount findOneByUuid(String id);
}
