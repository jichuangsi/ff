package cn.com.fintheircing.admin.common.feign;

import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.common.feign.impl.CustomerFeignServiceFallBack;
import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.PayConfigModel;
import cn.com.fintheircing.admin.usermanag.model.pay.RecodeInfoPayModel;
import cn.com.fintheircing.admin.usermanag.model.ｍes.MesInfoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "ffcostomer", fallback = CustomerFeignServiceFallBack.class)
public interface ICustomerFeignService {
    @RequestMapping("/findAllOnline")
    List<OnlineUserInfo> findAllInfo();

    @RequestMapping("/findAllUser")
    public List<UserTokenInfo> findAllUser();

    /**
     * 强制登出
     *
     * @param id
     * @return
     */
    @RequestMapping("/isOut")
    public boolean outLine(String id);

    @RequestMapping("/viewOnline")
    public List<OnlineUserInfo> findAllRecoding();

    @RequestMapping("/deleteRecoding")
    int deleteRecoding(String userId);

    /**
     * 获取第三方配置信息
     *
     * @return
     */
    @RequestMapping("/pay/getPayConfig")
    PayConfigModel getPayConfig();

    /**
     * 获得待确认所有的 信息
     *
     * @return
     */
    @RequestMapping("/pay/recodPayInfo")
    RecodeInfoPayModel recodPayInfo();

    @RequestMapping("/rudeEndContract")
    ResponseModel<String> rudeEndContract(@RequestBody ContractModel model);


    @RequestMapping(value = "/updateMesInfo")
    public ResponseModel sendMesg(@RequestBody MesInfoModel model);
}
