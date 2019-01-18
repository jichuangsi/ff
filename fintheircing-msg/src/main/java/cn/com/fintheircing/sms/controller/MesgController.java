package cn.com.fintheircing.sms.controller;

import cn.com.fintheircing.sms.model.MesModel;
import cn.com.fintheircing.sms.service.ISmsSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("短信相关的controller")
public class MesgController {
    @Resource
    ISmsSendService iSmsSendService;
    @ApiOperation(value = "管理员-短信记录", notes = "")
    @GetMapping("/findAllMessage")
    public List<MesModel> getMessage(){
        return iSmsSendService.findAllMessage();
    }
    @ApiOperation(value = "用户列表-短信记录", notes = "")
    @GetMapping("/findAllMesByUserId")
    public List<MesModel> findAllMesByUserId(@RequestParam("id")String id){
        return iSmsSendService.findAllMesByUserId(id);
    }
}
