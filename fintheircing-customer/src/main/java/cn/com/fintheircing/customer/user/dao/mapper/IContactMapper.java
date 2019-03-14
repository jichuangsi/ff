package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.contract.contactModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IContactMapper {
    @Select("<script>SELECT t1.created_time AS createTime ,t2.allot AS allot,t2.lever_rate AS rate,t1.promised_money AS capital" +
            "            FROM business_contract t1 LEFT JOIN systemdetect_product t2 ON t1.product_id =t2.id WHERE" +
            "  t1.user_id =#{userId} and t1.delete_flag=\"0\"</script>")
    List<contactModel> QueryContractInfos(String userId);
    @Select("<script>select t1.available_money as availableMoney,t1.created_time as createTime,t2.lever_rate AS rate,t2.allot AS allot" +
            ",t3.current_worth as marketValue,t3.float_money as profit from business_contract t1 " +
            "left join systemdetect_product t2  ON t1.product_id =t2.id " +
            "left join  business_stock_holding t3 on t1.uuid=t3.contract_id where t1.user_id=#{uuid}" +
            " </script>")
    List<contactModel> accountManagement(String uuid);
}

