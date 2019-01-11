package cn.com.fintheircing.admin.system.dao.repository;

import cn.com.fintheircing.admin.system.entity.SystemHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISystemHolidayRepository extends JpaRepository<SystemHoliday,String> {

    SystemHoliday findByUuid(String uuid);
}
