package cn.com.fintheircing.admin.systemdetect.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Distributcontroller class
 *
 * @author 姚雄
 * @date 2019/1/2
 */
@RestController
@Api("系统管理的产品Distributcontroller")
@RequestMapping("/dis")
public class Distributcontroller {

    private IDistributService iDistributService;
    @ApiOperation(value = "产品列表-日配", notes = "")
    @ApiImplicitParams({
    })
    @GetMapping("/findDay")
    public ResponseModel<List<ProductModel>> findForDayAllot(){
        return ResponseModel.sucess("",iDistributService.findForDayAllot());
    }
    @ApiOperation(value = "产品列表-月配", notes = "")
    @ApiImplicitParams({
    })
    @GetMapping("/findmonth")

    public ResponseModel<List<ProductModel>> findForMonthAllot(){
        return ResponseModel.sucess("",iDistributService.findForMonthAllot());
    }
    @ApiOperation(value = "产品列表-特殊", notes = "")
    @ApiImplicitParams({
    })
    @GetMapping("/findspec")
    public ResponseModel<List<ProductModel>> findForSpecialAllot(){
        return ResponseModel.sucess("",iDistributService.findForSpecialAllot());
    }
    @ApiOperation(value = "产品列表-修改", notes = "")
    @ApiImplicitParams({
    })
    @GetMapping("/update")
    public ResponseModel<ProductModel> findForSpecialAllot(ProductModel model){
        return ResponseModel.sucess("",iDistributService.updateProduce(model));
    }

}
