package cn.com.fintheircing.admin.proxy.service;

import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import cn.com.fintheircing.admin.common.model.AdminLoginModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProxyService {

    public PageInfo<ProxyModel> getProxyList(AdminLoginModel adminLoginModel , ProxyModel proxyModel){
        PageHelper.startPage(proxyModel.getPageIndex(),proxyModel.getPageSize());
        List<ProxyModel> proxyModels = new ArrayList<>();
        PageInfo pageInfo = new PageInfo(proxyModels);
        return pageInfo;
    }

    public void saveProxy(AdminLoginModel adminLoginModel , ProxyModel proxyModel){

    }
}
