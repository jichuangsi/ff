package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RecodeInfoPay interface
 *
 * @author yaoxiong
 * @date 2019/1/14
 */
public interface IRecodInfoPayRepository extends JpaRepository<RecodeInfoPay,String> {
    List<RecodeInfoPay> findAllByUserId(String userId);
}
