package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.common.feign.impl.MsgFeignServiceFallBack;

import cn.com.fintheircing.admin.usermanag.model.ｍes.MesModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "ffmsg", fallback = MsgFeignServiceFallBack.class)
public interface IMsgFeignService {
    @RequestMapping("/findAllMessage")
    List<MesModel> findAllMessage();

    @RequestMapping("/findAllMesByUserId")
    List<MesModel> findAllMesByUserId(String id);

}
