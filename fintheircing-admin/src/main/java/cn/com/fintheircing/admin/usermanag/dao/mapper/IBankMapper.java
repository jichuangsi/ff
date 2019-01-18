package cn.com.fintheircing.admin.usermanag.dao.mapper;

import cn.com.fintheircing.admin.usermanag.model.BankCardModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * IBankMapper class
 *
 * @author keriezhang
 * @date 2016/10/31
 */
public interface IBankMapper {
    @Select("<script></script>")
    List<BankCardModel> findAllBankCard(BankCardModel model);
}
