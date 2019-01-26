package cn.com.fintheircing.admin.common.feign.impl;

import cn.com.fintheircing.admin.common.feign.IMsgFeignService;
import cn.com.fintheircing.admin.usermanag.model.ｍes.MesModel;

import java.util.ArrayList;
import java.util.List;

public class MsgFeignServiceFallBack  implements IMsgFeignService {
    @Override
    public List<MesModel> findAllMessage() {
        return new ArrayList<MesModel>();
    }

    @Override
    public List<MesModel> findAllMesByUserId(String id) {
        return new ArrayList<MesModel>();
    }
}
