package cn.com.fintheircing.admin.usermanag.service.Impl;

import cn.com.fintheircing.admin.account.dao.repository.IAdminClientInfoRepository;
import cn.com.fintheircing.admin.account.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.constant.ResultCode;

import cn.com.fintheircing.admin.useritem.utils.DateUtils;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IBankMapper;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IUserMapper;


import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.BankCardModel;
import cn.com.fintheircing.admin.usermanag.service.IUserService;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    IUserMapper usermapper;
    @Resource
    IAdminClientInfoRepository adminClientInfoRepository;

    @Resource
    IBankMapper iBankMapper;


    @Override
    public List<AdminClientInfoModel> findAllUserInfo(AdminClientInfoModel Model
    ) throws UserServiceException {
        List<AdminClientInfo> users = null;
        try {
            List<AdminClientInfoModel> all = usermapper.findAll(Model);
            for (AdminClientInfoModel m :all
                    ) {
                if (StringUtils.isEmpty(m.getBossId())){
                    m.setBelongs("无");
                }else{
                    m.setBelongs("有");
                }
            }
            return all;

        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public boolean changeStatus(String id) throws UserServiceException {
        try {
            if (StringUtils.isEmpty(id)) {
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            } else {
                int i = usermapper.updateStatus(id);
                if (i > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public List<AdminClientInfoModel> findByOption(AdminClientInfoModel model) throws UserServiceException {

        try {
            List<AdminClientInfoModel> all = usermapper.findAllUseless(model);
            if (all == null || all.size() == 0) {
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            } else {

                return all;
            }
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public int changeProxyNum(String userId, String proxyId) throws UserServiceException {
        try {
            if (StringUtil.isEmpty(userId)) {
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            } else {
                Map<String, Object> pamrs = new HashMap<>();
                pamrs.put("userId", userId);
                pamrs.put("proxyId", proxyId);

                return usermapper.changeProxyNum(pamrs);
            }
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public boolean returnStatus(String id) throws UserServiceException {

        try {
            if (StringUtils.isEmpty(id)) {
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            } else {
                int i = usermapper.restoreStatus(id);
                if (i > 0) {
                    return true;
                } else {
                    return false;
                }

            }
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public List<BankCardModel> findAllBankCard(BankCardModel model) throws UserServiceException {
        try {

            List<BankCardModel> alluser = iBankMapper.findAllBankCard(model);
            if (alluser == null || alluser.size() == 0) {
                throw new UserServiceException(ResultCode.USER_EXITS);
            } else {
                return alluser;
            }

        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public boolean updatebankCard(String id) throws UserServiceException {
        if(iBankMapper.UpdateBankCard(id)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<AdminClientInfoModel> findAllDetails(String id) {
        List<AdminClientInfoModel> allDetails = usermapper.findAllDetails(id);
        allDetails.forEach(a -> {
            if (a.getAccountStatus().equalsIgnoreCase("0")) {
                a.setAccountStatus("正常");
            }
            if (a.getAccountStatus().equalsIgnoreCase("1")) {
                a.setAccountStatus("冻结");
            }
            if (StringUtils.isEmpty(a.getEmplooyeeId())) {
                a.setEmplooyeeId("无下级员工");
            }
        });
        return allDetails;
    }

    @Override
    public boolean changeAmount(String id, double amount) {
        Map<String, Object> parms = new HashMap<>();
        parms.put("userId", id);
        parms.put("amount", amount);
        if (usermapper.changeAmount(parms) > 0) {
            return true;
        }
        return false;
    }
}