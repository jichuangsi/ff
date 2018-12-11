package cn.com.fintheircing.sms.Controller;

import cn.com.fintheircing.sms.service.ISmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class app {
    @Autowired
    ISmsSendService iSmsSendService;
    @GetMapping("/user")
    public void test(String phoneNo,String code){
        iSmsSendService.sendValCodeSms(phoneNo,code);
    }
}
