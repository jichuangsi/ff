package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.feign.ICustomerFeignService;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IRecodeInfoMapper;
import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.usermanag.service.Impl.OnlineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("OnlineController相关控制层")
@CrossOrigin
public class OnlineController {
    @Resource
    private ICustomerFeignService customerFeignService;
    @Resource
    private OnlineService onlineService;
    @Resource
    private IRecodeInfoMapper iRecodeInfoMapper;
    @GetMapping("/findAllInfo")
    @ApiOperation(value = "系统监控--在线人数", notes = "")
    public ResponseModel<PageInfo<OnlineUserInfo>> findAllInfo(@RequestParam(value = "pageNum") int pageNum,
                                                               @RequestParam (value = "pageSize")int pageSize,
                                                               @Param(value = "userName") String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<OnlineUserInfo> allInfo = customerFeignService.findAllRecoding();
        List<OnlineUserInfo> allByName = onlineService.findAllByName(allInfo, userName);
        PageInfo<OnlineUserInfo> personPageInfo = new PageInfo<>(allByName);
        return ResponseModel.sucess("",personPageInfo );
    }

    @GetMapping("/deleteInfo")
    @ApiOperation(value = "系统监控-系统监控-删除记录", notes = "")
    public ResponseModel deleteInfo(String recodeInfoId) {
       return ResponseModel.sucess("",iRecodeInfoMapper.deleteRecoding(recodeInfoId));
    }
    @GetMapping("/outLine")
    @ApiOperation(value = "系统监控-在线人数-强制登出", notes = "")
    public ResponseModel outLine(String id) {
       return ResponseModel.sucess("",customerFeignService.outLine(id));
    }

    @GetMapping("/viewOnline")
    @ApiOperation(value = "系统监控-系统监控-查询记录", notes = "")
    public ResponseModel<PageInfo<OnlineUserInfo>> findAllRecoding(int pageNum, int pageSize,String opeart,String userName) {
        Map<String,Object> parms =new HashMap<>();
        parms.put("opeart", opeart);
        parms.put("userName", userName);
        PageHelper.startPage(pageNum,pageSize);
        List<OnlineUserInfo> allRecode = iRecodeInfoMapper.findAllRecode(parms);
        PageInfo<OnlineUserInfo> personPageInfo = new PageInfo<>(allRecode);
        return ResponseModel.sucess("",personPageInfo);
    }
}
