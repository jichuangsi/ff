package cn.com.fintheircing.customer.user.service.Impl;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.utils.CommonUtil;
import cn.com.fintheircing.customer.user.dao.mapper.IContactMapper;
import cn.com.fintheircing.customer.user.dao.mapper.IUserClientInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserClientLoginInfoRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.entity.UserAccount;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;
import cn.com.fintheircing.customer.user.exception.AccountPayException;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.*;
import cn.com.fintheircing.customer.user.model.contact.contactModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.queryModel.Encode64;
import cn.com.fintheircing.customer.user.model.queryModel.PassWordModel;
import cn.com.fintheircing.customer.user.model.withdraw.WithdrawModel;
import cn.com.fintheircing.customer.user.service.RegisterService;
import cn.com.fintheircing.customer.user.service.UserService;
import cn.com.fintheircing.customer.user.utlis.Entity2Model;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserInfoRepository userInfoRepository;
    @Resource
    private IUserClientInfoMapper userClientInfoMapper;
    @Resource
    private IRecodInfoPayRepository iRecodInfoPayRepository;
    @Resource
    private IUserAccountRepository iUserAccountRepository;
    @Resource
    private RegisterService registerService;
    @Resource
    private IUserClientLoginInfoRepository userClientLoginInfoRepository;
    @Resource
    private IContactMapper iContactMapper;
    @Value("${custom.pay.url}")
    private String url;
    @Value("${custom.pay.reciveWay}")
    private String reciveWay;
    @Value("${custom.pay.weChatOrAilpay}")
    private String weChatOrAilpay;

    @Override
    public UserClientInfo findOneByUserName(String userName) {
        return userInfoRepository.findOneByUserName(userName);
    }

    @Override
    public String getSaleManId(String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        UserClientInfo userClientInfo = userClientInfoMapper.selectSaleMan(params);
        if (null != userClientInfo) {
            return userClientInfoMapper.selectSaleMan(params).getInviterId();
        }
        return "";
    }

    @Override
    public PayConfigModel payForNet(UserTokenInfo userInfo) {
        PayConfigModel model = new PayConfigModel();
        model.setPhone(userInfo.getPhone());
        model.setUserId(userInfo.getUuid());
        model.setUserName(userInfo.getUserName());
        model.setWay(url);
        return model;
    }

    @Override
    public UserInfoModel getUserInfo(UserTokenInfo userInfo) {
        UserInfoModel model = new UserInfoModel();
        model.setUserName(userInfo.getUserName());
        model.setUserId(userInfo.getUuid());
        model.setPhone(userInfo.getPhone());
        model.setAccount(iUserAccountRepository.findAccountByUserId(userInfo.getUuid()));
        UserClientLoginInfo oneByUuid = userClientLoginInfoRepository.findOneByAndClientInfoId(userInfo.getUuid());
        String photo = oneByUuid.getPhoto();
        model.setPhoto(photo);
        return model;
    }

    /**
     * 记录并且修改余额
     *
     * @param model
     * @return
     */
    @Override
    public boolean addOrUseMoney(RecodeInfoPayModel model) {
        if (model.getCostCount() >= iUserAccountRepository.findAccountByUserId(model.getUserId())) {
            return false;
        } else {
            UserAccount byId = iUserAccountRepository.findOneByUserId(model.getUserId());
            if (!StringUtils.isEmpty(byId)) {
                //扣款
                byId.setAccount(byId.getAccount() - model.getCostCount());
                iUserAccountRepository.save(byId);
                // 记录
                iRecodInfoPayRepository.save(Model2Entity.UpdateRecodeInfoPayModel(model));
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void updatePass(UserTokenInfo userInfo, PassWordModel model) throws LoginException {
        validatePass(userInfo, model);
        UserClientLoginInfo loginInfo = userClientLoginInfoRepository.findByDeleteFlagAndLoginNameAndPwd("0", userInfo.getUserName(), CommonUtil.toSha256(model.getOldPassWord()));
        loginInfo.setUpdatedTime(new Date());
        loginInfo.setUpdateUserId(userInfo.getUuid());
        loginInfo.setUpdateUserName(userInfo.getUserName());
        loginInfo.setPwd(CommonUtil.toSha256(model.getNewPassWord()));
        userClientLoginInfoRepository.save(loginInfo);

    }

    @Override
    public void validatePass(UserTokenInfo userInfo, PassWordModel model) throws LoginException {
        userInfo.setPwd(model.getOldPassWord());
        userInfo.setLoginName(userInfo.getUserName());
        UserTokenInfo user = registerService.getUserForLogin(userInfo);
        if (user == null) {
            throw new LoginException(ResultCode.LOGIN_USER_NOTEXIST);
        }
        if (UserClientInfo.STATUS_STOP.equals(user.getStatus())) {
            throw new LoginException(ResultCode.LOGIN_USER_STOP);
        }
    }

    @Override
    public void userCer(UserTokenInfo userInfo, UserCerModel model) throws LoginException {
        UserClientInfo userClientInfo = userInfoRepository.findByUuid(userInfo.getUuid());
        userClientInfo.setCer(UserClientInfo.CER_PASS);
        userClientInfo.setDisplayname(model.getRealName());
        userClientInfo.setIdCard(model.getIdCard());
        userClientInfo.setUpdatedTime(new Date());
        userClientInfo.setUpdateUserId(userInfo.getUuid());
        userClientInfo.setUpdateUserName(userInfo.getUserName());
        userInfoRepository.save(userClientInfo);
    }

    @Override
    public boolean setAvatar(Encode64 base64, String uuid) throws IOException {
        UserClientLoginInfo oneByAndClientInfoId = userClientLoginInfoRepository.findOneByAndClientInfoId(uuid);
        oneByAndClientInfoId.setPhoto(base64.getBase64());
        UserClientLoginInfo save = userClientLoginInfoRepository.save(oneByAndClientInfoId);
        if (StringUtils.isEmpty(save)) {
            return false;
        }
        return true;
    }

    @Override
    public UserTokenInfo getUserTokenInfo(String id) {
        return userClientInfoMapper.findByUuid(id);
    }

    @Override
    public RecodeInfoPayModel withdrawCash(String uuid, WithdrawModel model) throws AccountPayException {
        UserClientInfo byUuid = userInfoRepository.findByUuid(uuid);

        if (!model.getTxPassword().equalsIgnoreCase(byUuid.getTxPassword())) {
            throw new AccountPayException(ResultCode.PASSWORD_IS_MISTAKE);
        } else {
            if (iUserAccountRepository.findAccountByUserId(uuid) < model.getAmount()) {
                throw new AccountPayException(ResultCode.ACCOUNT_LESS_ERR);
            }
            RecodeInfoPay r = new RecodeInfoPay();
            r.setRemark(model.getRemark());
            r.setBusinessContractId(model.getBusinessContractId());
            r.setUserId(uuid);
            r.setCostCount(model.getAmount());
            r.setTaskId("2");
            r.setTaskType("提现申请");
            RecodeInfoPay save = iRecodInfoPayRepository.save(r);
            RecodeInfoPayModel model1 = Entity2Model.CoverRecodInfoPay(save);
            return model1;
        }
    }

    /**
     * 增加或者修改支付密码
     *
     * @param uuid
     * @param txPassword
     */
    @Override
    public boolean addOrChangePassword(String uuid, String txPassword) throws AccountPayException {
        if (StringUtils.isEmpty(uuid)) {
            throw new AccountPayException(ResultCode.ENTRUST_VALIDATE_ERR);
        }
        if (StringUtils.isEmpty(txPassword)) {
            throw new AccountPayException(ResultCode.PASSWORD_NOT_EMPTY);
        }
        Map<String, Object> parms = new HashMap<>();
        parms.put("uuid", uuid);
        parms.put("txPassword", txPassword);
        if (userClientInfoMapper.addOrChangePassword(parms) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否设置支付密码
     *
     * @param uuid
     * @return
     */
    @Override
    public boolean checkTxPassword(String uuid) {
        String txPassword = userInfoRepository.findByUuid(uuid).getTxPassword();
        if (StringUtils.isEmpty(txPassword)) {
            return false;
        }
        return true;
    }

    /**
     * 配置订单详情
     *
     * @param uuid
     * @return
     */
    @Override
    public List<contactModel> assignOrder(String uuid) throws LoginException{
        if (StringUtils.isEmpty(uuid)){
            throw new LoginException(ResultCode.PARAM_ERR_MSG);
        }
        return iContactMapper.QueryContractInfos(uuid);

    }

    /**
     * 账号管理
     *
     * @param uuid
     * @return
     */
    @Override
    public List<contactModel> accountManagement(String uuid) throws LoginException{
        List<contactModel> contactModels = iContactMapper.accountManagement(uuid);
        for (contactModel m:contactModels
             ) {
            m.setAllValue(m.getMarketValue()+m.getAvailableMoney());
        }
        return contactModels;
    }
}