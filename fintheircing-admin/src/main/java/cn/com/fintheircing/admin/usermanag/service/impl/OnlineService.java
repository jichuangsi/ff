package cn.com.fintheircing.admin.usermanag.service.impl;


import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class OnlineService {

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
