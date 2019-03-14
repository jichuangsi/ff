package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.pay.PayInfoModel;
import org.apache.ibatis.annotations.Select;

public interface IPayMapper {
    @Select("<script>select t1.amount as amount,t1.pay_way as payway,t1.method as method,t1.user_name as userName," +
            "t1.user_id as userId,t1.remark as remark from customer_recharge_recode t1 where t1.delete_falg=\"0\" and " +
            " not(t1.status =1)" +
            " </script>")
    PayInfoModel findAllPayInfo();
}
