package cn.com.fintheircing.admin.business.service;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.systemdetect.common.Status;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessService {
    @Resource
    private IBusinessContractMapper businessContractMapper;

    public Boolean canBuy(Integer productNo,String userId) {
        Map<String,Object> params = new HashMap<String,Object>();
        String productName = "";
        if (Status.SPECIAL.getIndex()!=productNo){
            productName = Status.getName(productNo);
        }
        params.put("productName", productName);
        params.put("userId",userId);
        return !(businessContractMapper.countSameContract(params)>0);
    }
}
