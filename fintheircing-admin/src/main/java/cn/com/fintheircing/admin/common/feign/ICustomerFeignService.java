package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.usermanag.model.MesModel;
import cn.com.fintheircing.admin.usermanag.model.pay.PayConfigModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.common.feign.impl.CustomerFeignServiceFallBack;
import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.promise.PromiseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

     int deleteRecoding(String userId);

    /**
     * 获取第三方配置信息
     * @return
     */
    @RequestMapping("/pay/getPayConfig")
    PayConfigModel getPayConfig();

    /**
     * 获得待确认所有的 信息
     * @return
     */
    @RequestMapping("/pay/recodPayInfo")
    RecodeInfoPayModel recodPayInfo();


}
