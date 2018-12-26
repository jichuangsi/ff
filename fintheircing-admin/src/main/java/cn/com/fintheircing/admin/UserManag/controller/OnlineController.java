package cn.com.fintheircing.admin.UserManag.controller;

import cn.com.fintheircing.admin.UserManag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.UserManag.service.Impl.IOnlineService;
import cn.com.fintheircing.admin.UserManag.service.OnlineService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
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
    OnlineService onlineService;
    @Resource
    IOnlineService iOnlineService;
    @GetMapping("/findAllInfo")
    @ApiOperation(value = "系统监控--在线人数", notes = "")
    public ResponseModel<PageInfo<OnlineUserInfo>> findAllInfo(int pageNum, int pageSize,String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<OnlineUserInfo> allInfo = onlineService.findAllInfo();
        List<OnlineUserInfo> allByName = iOnlineService.findAllByName(allInfo, userName);
        PageInfo<OnlineUserInfo> personPageInfo = new PageInfo<>(allByName);
        return ResponseModel.sucess("",personPageInfo );
    }

    @GetMapping("/deleteInfo")
    @ApiOperation(value = "系统监控-系统监控-删除记录", notes = "")
    public ResponseModel deleteInfo(String id) {
       return ResponseModel.sucess("",onlineService.deleteRecoding(id));
    }
    @GetMapping("/deleteInfo")
    @ApiOperation(value = "系统监控-在线人数-强制登出", notes = "")
    public ResponseModel outLine(String id) {
       return ResponseModel.sucess("",onlineService.outLine(id));
    }

    @GetMapping("/deleteInfo")
    @ApiOperation(value = "系统监控-系统监控-查询记录", notes = "")
    public ResponseModel<PageInfo<OnlineUserInfo>> findAllRecoding(String op,String name,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OnlineUserInfo> allRecoding = onlineService.findAllRecoding(op, name);
        PageInfo<OnlineUserInfo> personPageInfo = new PageInfo<>(allRecoding);
        return ResponseModel.sucess("",personPageInfo);
    }
}
