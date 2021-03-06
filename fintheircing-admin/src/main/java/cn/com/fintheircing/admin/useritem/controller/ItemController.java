package cn.com.fintheircing.admin.useritem.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.login.exception.AdminLoginException;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ItemController class
 *
 * @author yaoxiong
 * @date 2018/12/27
 */
@RestController
@RequestMapping("/item")
@Api("ItemController白名单列表的控制层")
public class ItemController {
    @Resource
    private ItemService itemService;
    @ApiOperation(value = "白名单列表-绝对白名单", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/findWhite")
    public ResponseModel<PageInfo<TransactionModel>> findAllByWhite(@Validated @RequestBody TransactionModel model ,int pageNum,int pageSize) throws AdminLoginException {
        PageHelper.startPage(pageNum,pageSize);
        List<TransactionModel> allInfo = itemService.findAllByWhite(model);;
        PageInfo<TransactionModel> personPageInfo = new PageInfo<>(allInfo);
        return ResponseModel.sucess("",personPageInfo );
    }
    @ApiOperation(value = "白名单列表-交易总表", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/findAll")
    public ResponseModel<PageInfo<TransactionModel>> findAll(@Validated @RequestBody TransactionModel model ,int pageNum,int pageSize) throws AdminLoginException {
        PageHelper.startPage(pageNum,pageSize);
        List<TransactionModel> allInfo = itemService.findAll(model);;
        PageInfo<TransactionModel> personPageInfo = new PageInfo<>(allInfo);
        return ResponseModel.sucess("",personPageInfo );

    }
    @ApiOperation(value = "白名单列表-静态黑名单", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/findblack")
    public ResponseModel<PageInfo<TransactionModel>> findAllByBlack(@Validated @RequestBody TransactionModel model ,int pageNum,int pageSize) throws AdminLoginException {
        PageHelper.startPage(pageNum,pageSize);
        List<TransactionModel> allInfo = itemService.findAllByBlack(model);;
        PageInfo<TransactionModel> personPageInfo = new PageInfo<>(allInfo);
        return ResponseModel.sucess("",personPageInfo );

    }
    @ApiOperation(value = "白名单列表-静态黑名单-保存", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/saveblack")
    public ResponseModel saveByStaticList(@Validated @RequestBody TransactionModel model )  {
        TransactionModel allInfo = itemService.saveByStaticList(model);;
        return ResponseModel.sucess("",allInfo );

    }
    @ApiOperation(value = "白名单列表-绝对白名单-保存", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/saveWhite")
    public ResponseModel saveTransactionSummary(@Validated @RequestBody TransactionModel model ) throws AdminLoginException {
        TransactionModel allInfo = itemService.saveTransactionSummary(model);;
        return ResponseModel.sucess("",allInfo);
    }

    @ApiOperation(value = "白名单列表-更新字段", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/updateRemark")
    public ResponseModel updateRemark(@Validated @RequestBody TransactionModel model ) throws AdminLoginException {
        int i = itemService.updateRemark(model);
        return ResponseModel.sucess("",null);
    }
    @ApiOperation(value = "白名单列表-删除信息", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/deleteRemark")
    public ResponseModel deleteRemark(String[] ids ) throws AdminLoginException {
        int i = itemService.deleteTransactionSummary(ids);
        return ResponseModel.sucess("",i);
    }
    @ApiOperation(value = "白名单列表-动态黑名单", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/findBlackList")
    public ResponseModel<PageInfo<TransactionModel>> findAllBlackList(@Validated @RequestBody TransactionModel model ,int pageNum,int pageSize) throws AdminLoginException {
        PageHelper.startPage(pageNum,pageSize);
        List<TransactionModel> allInfo = itemService.findAllBlackList(model);;
        PageInfo<TransactionModel> personPageInfo = new PageInfo<>(allInfo);
        return ResponseModel.sucess("",personPageInfo );

    }
    @ApiOperation(value = "白名单列表-白名单", notes = "")
    @ApiImplicitParams({
    })
    @PostMapping("/findWhiteList")
    public ResponseModel<PageInfo<TransactionModel>> findAllWhiteList(@Validated @RequestBody TransactionModel model ,int pageNum,int pageSize) throws AdminLoginException {
       // PageHelper.startPage(pageNum,pageSize);
        List<TransactionModel> allInfo = itemService.findAllWhiteList(model);;
        PageInfo<TransactionModel> personPageInfo = new PageInfo<>(allInfo);
        /*System.out.println();*/
        return ResponseModel.sucess("",personPageInfo );

    }
}
