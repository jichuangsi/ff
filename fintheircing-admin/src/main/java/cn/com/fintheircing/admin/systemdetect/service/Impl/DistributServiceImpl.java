package cn.com.fintheircing.admin.systemdetect.service.Impl;

import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import cn.com.fintheircing.admin.systemdetect.dao.mapper.IProductMapper;
import cn.com.fintheircing.admin.systemdetect.dao.repository.ProductRepository;
import cn.com.fintheircing.admin.systemdetect.entity.Product;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import cn.com.fintheircing.admin.systemdetect.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.systemdetect.utils.MappingModel2EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistributServiceImpl implements IDistributService {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private IProductMapper productMapper;


    @Override
    public List<ProductModel> findForDayAllot() {
        return productMapper.findAllByDay();
    }

    @Override
    public List<ProductModel> findForMonthAllot() {

        return productMapper.findAllByMonth();
    }

    @Override
    public List<ProductModel> findForSpecialAllot() {
        return productMapper.findAllBySpec();
    }

    @Override
    public ProductModel updateProduce(ProductModel model) {
       return MappingEntity2ModelConverter.coverProduct(
                productRepository.save(
                MappingModel2EntityConverter.coverProduct(model)) );

    }


    //获取当前开启，选择的套餐
    @Override
    public List<ProductModel> getProducts(Integer productNo) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("productNo",productNo);
        return productMapper.selectCurrentProduct(params);
        /*return MappingEntity2ModelConverter.coverProductList(productRepository.findProductsByAllotContains(productNo));*/
    }

    @Override
    public ProductModel getProduct(String productId) {
        return productMapper.selectProduct(productId);
    }
}
