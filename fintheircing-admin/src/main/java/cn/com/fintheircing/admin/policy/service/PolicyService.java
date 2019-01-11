package cn.com.fintheircing.admin.policy.service;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.policy.model.TranferContractModel;
import cn.com.fintheircing.admin.policy.model.TranferControlContractModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PolicyService {
    @Resource
    private IBusinessContractMapper businessContractMapper;

    public PageInfo<TranferContractModel> getPageContractInfo(TranferContractModel model){
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<TranferContractModel> contracts = businessContractMapper.selectPageContracts(model);
        PageInfo<TranferContractModel> pageInfo = new PageInfo<TranferContractModel>(contracts);
        return pageInfo;
    }

    public PageInfo<TranferControlContractModel> getPageContractControl(TranferControlContractModel model){
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<TranferControlContractModel> contracts = businessContractMapper.selectPageControl(model);
        PageInfo<TranferControlContractModel> pageInfo = new PageInfo<TranferControlContractModel>(contracts);
        return pageInfo;
    }
}
