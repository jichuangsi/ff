package cn.com.fintheircing.admin.UserManag.service;

import cn.com.fintheircing.admin.UserManag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "fintheircing-customer")
public interface OnlineService {
    @RequestMapping("/findAllOnline")
    List<OnlineUserInfo>  findAllInfo();
    @RequestMapping("/findAllUser")
    public List<UserTokenInfo> findAllUser();
    @RequestMapping("/outLine")
    public boolean outLine(String id);
    @RequestMapping("/findAllRecoding")
    public List<OnlineUserInfo> findAllRecoding(String operating, String loginName);
    @RequestMapping("/deleteRecoding")

    public int deleteRecoding(String userId);
}
