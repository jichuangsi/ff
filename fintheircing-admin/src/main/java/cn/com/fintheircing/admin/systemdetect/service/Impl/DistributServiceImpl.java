package cn.com.fintheircing.admin.systemdetect.service.Impl;

import cn.com.fintheircing.admin.systemdetect.common.Status;
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
import java.util.List;

@Service
public class DistributServiceImpl implements IDistributService {
    @Autowired
    private ProductRepository productRepository;
    @Resource
    private IProductMapper productMapper;

    @Override
    public List<ProductModel> findForDayAllot() {
        List<Product> all = productRepository.findAll();
        all.forEach(a -> {
            if (!Status.DAYS.getName().equals(a.getAllot())) {
                all.remove(a);
            }
        });
        return MappingEntity2ModelConverter.coverProductList(all);
    }

    @Override
    public List<ProductModel> findForMonthAllot() {
        List<Product> all = productRepository.findAll();
        all.forEach(a -> {
            if (!Status.MONTHS.getName().equals(a.getAllot())) {
                all.remove(a);
            }
        });
        return MappingEntity2ModelConverter.coverProductList(all);
    }

    @Override
    public List<ProductModel> findForSpecialAllot() {
        List<Product> all = productRepository.findAll();
        all.forEach(a -> {
            if (!Status.SPECIAL.getName().equals(a.getAllot())) {
                all.remove(a);
            }
        });
        return MappingEntity2ModelConverter.coverProductList(all);
    }

    @Override
    public ProductModel updateProduce(ProductModel model) {
       return MappingEntity2ModelConverter.coverProduct(
                productRepository.save(
                MappingModel2EntityConverter.coverProduct(model)) );

    }


    //获取当前开启，选择的套餐
    @Override
    public ProductModel getProduct(ProductModel model) {
        return productMapper.selectCurrentProduct(model);
    }
}
