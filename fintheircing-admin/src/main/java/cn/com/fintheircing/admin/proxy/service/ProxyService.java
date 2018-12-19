package cn.com.fintheircing.admin.proxy.service;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.entity.Position;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.login.entity.AdminClientLoginInfo;
import cn.com.fintheircing.admin.proxy.dao.mapper.IAdminClientInfoMapper;
import cn.com.fintheircing.admin.proxy.dao.mapper.ICommissionMapper;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientInfoRepository;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientLoginInfoRepository;
import cn.com.fintheircing.admin.proxy.dao.repository.ICommissionRepository;
import cn.com.fintheircing.admin.proxy.entity.Commission;
import cn.com.fintheircing.admin.proxy.exception.ProxyException;
import cn.com.fintheircing.admin.proxy.model.EmployeeModel;
import cn.com.fintheircing.admin.proxy.model.IdModel;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import cn.com.fintheircing.admin.proxy.utils.MappingModel2EntityConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProxyService {

    @Value("${custom.admin.pwd}")
    private String pwd;


    @Resource
    private IAdminClientInfoRepository adminClientInfoRepository;
    @Resource
    private IAdminClientLoginInfoRepository adminClientLoginInfoRepository;
    @Resource
    private ICommissionRepository commissionRepository;
    @Resource
    private ICommissionMapper commissionMapper;
    @Resource
    private IAdminClientInfoMapper adminClientInfoMapper;


    public PageInfo<ProxyModel> getProxyList(UserTokenInfo adminLoginModel , ProxyModel proxyModel){
        PageHelper.startPage(proxyModel.getPageIndex(),proxyModel.getPageSize());
        List<ProxyModel> proxyModels = new ArrayList<>();
        PageInfo pageInfo = new PageInfo(proxyModels);
        return pageInfo;
    }

    @Transactional(rollbackFor=Exception.class)
    public void saveProxy(UserTokenInfo adminLoginModel , ProxyModel proxyModel) throws ProxyException{
        AdminClientInfo info = MappingModel2EntityConverter.CONVERTERFORPROXYMODEL(proxyModel);
        info.setRole(AdminClientInfo.ROLE_ADMIN);
        info.setBossId(adminLoginModel.getUuid());
        if(proxyModel.getProxyPosition()==null) {
            if (adminLoginModel.getPosition() < Position.POSITION_PROXY_TWO) {
                info.setPosition(adminLoginModel.getPosition() + 1);
            } else {
                throw new ProxyException(ResultCode.POWER_VISIT_ERR);
            }
        } else{
            info.setPosition(proxyModel.getProxyPosition());
        }
        info.setStatus(AdminClientInfo.STATUS_EXIST);
        info = adminClientInfoRepository.save(info);
        AdminClientLoginInfo loginInfo = new AdminClientLoginInfo();
        loginInfo.setAdminClientId(info.getUuid());
        loginInfo.setLoginName(info.getUserName());
        loginInfo.setPwd(pwd);
        adminClientLoginInfoRepository.save(loginInfo);
        if(StringUtils.isEmpty(proxyModel.getMonthCommission())||StringUtils.isEmpty(proxyModel.getDayCommission())
                ||StringUtils.isEmpty(proxyModel.getBackCommission())){
            throw new ProxyException(ResultCode.COMMISSION_NULL_ERR);
        }
        Commission commission = new Commission();
        commission.setBackCommission(proxyModel.getBackCommission());
        commission.setDayCommission(proxyModel.getDayCommission());
        commission.setMonthCommission(proxyModel.getMonthCommission());
        commission.setSalemanId(info.getUuid());
        commissionRepository.save(commission);
    }

    public ProxyModel getCommissions(IdModel ids){
        ProxyModel proxyModel = new ProxyModel();
        Commission commission = commissionRepository.findCommissionBySalemanId(ids.getIds().get(0));

        proxyModel.setBackCommission(commission.getBackCommission());
        proxyModel.setDayCommission(commission.getDayCommission());
        proxyModel.setMonthCommission(commission.getMonthCommission());
        proxyModel.setProxyId(commission.getSalemanId());
        return proxyModel;

    }

    public void updateCommission(ProxyModel model) throws ProxyException{
       if( !(commissionMapper.updateCommission(model)>0)){
           throw new ProxyException(ResultCode.UPDATE_ERR);
       }
    }

    public PageInfo<EmployeeModel> getEmployee(ProxyModel model){
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<EmployeeModel> employeeModels = adminClientInfoMapper.selectEmp(model);
        PageInfo<EmployeeModel> page = new PageInfo<EmployeeModel>(employeeModels);
        return page;
    }
}
