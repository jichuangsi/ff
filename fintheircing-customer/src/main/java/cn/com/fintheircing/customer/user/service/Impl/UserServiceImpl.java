package cn.com.fintheircing.customer.user.service.Impl;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.utils.CommonUtil;
import cn.com.fintheircing.customer.user.dao.mapper.IContactMapper;
import cn.com.fintheircing.customer.user.dao.mapper.IUserClientInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.*;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.entity.UserAccount;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;
import cn.com.fintheircing.customer.user.exception.AccountPayException;
import cn.com.fintheircing.customer.user.exception.LoginException;
import cn.com.fintheircing.customer.user.model.*;
import cn.com.fintheircing.customer.user.model.contract.contactModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.PayInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.withdraw.WithdrawModel;
import cn.com.fintheircing.customer.user.service.RegisterService;
import cn.com.fintheircing.customer.user.service.UserService;
import cn.com.fintheircing.customer.user.utlis.Entity2Model;
import cn.com.fintheircing.customer.user.utlis.MappingEntity2ModelConverter;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private IRechargeRecodeRepository iRechargeRecodeRepository;
    @Resource
    private IContactMapper iContactMapper;
    @Value("${custom.pay.method}")
    private String method;
    @Value("${custom.pay.netQuery}")
    private String netQuery;
    @Value("${custom.pay.appPayPublic}")
    private String appPayPublic;
    private String valsmsPre = "backPass_val_";
    private long valZSendInterSeconds = 60;// 通知发送时间间隔
    private int valCodeLength = 4;
    private String notifyType = "valCode";// 短信类型
    @Value("${custom.mq.producer.queue-name.sendSms}")
    private String smsMqDestName;

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
     * 生成待确认充值记录
     */
    @Override
    public PayInfoModel recodPayInfo() {
        return null;
    }

    @Override
    public String getPassBackCode(String phoneNo) throws LoginException {
        synchronized (phoneNo) {
            // 使用手机号作为用户名
            if (null == userInfoRepository.findOneByUserName(phoneNo)) {
                throw new LoginException(phoneNo + "不存在");
            }
            // 看缓存中是否存在发送验证码记录
            if (null != redisTemplate.opsForValue().get(valsmsPre + phoneNo)) {
                throw new LoginException(valZSendInterSeconds + "秒内只能发送一次");
            }
            String code = getRandomNumCode(valCodeLength);
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("phoneNo", phoneNo);
            dataMap.put("type", notifyType);
            dataMap.put("content", code);
            // 发送MQ消息
            rabbitTemplate.convertAndSend(smsMqDestName, JSONObject.toJSONString(dataMap));
            // 记录短信发送记录在缓存
            redisTemplate.opsForValue().set(valsmsPre + phoneNo, code, valZSendInterSeconds, TimeUnit.SECONDS);
            return code;
        }
    }

    // 生成随机数字
    private String getRandomNumCode(int number) {
        String codeNum = "";
        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int next = random.nextInt(10000);
            codeNum += numbers[next % 10];
        }
        return codeNum;
    }

    @Override
    public void updateNewPass(String code, String pass, String phoneNo) throws LoginException {
        UserClientInfo clientInfo = userInfoRepository.findOneByUserName(phoneNo);
        if (null == clientInfo) {
            throw new LoginException(phoneNo + "不存在");
        }
        // 取缓存中是否存在发送验证码记录
        String valCodeInCache = redisTemplate.opsForValue().get(valsmsPre + phoneNo);
        // 对比验证码
        if (StringUtils.isEmpty(valCodeInCache)) {
            throw new LoginException("验证码不存在或已过期");
        }
        if (!valCodeInCache.equals(code)) {
            throw new LoginException("验证码不正确");
        }
        //删除验证码缓存
        redisTemplate.delete(valsmsPre + phoneNo);

        UserClientLoginInfo loginInfo = userClientLoginInfoRepository.findByDeleteFlagAndClientInfoId("0", clientInfo.getUuid());
        loginInfo.setPwd(CommonUtil.toSha256(pass));
        userClientLoginInfoRepository.save(loginInfo);
    }

    /**
     * 配置订单详情
     *
     * @param uuid
     * @return
     */
    @Override
    public List<contactModel> assignOrder(String uuid) throws LoginException {
        if (StringUtils.isEmpty(uuid)) {
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
    public List<contactModel> accountManagement(String uuid) throws LoginException {
        return iContactMapper.accountManagement(uuid);

    }

    @Override
    public Map<String, String> getPassStatus(UserTokenInfo userInfo) throws LoginException{
        UserClientInfo clientInfo = userInfoRepository.findByUuid(userInfo.getUuid());
        if (null == clientInfo){
            throw new LoginException(ResultCode.SELECT_NULL_MSG);
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("txPass",String.valueOf(!StringUtils.isEmpty(clientInfo.getTxPassword())));
        map.put("cer",String.valueOf(UserClientInfo.CER_PASS.equals(clientInfo.getCer())));
        return map;
    }

    @Override
    public UserTokenInfo findUserByUserId(String userId) {
        UserClientInfo clientInfo = userInfoRepository.findByUuid(userId);
        if (null == clientInfo){
            return null;
        }
        UserTokenInfo userInfo = MappingEntity2ModelConverter.CONVERTERFROMUSERCLIENTINFO(clientInfo);
        return userInfo;
    }
}