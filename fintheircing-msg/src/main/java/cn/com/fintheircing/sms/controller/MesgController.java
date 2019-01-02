package cn.com.fintheircing.sms.controller;

import cn.com.fintheircing.sms.model.mesModel;
import cn.com.fintheircing.sms.service.ISmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MesgController {
    @Autowired
    ISmsSendService iSmsSendService;
    @RequestMapping("/findAllMessage")
    public List<mesModel> getMessage(){
        return iSmsSendService.findAllMessage();
    }
}
