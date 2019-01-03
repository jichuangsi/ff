package cn.com.fintheircing.customer.business.service.feign.impl;

import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.service.feign.IBusinessFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessFeignServiceImpl implements IBusinessFeignService {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public ProductModel getProductModel(ProductModel model) {
        logger.error("调用feign失败，未获取product");
        return null;
    }
}
