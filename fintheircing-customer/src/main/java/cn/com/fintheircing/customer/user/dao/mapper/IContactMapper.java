package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.contact.contactModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IContactMapper {
    @Select("<script>SELECT t1.created_time AS createTime ,t2.allot AS allot,t2.lever_rate AS rate,t1.promised_money AS capital" +
            "            FROM business_contract t1 LEFT JOIN systemdetect_product t2 ON t1.product_id =t2.id WHERE" +
            "  t1.user_id =#{userId} and t1.delete_flag=\"0\"</script>")
    List<contactModel> QueryContractInfos(String userId);
    @Select("<script>SELECT t1.available_money AS availableMoney,t1.created_time AS createTime,t2.lever_rate AS rate,t2.allot AS allot" +
            "  ,t3.current_worth AS marketValue,t3.float_money AS profit FROM business_contract t1 " +
            "   LEFT JOIN systemdetect_product t2 ON t1.product_id =t2.id" +
            "   LEFT JOIN  business_stock_holding t3 ON t1.uuid=t3.contract_id WHERE t1.user_id=#{uuid}" +
            " and t1.delete_flag=\"0\" and t3.delete_flag=\"0\"  </script>")
    List<contactModel> accountManagement(String uuid);
}
