package cn.com.fintheircing.admin.business.dao.mapper;

import cn.com.fintheircing.admin.policy.model.TranferContractModel;
import cn.com.fintheircing.admin.policy.model.TranferControlContractModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IBusinessContractMapper {

    @Select("<script>select count(1) from business_contract t1 LEFT JOIN systemdetect_product t2 on t1.product_id=t2.id " +
            "<where>" +
            " t1.user_id=#{userId}" +
            "<if test='productName!=\"\" and productName!=null'> and t2.allot=#{productName}</if>" +
            "</where></script>")
    int countSameContract(Map<String,Object> params);


    @Select("<scirpt></script>")
    List<TranferContractModel> selectPageContracts(TranferContractModel contractModel);


    @Select("<scirpt></script>")
    List<TranferControlContractModel> selectPageControl(TranferControlContractModel model);
}
