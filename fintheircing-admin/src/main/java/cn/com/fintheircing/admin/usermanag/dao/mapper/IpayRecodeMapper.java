package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IpayRecodeMapper {
    /**
     * 把字段status改成1
     * @param model
     * @return
     */
    @Update("<script><script>")
    int updatePayInfo(RecodeInfoPayModel model);

    /***
     * 查询checkStatus =0的
     * @return
     */
    @Select("<script></script>")
    List<RecodeInfoPayModel> findAllPayInfo();
}
