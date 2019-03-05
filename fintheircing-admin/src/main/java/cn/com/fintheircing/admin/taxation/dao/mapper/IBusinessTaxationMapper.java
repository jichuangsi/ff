package cn.com.fintheircing.admin.taxation.dao.mapper;

import cn.com.fintheircing.admin.taxation.model.TaxationModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IBusinessTaxationMapper {

    @Select("<script>select t1.uuid as id,t1.tax_rate as taxationRate,t1.tax_name as taxationName,t1.remarks as remarks,t1.label_name as labelName,t1.fixed as fixed from business_taxation t1 right JOIN (select t3.taxation_id as taxation from business_stock_entrust t2 right JOIN business_taxation_relation t3 on t2.uuid=t3.entrust_id where t2.delete_flag=\"0\" and t2.uuid=#{entrustId}) t4 on t1.uuid = t4.taxation</script>")
    List<TaxationModel> selectEntrustTax(@Param("entrustId") String entrustId);

    @Select("<script>select t1.uuid as id,t1.tax_rate as taxationRate,t1.tax_name as taxationName,t1.remarks as remarks,t1.label_name as labelName from business_taxation t1 LEFT JOIN (select t3.taxation_id as taxation from business_stock_entrust t2 LEFT JOIN business_taxation_relation t3 on t2.uuid=t3.entrust_id where t2.delete_flag=\"0\" GROUP BY t3.taxation_id ) t4 on t1.uuid = t4.taxation</script>")
    List<TaxationModel> getEntrustTaxLabel();

    @Update("<script>UPDATE business_taxation SET  `delete_flag` = '1', " +
            " `update_user_id` = #{id}, `update_user_name` = #{name}," +
            "   `updated_time` = #{time}  WHERE `uuid` in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "    #{item}   </foreach></script>")
    int deleteTaxations(Map<String,Object> params);

    @Update("<script>update business_taxation set update_user_id=#{usreId}, update_user_name=#{userName},updated_time=#{time}" +
            "<if test='taxName!=null and taxName!=\"\"'> ,tax_name=#{taxName}</if>" +
            "<if test='remark!=null and remark!=\"\"'> ,remarks=#{remark}</if>" +
            "<if test='businessTo!=null and businessTo!=\"\"'> ,bsuiness_to=#{businessTo}</if>" +
            " where uuid=#{id} and delete_flag=\"0\"</script>")
    int updateTaxation(Map<String,Object> params);

    @Select("<script>select t1.uuid as id,t1.tax_rate as taxationRate,t1.tax_name as taxationName,t1.remarks as remarks,t1.label_name as labelName,t1.fixed as fixed from business_taxation t1 right JOIN (select t3.taxation_id as taxation,t3.entrust_id from business_stock_entrust t2 right JOIN business_taxation_relation t3 on t2.uuid=t3.entrust_id where t2.delete_flag=\"0\") t4 on t1.uuid = t4.taxation <where>" +
            "<if test='list!=null and list.size>0'> and t4.entrust_id in <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> " +
            "     #{item}   </foreach></if></where></script>")
    List<TaxationModel> selectEntrustTaxIn(@Param("list") List<String> entrustId);
}
