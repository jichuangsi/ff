package cn.com.fintheircing.admin.feign;

import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.feign.impl.CustomerFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "ffcostomer",fallback = CustomerFeignServiceFallBack.class)
public interface ICustomerFeignService {
    @RequestMapping("/findAllOnline")
    List<OnlineUserInfo>  findAllInfo();
    @RequestMapping("/findAllUser")
    public List<UserTokenInfo> findAllUser();
    @RequestMapping("/outLine")
    public boolean outLine(String id);
    @RequestMapping("/findAllRecoding")
    public List<OnlineUserInfo> findAllRecoding(OnlineUserInfo userInfo);
    @RequestMapping("/deleteRecoding")

    public int deleteRecoding(String userId);
}
