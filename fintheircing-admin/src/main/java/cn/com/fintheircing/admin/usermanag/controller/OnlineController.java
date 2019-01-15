package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.usermanag.service.Impl.OnlineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("OnlineController相关控制层")
public class OnlineController {
    @Resource
    private ICustomerFeignService customerFeignService;
    @Resource
    private OnlineService onlineService;
    @GetMapping("/findAllInfo")
    @ApiOperation(value = "系统监控--在线人数", notes = "")
    public ResponseModel<PageInfo<OnlineUserInfo>> findAllInfo(int pageNum, int pageSize,String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<OnlineUserInfo> allInfo = customerFeignService.findAllInfo();
        List<OnlineUserInfo> allByName = onlineService.findAllByName(allInfo, userName);
        PageInfo<OnlineUserInfo> personPageInfo = new PageInfo<>(allByName);
        return ResponseModel.sucess("",personPageInfo );
    }

    @GetMapping("/deleteInfo")
    @ApiOperation(value = "系统监控-系统监控-删除记录", notes = "")
    public ResponseModel deleteInfo(String id) {
       return ResponseModel.sucess("",customerFeignService.deleteRecoding(id));
    }
    @GetMapping("/outLine")
    @ApiOperation(value = "系统监控-在线人数-强制登出", notes = "")
    public ResponseModel outLine(String id) {
       return ResponseModel.sucess("",customerFeignService.outLine(id));
    }

    @GetMapping("/findAllRecoding")
    @ApiOperation(value = "系统监控-系统监控-查询记录", notes = "")
    public ResponseModel<PageInfo<OnlineUserInfo>> findAllRecoding(OnlineUserInfo userInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OnlineUserInfo> allRecoding = customerFeignService.findAllRecoding(userInfo);
        PageInfo<OnlineUserInfo> personPageInfo = new PageInfo<>(allRecoding);
        return ResponseModel.sucess("",personPageInfo);
    }
}
