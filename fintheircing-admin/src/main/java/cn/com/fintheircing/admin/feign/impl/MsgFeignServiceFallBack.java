package cn.com.fintheircing.admin.feign.impl;

import cn.com.fintheircing.admin.feign.IMsgFeignService;
import cn.com.fintheircing.admin.usermanag.model.MesModel;

import java.util.ArrayList;
import java.util.List;

public class MsgFeignServiceFallBack  implements IMsgFeignService {
    @Override
    public List<MesModel> findAllMessage() {
        return new ArrayList<MesModel>();
    }
}
