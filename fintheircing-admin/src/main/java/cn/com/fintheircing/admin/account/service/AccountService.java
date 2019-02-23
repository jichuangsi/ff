package cn.com.fintheircing.admin.account.service;

import cn.com.fintheircing.admin.account.dao.repository.IAdminClientInfoRepository;
import cn.com.fintheircing.admin.account.entity.AdminClientInfo;
import cn.com.fintheircing.admin.account.model.AdminModel;
import cn.com.fintheircing.admin.account.model.PassWordModel;
import cn.com.fintheircing.admin.account.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.login.dao.repository.IAdminClientLoginInfoRepository;
import cn.com.fintheircing.admin.login.entity.AdminClientLoginInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;

@Service
public class AccountService {

    @Resource
    private IAdminClientInfoRepository adminClientInfoRepository;
    @Resource
    private IAdminClientLoginInfoRepository adminClientLoginInfoRepository;

    //获取个人信息
    public AdminModel getSelfData(UserTokenInfo userInfo){
        AdminClientInfo clientInfo = adminClientInfoRepository.findOneByUuid(userInfo.getUuid());
        return MappingEntity2ModelConverter.CONVERTERFORMADMINCLIENTINFO(clientInfo);
    }

    //修改个人信息
    public void updateSelfData(UserTokenInfo userInfo,AdminModel model){
        AdminClientInfo clientInfo = adminClientInfoRepository.findOneByUuid(userInfo.getUuid());
        if (!StringUtils.isEmpty(model.getBornDay())){
            clientInfo.setBornDay(model.getBornDay());
        }
        if (!StringUtils.isEmpty(model.getUserName())){
            clientInfo.setUserName(model.getUserName());
        }
        if (!StringUtils.isEmpty(model.getSex())){
            clientInfo.setSex(model.getSex());
        }
        if (!StringUtils.isEmpty(model.getPhone())){
            clientInfo.setPhone(model.getPhone());
        }
        if (!StringUtils.isEmpty(model.getEmail())){
            clientInfo.setEmail(model.getEmail());
        }
        clientInfo.setUpdateUserName(userInfo.getUserName());
        clientInfo.setUpdateUserId(userInfo.getUuid());
        adminClientInfoRepository.save(clientInfo);
    }

    //修改密码
    public void updatePass(UserTokenInfo userInfo, PassWordModel model) throws LoginException{
        AdminClientLoginInfo clientLoginInfo = adminClientLoginInfoRepository.findByDeleteFlagAndLoginNameAndPwd("0",userInfo.getUserName(),changePwd(model.getOldPassWord()));
        if (null == clientLoginInfo){
            throw new LoginException(ResultCode.LOGIN_VALIDATE_ERR);
        }
        clientLoginInfo.setPwd(changePwd(model.getNewPassWord()));
        clientLoginInfo.setUpdateUserId(userInfo.getUuid());
        clientLoginInfo.setUpdateUserName(userInfo.getUserName());
        adminClientLoginInfoRepository.save(clientLoginInfo);
    }

    //密码加密
    private String changePwd(String pwd) {
        return CommonUtil.toSha256(pwd);
    }
}
