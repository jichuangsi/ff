package cn.com.fintheircing.customer.user.dao.mapper;

import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * PayInfoMapper接口 interface
 *
 * @author yaoxiong
 * @date 2019/1/15
 */
public interface IPayMapper {
    /**
     * 查询所有支付信息
     * */
    @Select("<script></script>")
    List<RecodeInfoPayModel> findAllPayInfo();

    /**
     * 更新字段
     * @param model
     * @return
     */
    @Update("<script></script>")
   int updatePayInfo(RecodeInfoPayModel model);
}
