package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.usermanag.entity.pay.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPayInfoRepository extends JpaRepository<PayInfo ,String> {
}