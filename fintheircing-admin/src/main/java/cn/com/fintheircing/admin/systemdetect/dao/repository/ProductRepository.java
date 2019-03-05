package cn.com.fintheircing.admin.systemdetect.dao.repository;

import cn.com.fintheircing.admin.systemdetect.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    List<Product> findProductsByAllot(Integer productNo);

    Product findOneById(String id);

    int countAllBy();

}
