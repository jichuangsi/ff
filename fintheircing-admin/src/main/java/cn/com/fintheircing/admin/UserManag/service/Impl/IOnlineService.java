package cn.com.fintheircing.admin.UserManag.service.Impl;

import cn.com.fintheircing.admin.UserManag.model.OnlineUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class IOnlineService {

    public List<OnlineUserInfo> findAllByName(List<OnlineUserInfo> allInfo, String userName) {

        if (StringUtils.isEmpty(userName)) {
            return allInfo;
        } else {
            allInfo.forEach(user -> {
                if (!user.getUserName().contains(userName)) {
                    allInfo.remove(user);
                }
            });
            return allInfo;
        }
    }
}
