package cn.com.fintheircing.sms.service.impl;

import cn.com.fintheircing.sms.Commons.SmsSendUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import cn.com.fintheircing.sms.service.ISmsSendService;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class SmsSendServiceDefImpl implements ISmsSendService{
	@Autowired
	private SmsSendUtil smsSendUtil;
	@Override
	public void sendValCodeSms(String phone,String code) {
		smsSendUtil.send(phone,code);
	}
}
