package cn.com.fintheircing.admin.systemdetect.dao.mapper;

import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import org.apache.ibatis.annotations.Select;

public interface IProductMapper {


    @Select("<script></script>")
    ProductModel selectCurrentProduct(ProductModel model);
}
