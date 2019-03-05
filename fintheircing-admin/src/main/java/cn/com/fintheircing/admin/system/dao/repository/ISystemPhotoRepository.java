package cn.com.fintheircing.admin.system.dao.repository;

import cn.com.fintheircing.admin.system.entity.SystemPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISystemPhotoRepository  extends JpaRepository<SystemPhoto,String>{

    List<SystemPhoto> findByStayOnAndDeleteFlag(String apply,String delete);

    SystemPhoto findByUuid(String uuid);
}
