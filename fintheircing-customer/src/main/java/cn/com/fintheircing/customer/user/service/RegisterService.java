package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.common.utils.CommonUtil;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.constant.RoleCodes;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.model.RoleModel;
import cn.com.fintheircing.customer.user.dao.mapper.IUserClientInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserClientLoginInfoRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserAccount;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;
import cn.com.fintheircing.customer.user.exception.RegisterheckExistException;
import cn.com.fintheircing.customer.user.model.RegisterModel;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterService {

	@Resource
	private IUserInfoRepository userInfoRepository;
	@Resource
	private IUserClientLoginInfoRepository userClientLoginInfoRepository;
	@Resource
	private IUserClientInfoMapper userClientInfoMapper;
	/*@Resource
	private ITodoTaskService todoTaskService;*/
	@Resource
	private IUserAccountRepository userAccountRepository;
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	@Resource
	private AmqpTemplate rabbitTemplate;
	@Resource
	private IAdminFeignService adminFeignService;

	private int valCodeLength = 4;
	private String valsmsPre = "sms_val_";
	private long valZSendInterSeconds = 60;// 通知发送时间间隔
	private String notifyType = "valCode";// 短信类型
	@Value("${custom.mq.producer.queue-name.sendSms}")
	private String smsMqDestName;

	// 根据手机号获取验证码
	public String getValCode(String phoneNo) throws RegisterheckExistException {
		synchronized (phoneNo.intern()) {
			// 使用手机号作为用户名
			if (null != userInfoRepository.findOneByUserName(phoneNo)) {
				throw new RegisterheckExistException(phoneNo + "已存在");
			}
			// 看缓存中是否存在发送验证码记录
			if (null != redisTemplate.opsForValue().get(valsmsPre + phoneNo)) {
				throw new RegisterheckExistException(valZSendInterSeconds + "秒内只能发送一次");
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

	// 新增注册
	@Transactional(rollbackOn = Exception.class)
	public void register(RegisterModel registerModel) throws RegisterheckExistException {
		this.getRole(); //一次性加载role
		String invitId = "";
		String phoneNo = registerModel.getPhoneNo();
		String pwd = registerModel.getPwd();
		String valCode = registerModel.getValCode();
		if (!StringUtils.isEmpty(registerModel.getInvitCode())){
			invitId = adminFeignService.getInviteId(registerModel.getInvitCode());
		}
		synchronized (phoneNo.intern()) {
			// 使用手机号作为用户名
			if (null != userInfoRepository.findOneByUserName(phoneNo)) {
				throw new RegisterheckExistException(phoneNo + "已存在");
			}

			// 取缓存中是否存在发送验证码记录
			String valCodeInCache = redisTemplate.opsForValue().get(valsmsPre + phoneNo);
			// 对比验证码
			if (StringUtils.isEmpty(valCodeInCache)) {
				throw new RegisterheckExistException("验证码不存在或已过期");
			}
			if (!valCodeInCache.equals(valCode)) {
				throw new RegisterheckExistException("验证码不正确");
			}
			//删除验证码缓存
			redisTemplate.delete(valsmsPre + phoneNo);

			//新增用户信息
			UserClientInfo userClientInfo = new UserClientInfo();
			userClientInfo.setUserName(phoneNo);//手机号做用户名
			userClientInfo.setDisplayname(phoneNo);
			userClientInfo.setPhone(phoneNo);
			userClientInfo.setStatus(UserClientInfo.STATUS_INIT);
			userClientInfo.setCer(UserClientInfo.CER_NOT);
			userClientInfo.setRoleGrade(RoleCodes.ROLE_KEY_STRING.get("U"));//添加区别用户管理员
			userClientInfo.setCreatedTime(new Date());
			userClientInfo.setUpdatedTime(new Date());
			userClientInfo.setInviterId(invitId);   //添加邀请人
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
			userAccount.setCreatedTime(new Date());
			userAccount.setUpdatedTime(new Date());
			userAccountRepository.save(userAccount);

			UserTokenInfo userInfo = new UserTokenInfo();
			userInfo.setUuid(userClientInfo.getUuid());
			userInfo.setRoleGrade(userClientInfo.getRoleGrade());
			if (!adminFeignService.saveUserSpread(userInfo)){
				throw new RegisterheckExistException(ResultCode.SPREAD_CREATED_ERR);
			}


			/*//创建待办注册审核任务
			CreateTodoTaskModel model = new CreateTodoTaskModel();
			model.setTaskType(CreateTodoTaskModel.TASK_TYPE_REG);
			model.setRegisterUserId(userClientInfo.getUuid());
			model.setPhoneNo(phoneNo);
			ResponseModel<Object> reponse = todoTaskService.createRegTodoTask(model);
			if(!ResultCode.SUCESS.equals(reponse.getCode())) {
				throw new RegisterheckExistException("注册审核暂时停止");
			}*/
		}

	}
	
	//登录时获取用户信息
	public UserTokenInfo getUserForLogin (UserTokenInfo model) {
		model.setPwd(CommonUtil.toSha256(model.getPwd()));
		return userClientInfoMapper.find(model);
	}

	// 生成随机数字
	private String getRandomNumCode(int number) {
		String codeNum = "";
		int[] numbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random random = new Random();
		for (int i = 0; i < number; i++) {
			int next = random.nextInt(10000);
			codeNum += numbers[next % 10];
		}
		return codeNum;
	}

	public Boolean getRole(){
		if (!(RoleCodes.ROLE_KEY_INTEGER.size()>0)){
			List<RoleModel> roles = adminFeignService.getRoles();
			for (RoleModel role : roles) {
				RoleCodes.ROLE_KEY_INTEGER.put(role.getPosition(), role.getSign());
				RoleCodes.ROLE_KEY_STRING.put(role.getSign(), role.getPosition());
			}
		}
		return true;
	}



}
