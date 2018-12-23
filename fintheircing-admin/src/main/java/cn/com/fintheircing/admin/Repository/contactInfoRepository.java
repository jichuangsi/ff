package cn.com.fintheircing.admin.Repository;

import cn.com.fintheircing.admin.common.entity.Contact.contactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface contactInfoRepository extends JpaRepository<contactInfo,String> {
    List<contactInfo> findAllByGoodsType(String goodType);

}
