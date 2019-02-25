package cn.com.fintheircing.admin.systemdetect.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.systemdetect.model.ProductModel;
import cn.com.fintheircing.admin.systemdetect.service.IDistributService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@CrossOrigin
public class Distributcontroller {
    @Resource
    private IDistributService iDistributService;
    @ApiOperation(value = "产品列表-日配", notes = "")
    @ApiImplicitParams({
    })
    @GetMapping("/findDay")
    public ResponseModel<PageInfo<ProductModel>> findForDayAllot(int pageNum ,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ProductModel> allUserInfo =iDistributService.findForDayAllot();
        PageInfo<ProductModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "产品列表-月配", notes = "")
    @ApiImplicitParams({
    })
    @GetMapping("/findmonth")
    public ResponseModel<PageInfo<ProductModel>> findForMonthAllot(int pageNum ,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ProductModel> allUserInfo =iDistributService.findForMonthAllot();
        PageInfo<ProductModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "产品列表-特殊", notes = "")
    @ApiImplicitParams({
    })
    @GetMapping("/findspec")
    public ResponseModel<PageInfo<ProductModel>> findForSpecialAllot(int pageNum ,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ProductModel> allUserInfo =iDistributService.findForSpecialAllot();
        PageInfo<ProductModel> personPageInfo = new PageInfo<>(allUserInfo);
        return ResponseModel.sucess("",personPageInfo);
    }
    @ApiOperation(value = "产品列表-修改", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/update")
    public ResponseModel<ProductModel> findForSpecialAllot(@Validated @RequestBody ProductModel model){
        return ResponseModel.sucess("",iDistributService.updateProduce(model));
    }

}
