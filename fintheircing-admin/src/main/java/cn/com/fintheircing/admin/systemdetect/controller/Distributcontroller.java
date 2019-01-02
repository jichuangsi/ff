package cn.com.fintheircing.admin.systemdetect.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Distributcontroller class
 *
 * @author keriezhang
 * @date 2019/1/2
 */
@RestController
@Api("系统管理的产品Distributcontroller")
@RequestMapping("/dis")
public class Distributcontroller {

    private IDistributService iDistributService;
    @RequestMapping("/findDay")
    public ResponseModel<List<ProductModel>> findForDayAllot(){
        return ResponseModel.sucess("",iDistributService.findForDayAllot());
    }

    @RequestMapping("/findmonth")
    public ResponseModel<List<ProductModel>> findForMonthAllot(){
        return ResponseModel.sucess("",iDistributService.findForMonthAllot());
    }

    @RequestMapping("/findspec")
    public ResponseModel<List<ProductModel>> findForSpecialAllot(){
        return ResponseModel.sucess("",iDistributService.findForSpecialAllot());
    }

    @RequestMapping("/update")
    public ResponseModel<ProductModel> findForSpecialAllot(ProductModel model){
        return ResponseModel.sucess("",iDistributService.updateProduce(model));
    }

}
