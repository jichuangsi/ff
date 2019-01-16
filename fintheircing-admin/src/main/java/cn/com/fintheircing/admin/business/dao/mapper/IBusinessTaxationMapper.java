package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.business.model.TaxationModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IBusinessTaxationMapper {

    @Select("<script>select t1.uuid as id,t1.tax_rate as taxationRate,t1.tax_name as taxationName,t1.remarks as remarks from business_taxation t1 LEFT JOIN (select t3.taxation_id as taxation from business_stock_entrust t2 LEFT JOIN business_taxation_relation t3 on t2.uuid=t3.entrust_id where t2.delete_flag=\"0\" and t2.uuid=#{id}) t4 on t1.uuid = t4.taxation</script>")
    List<TaxationModel> selectEntrustTax(@Param("id") String id);
}
