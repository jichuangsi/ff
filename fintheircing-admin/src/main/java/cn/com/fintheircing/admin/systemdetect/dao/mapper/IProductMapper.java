package cn.com.fintheircing.admin.systemdetect.dao.mapper;

import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface IProductMapper {


    @Select("<script></script>")
    ProductModel selectCurrentProduct(Map<String,Object> params);
}
