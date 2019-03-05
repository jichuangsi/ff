package cn.com.fintheircing.customer.common.initConfig;

import cn.com.fintheircing.customer.common.constant.RoleCodes;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.utils.CommonUtil;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserClientLoginInfoRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserAccount;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Component
public class initComponent {

    @Resource
    private IAdminFeignService adminFeignService;
    @Resource
    private IUserInfoRepository userInfoRepository;
    @Resource
    private IUserClientLoginInfoRepository userClientLoginInfoRepository;
    @Resource
    private IUserAccountRepository userAccountRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());
   /* @PostConstruct
    public void getRole(){
        List<RoleModel> roles = todoTaskService.getRoles();
        for (RoleModel role :roles){
            RoleCodes.ROLE_KEY_INTEGER.put(role.getPosition(),role.getSign());
            RoleCodes.ROLE_KEY_STRING.put(role.getSign(),role.getPosition());
        }
    }*/

    @PostConstruct
    public void addTestUser() {
        try {
            String phoneNo = "13922778953";
            String pwd = "q13922778953";
            if (null == userInfoRepository.findOneByUserName(phoneNo)){
                UserClientInfo userClientInfo = new UserClientInfo();
                userClientInfo.setUserName(phoneNo);//手机号做用户名
                userClientInfo.setDisplayname(phoneNo);
                userClientInfo.setPhone(phoneNo);
                userClientInfo.setStatus(UserClientInfo.STATUS_INIT);
                userClientInfo.setCer(UserClientInfo.CER_NOT);
                userClientInfo.setRoleGrade(RoleCodes.ROLE_KEY_STRING.get("U"));//添加区别用户管理员
                userClientInfo.setCreatedTime(new Date());
                userClientInfo.setUpdatedTime(new Date());

                UserTokenInfo userInfo = new UserTokenInfo();
                userInfo.setUuid(userClientInfo.getUuid());
                userInfo.setRoleGrade(userClientInfo.getRoleGrade());

                userClientInfo = userInfoRepository.save(userClientInfo);

                //新增登录信息
                UserClientLoginInfo userClientLoginInfo = new UserClientLoginInfo();
                userClientLoginInfo.setLoginName(phoneNo);//手机号做登录名
                //对登录密码做SHA256处理
                userClientLoginInfo.setPwd(CommonUtil.toSha256(pwd));
                userClientLoginInfo.setClientInfoId(userClientInfo.getUuid());//登录信息和用户信息关联
                userClientLoginInfo.setCreatedTime(new Date());
                userClientLoginInfo.setUpdatedTime(new Date());
                userClientLoginInfoRepository.save(userClientLoginInfo);

                UserAccount userAccount = new UserAccount();
                userAccount.setUserId(userClientInfo.getUuid());
                userAccount.setAccount(10000.0);
                userAccount.setCreatedTime(new Date());
                userAccount.setUpdatedTime(new Date());
                userAccountRepository.save(userAccount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
