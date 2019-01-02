package cn.com.fintheircing.admin.usermanag.service.Impl;

import cn.com.fintheircing.admin.usermanag.model.OnlineUserInfo;
import cn.com.fintheircing.admin.usermanag.service.IOnlineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineServiceFallBack implements IOnlineService {
    @Override
    public List<OnlineUserInfo> findAllInfo() {
        return null;
    }

    @Override
    public boolean outLine(String id) {
        return false;
    }

    @Override
    public List<OnlineUserInfo> findAllRecoding(OnlineUserInfo model) {
        return null;
    }


    @Override
    public int deleteRecoding(String userId) {
        return 0;
    }
}
