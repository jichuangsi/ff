package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.BankCardModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * IBankMapper class
 *
 * @author keriezhang
 * @date 2016/10/31
 */
public interface IBankMapper {
    @Select("<script>select t1.bank_id as bankId,t1.bank_name as bankName,t1.user_id as userId,t1.user_name as userName" +
            " t1.phone_no as phoneNo from bank_card t1" +
            " <where><if Test = \"bankId !=null and bankId !=''\"> and t1.bank_id=#{bankId} </if>" +
            "  <if Test =\"userId!=null and userId!=''\">and t1.user_id =#{userId}</if>" +
            "  <if Test =\"phoneNo!=null and phoneNo!=''\">and t1.phone_no=#{phoneNo}</if>" +
            "   <if Test =\"userName!=null and userName!=''\"> and t1.user_name=#{userName}</if> " +
            " and t1.status=\"0\"</where> </script>")
    List<BankCardModel> findAllBankCard(BankCardModel model);
    @Update("<script>update bank_card t1 set t1.status =\"1\" where t1.bank_id=#{id}</script>")
   int UpdateBankCard(String id);
}
