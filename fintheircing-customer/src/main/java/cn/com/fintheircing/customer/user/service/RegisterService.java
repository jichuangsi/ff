package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import cn.com.fintheircing.admin.todotask.model.TaskModel;
import cn.com.fintheircing.customer.common.CommonUtil;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.mapper.IUserClientInfoMapper;
import cn.com.fintheircing.customer.user.dao.repository.IUserClientLoginInfoRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserInfoRepository;
import cn.com.fintheircing.customer.user.entity.UserClientInfo;
import cn.com.fintheircing.customer.user.entity.UserClientLoginInfo;
import cn.com.fintheircing.customer.user.exception.RegisterheckExistExcption;
import cn.com.fintheircing.customer.user.model.RegisterModel;
import cn.com.fintheircing.customer.user.model.UserForLoginModel;
import cn.com.fintheircing.customer.user.service.feign.ITodoTaskService;
import cn.com.fintheircing.customer.user.service.feign.model.CreateTodoTaskModel;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterService {

	@Resource
	private IUserInfoRepository userInfoRepository;
	@Resource
	private IUserClientLoginInfoRepository userClientLoginInfoRepository;
	@Resource
	private IUserClientInfoMapper userClientInfoMapper;
	@Resource
	private ITodoTaskService todoTaskService;
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	@Resource
	private AmqpTemplate rabbitTemplate;

	private int valCodeLength = 4;
	private String valsmsPre = "sms_val_";
	private long valZSendInterSeconds = 60;// 通知发送时间间隔
	private String notifyType = "valCode";// 短信类型
	@Value("${custom.mq.producer.queue-name.sendSms}")
	private String smsMqDestName;

	// 根据手机号获取验证码
	public String getValCode(String phoneNo) throws RegisterheckExistExcption {

		synchronized (phoneNo.intern()) {
			// 使用手机号作为用户名
			if (null != userInfoRepository.findOneByUserName(phoneNo)) {
				throw new RegisterheckExistExcption(phoneNo + "已存在");
			}
			// 看缓存中是否存在发送验证码记录
			if (null != redisTemplate.opsForValue().get(valsmsPre + phoneNo)) {
				throw new RegisterheckExistExcption(valZSendInterSeconds + "秒内只能发送一次");
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
	@Transactional
	public void register(RegisterModel registerModel) throws RegisterheckExistExcption {
		String phoneNo = registerModel.getPhoneNo();
		String pwd = registerModel.getPwd();
		String valCode = registerModel.getValCode();
		synchronized (phoneNo.intern()) {
			// 使用手机号作为用户名
			if (null != userInfoRepository.findOneByUserName(phoneNo)) {
				throw new RegisterheckExistExcption(phoneNo + "已存在");
			}

			// 取缓存中是否存在发送验证码记录
			String valCodeInCache = redisTemplate.opsForValue().get(valsmsPre + phoneNo);
			// 对比验证码
			if (StringUtils.isEmpty(valCodeInCache)) {
				throw new RegisterheckExistExcption("验证码不存在或已过期");
			}
			if (!valCodeInCache.equals(valCode)) {
				throw new RegisterheckExistExcption("验证码不正确");
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
			userClientInfo = userInfoRepository.save(userClientInfo);
			
			//新增登录信息
			UserClientLoginInfo userClientLoginInfo = new UserClientLoginInfo();
			userClientLoginInfo.setLoginName(phoneNo);//手机号做登录名
			//对登录密码做SHA256处理
			userClientLoginInfo.setPwd(CommonUtil.toSha256(pwd));
			userClientLoginInfoRepository.save(userClientLoginInfo);
			
			//创建待办注册审核任务
			TaskModel model = new TaskModel();
			model.setTaskType(CreateTodoTaskModel.TASK_TYPE_REG);
			model.setTaskId(userClientInfo.getUuid());
			model.setPhoneNo(phoneNo);
			model.setUserName(phoneNo);
			ResponseModel<CreateRegTodoTaskModel> reponse = todoTaskService.createRegTodoTask(model);
			if(!ResultCode.SUCESS.equals(reponse.getCode())) {
				throw new RegisterheckExistExcption("注册审核暂时停止");
			}
		}
	}
	
	//登录时获取用户信息
	public UserForLoginModel getUserForLogin(UserForLoginModel model) {
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

}
