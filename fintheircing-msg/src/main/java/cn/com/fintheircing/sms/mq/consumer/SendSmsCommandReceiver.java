package cn.com.fintheircing.sms.mq.consumer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.com.fintheircing.sms.mq.consumer.model.SendSmsModel;
import cn.com.fintheircing.sms.service.ISmsSendService;


@Component
public class SendSmsCommandReceiver {
	
	@Resource
	private ISmsSendService smsSendService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RabbitListener(queuesToDeclare = {
			@Queue(value = "${custom.mq.consumer.queue-name.sendSms}") })
	public void process(String jsonData) {
		logger.debug("Receive sendSms mq msg :"+ jsonData);
		SendSmsModel sendSmsModel = JSONObject.parseObject(jsonData,SendSmsModel.class);
		if(SendSmsModel.VALCODE_TYPE.equalsIgnoreCase(sendSmsModel.getType())) {
			smsSendService.sendValCodeSms(sendSmsModel.getPhoneNo(), sendSmsModel.getContent());
		}
		logger.debug("process sendSms mag sucess");
		
	}

}
