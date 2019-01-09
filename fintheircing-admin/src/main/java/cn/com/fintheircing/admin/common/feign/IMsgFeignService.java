package cn.com.fintheircing.admin.common.feign;


import cn.com.fintheircing.admin.common.feign.impl.MsgFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ffmsg",fallback = MsgFeignServiceFallBack.class)
public interface IMsgFeignService {
    /*@RequestMapping("/findAllMessage")
    List<MesModel> findAllMessage();*/
}
