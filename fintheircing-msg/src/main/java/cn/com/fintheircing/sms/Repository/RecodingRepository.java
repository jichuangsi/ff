package cn.com.fintheircing.sms.Repository;

import cn.com.fintheircing.sms.entity.Recoding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecodingRepository extends JpaRepository<Recoding,String> {
    Recoding findOneByPhone(String phone);
}
