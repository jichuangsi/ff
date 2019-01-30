package cn.com.fintheircing.admin.test;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.service.BusinessService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestComponent {

    @Resource
    private BusinessService businessService;


    public void testRollBack() throws BusinessException{
        businessService.testRollBack();
    }

}
