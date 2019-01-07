package cn.com.fintheircing.admin.systemdetect.service;

import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IDistributService interface
 *
 * @author yaoxiong
 * @date 2019/1/2
 */
@Transactional
public interface IDistributService {

    List<ProductModel> findForDayAllot();
    List<ProductModel> findForMonthAllot();
    List<ProductModel> findForSpecialAllot();

    ProductModel updateProduce(ProductModel model);

    List<ProductModel> getProduct(Integer producctNo);


}
