package cn.com.fintheircing.admin.systemdetect.service.Impl;

import cn.com.fintheircing.admin.systemdetect.common.Status;
import cn.com.fintheircing.admin.systemdetect.dao.ProductRepository;
import cn.com.fintheircing.admin.systemdetect.entity.Product;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import cn.com.fintheircing.admin.systemdetect.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.systemdetect.utils.MappingModel2EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DistributServiceImpl implements IDistributService {
    @Autowired
    private ProductRepository productRepository;

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
}
