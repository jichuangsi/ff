package cn.com.fintheircing.admin.UserManag.service;

import cn.com.fintheircing.admin.UserManag.model.mesModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "fintheircing-msg")
public interface ImesgService {
    @RequestMapping("/findAllMessage")
    public List<mesModel> findAllMessage();
}
