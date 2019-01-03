package cn.com.fintheircing.admin.systemdetect.dao.repository;

import cn.com.fintheircing.admin.systemdetect.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {





}
