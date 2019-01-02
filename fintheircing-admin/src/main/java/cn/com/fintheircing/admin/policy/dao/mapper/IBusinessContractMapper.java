package cn.com.fintheircing.admin.policy.dao.mapper;

import cn.com.fintheircing.admin.policy.model.TranferContractModel;
import cn.com.fintheircing.admin.policy.model.TranferControlContractModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IBusinessContractMapper {

    @Select("<scirpt></script>")
    List<TranferContractModel>  selectPageContracts(TranferContractModel contractModel);


    @Select("<scirpt></script>")
    List<TranferControlContractModel> selectPageControl(TranferControlContractModel model);
}
