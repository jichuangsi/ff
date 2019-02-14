package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.BankCardModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface IBankMapper {

    @Insert("<script>insert bank_card(bank_id,bank_name,user_id,user_name,phone_no) values(#{bankId},#{bankName},#{userId},#{userName},#{phoneNo}) </script>")
    @Options(useGeneratedKeys = false, keyProperty = "uuid",keyColumn = "uuid")
    int insertBank(BankCardModel model);
}
