package cn.com.fintheircing.admin.promisedUrls.dao.repository;

import cn.com.fintheircing.admin.promisedUrls.entity.UrlsPermised;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPermisedUrlsRepository extends JpaRepository<UrlsPermised,String>{

    UrlsPermised findOneByUuidAndDeleteFlag(String id, String flag);

    List<UrlsPermised> findByDeleteFlag(String flag);
}
