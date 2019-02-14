package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.mes.MesInfoModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IUserMesInfoMapper {
    @Select("<script>select t1.is_read as status, t1.send_time as sendTime,t1.uuid as mesId ,t1.title as title ,t1.content as content from customer_user_mes_info t1 where delete_flag=0 order by t1.send_time DESC</script>")
    List<MesInfoModel> findAllUserMesInfo();
    @Update("<script>update customer_user_mes_info t1 set t1.is_read =1 where t1.uuid=#{mesId}</script>")
    int updateRead(String mesId);

}
