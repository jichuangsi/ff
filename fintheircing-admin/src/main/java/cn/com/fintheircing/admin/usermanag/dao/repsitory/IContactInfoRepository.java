package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.usermanag.entity.contact.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContactInfoRepository extends JpaRepository<ContactInfo,String> {
    List<ContactInfo> findAllByGoodsType(String goodType);

}
