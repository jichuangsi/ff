package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.usermanag.entity.pay.RecodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPayInfoRepository extends JpaRepository<RecodeInfo,String> {

}
