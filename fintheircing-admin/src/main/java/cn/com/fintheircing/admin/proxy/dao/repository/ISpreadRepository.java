package cn.com.fintheircing.admin.proxy.dao.repository;

import cn.com.fintheircing.admin.proxy.entity.ProxySpread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISpreadRepository extends JpaRepository<ProxySpread,String>{
}
