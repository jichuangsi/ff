package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.usermanag.entity.MesInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMesInfoRepository extends JpaRepository<MesInfo,String> {
    MesInfo findOneByUuid(String id);
}
