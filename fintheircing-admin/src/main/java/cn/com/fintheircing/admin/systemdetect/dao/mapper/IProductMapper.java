package cn.com.fintheircing.admin.systemdetect.dao.mapper;

import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
@Mapper
public interface IProductMapper {


    @Select("<script>select t1.allot as allot,t1.id as id,t1.entry_amount as entryAmount,t1.financing_time as financingTime,t1.lever_rate as leverRate,t1.liquidation as liquidation,t1.money_in_contact as moneyInContact,t1.money_in_deal as moneyInDeal,t1.out_amount as outAmount,t1.worn_line as wornLine from systemdetect_product t1 where  t1.allot=#{productNo} order by t1.lever_rate</script>")
    List<ProductModel> selectCurrentProduct(Map<String,Object> params);


    @Select("<script>select t1.allot as allot,t1.uuid as id,t1.entry_amount as entryAmount,t1.financing_time as financingTime,t1.lever_rate as leverRate,t1.liquidation as liquidation,t1.money_in_contact as moneyInContact,t1.money_in_deal as moneyInDeal,t1.out_amount as outAmount,t1.worn_line as wornLine from systemdetect_product t1 where  t1.id=#{id}</script>")
    ProductModel selectProduct(@RequestParam("id") String productId);
    @Select("<script>select t1.allot as allot,t1.uuid as id,t1.entry_amount as entryAmount,t1.financing_time as financingTime,t1.lever_rate as leverRate,t1.liquidation as liquidation,t1.money_in_contact as moneyInContact,t1.money_in_deal as moneyInDeal,t1.out_amount as outAmount,t1.worn_line as wornLine from systemdetect_product t1 where  t1.allot=0</script>")
    List<ProductModel> findAllByDay();
    @Select("<script>select t1.allot as allot,t1.uuid as id,t1.entry_amount as entryAmount,t1.financing_time as financingTime,t1.lever_rate as leverRate,t1.liquidation as liquidation,t1.money_in_contact as moneyInContact,t1.money_in_deal as moneyInDeal,t1.out_amount as outAmount,t1.worn_line as wornLine from systemdetect_product t1 where  t1.allot=1</script>")
    List<ProductModel> findAllByMonth();
    @Select("<script>select t1.allot as allot,t1.uuid as id,t1.entry_amount as entryAmount,t1.financing_time as financingTime,t1.lever_rate as leverRate,t1.liquidation as liquidation,t1.money_in_contact as moneyInContact,t1.money_in_deal as moneyInDeal,t1.out_amount as outAmount,t1.worn_line as wornLine from systemdetect_product t1 where  t1.allot=2</script>")
    List<ProductModel> findAllBySpec();
}
