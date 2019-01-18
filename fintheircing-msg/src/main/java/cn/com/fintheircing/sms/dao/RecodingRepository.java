package cn.com.fintheircing.sms.dao;

import cn.com.fintheircing.sms.entity.Recoding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecodingRepository extends JpaRepository<Recoding,String> {

}
