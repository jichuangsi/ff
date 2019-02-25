package cn.com.fintheircing.admin.systemdetect.service.Impl;

import cn.com.fintheircing.admin.systemdetect.common.ProductStatus;
import cn.com.fintheircing.admin.systemdetect.dao.mapper.IProductMapper;
import cn.com.fintheircing.admin.systemdetect.dao.repository.ProductRepository;
import cn.com.fintheircing.admin.systemdetect.entity.Product;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import cn.com.fintheircing.admin.systemdetect.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.systemdetect.utils.MappingModel2EntityConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        List<ProductModel> allBySpec = productMapper.findAllByDay();
        for (ProductModel p :allBySpec
        ) {
            p.setAllotStr(ProductStatus.getName(p.getAllot()));
        }
        return allBySpec;
    }

    @Override
    public List<ProductModel> findForMonthAllot() {
        List<ProductModel> allBySpec = productMapper.findAllByMonth();
        for (ProductModel p :allBySpec
        ) {
            p.setAllotStr(ProductStatus.getName(p.getAllot()));
        }
        return allBySpec;

    }

    @Override
    public List<ProductModel> findForSpecialAllot() {
        List<ProductModel> allBySpec = productMapper.findAllBySpec();
        for (ProductModel p :allBySpec
             ) {
            p.setAllotStr(ProductStatus.getName(p.getAllot()));
        }
        return allBySpec;
    }

    @Override
    public ProductModel updateProduce(ProductModel model) {
        Product p =new Product();
        p.setId(model.getId());
        p.setAllot(model.getAllot());
        p.setEntryAmount(model.getEntryAmount());
        p.setFinancingTime(model.getFinancingTime());
        p.setLeverRate(Integer.parseInt(model.getLeverRate()));
        p.setLiquidation(model.getLiquidation());
        p.setMoneyInContact(model.getMoneyInContact());
        p.setOutAmount(model.getOutAmount());
        p.setWornLine(model.getWornLine());
        p.setMoneyInDeal(model.getMoneyInDeal());
        Product save = productRepository.save(p);
        if (StringUtils.isEmpty(save)){
            return new ProductModel();
        }
        return model;

    }

    //获取当前开启，选择的套餐
    @Override
    public List<ProductModel> getProducts(Integer productNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productNo", productNo);
        return productMapper.selectCurrentProduct(params);
        /*return MappingEntity2ModelConverter.coverProductList(productRepository.findProductsByAllotContains(productNo));*/
    }

    @Override
    public ProductModel getProduct(String productId) {
        return productMapper.selectProduct(productId);
    }

    @Override
    public String getProductName(String productId) {
        ProductModel model = productMapper.getProductNameByid(productId);
        if (null != model){
            return model.getProductName();
        }
        return null;
    }
}
