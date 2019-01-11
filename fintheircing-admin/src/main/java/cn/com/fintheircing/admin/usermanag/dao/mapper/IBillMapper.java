package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.result.TransListModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
/**
 * IBillMapper class
 *
 * @author yaoxiong
 * @date 2019/1/8
 */

@Mapper
@Component
public interface IBillMapper {
    /**
     * 修改支付信息
     * @param model
     * @return
     */
    @Update("<script>update Bill set pay_status=\"1\",trans_amount=#{transAmount},trans_Time=#{transTime},version=version+1 " +
            "where order_id=#{orderId} and pay_status=#{payStatus}</script>")
    int updateBill(TransListModel model);
}
