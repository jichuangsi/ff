package cn.com.fintheircing.sms.mq.consumer;

import cn.com.fintheircing.sms.mq.consumer.model.SendSmsModel;
import cn.com.fintheircing.sms.service.ISmsSendService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class SendSmsCommandReceiver {
	
	@Resource
	private ISmsSendService smsSendService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RabbitListener(queuesToDeclare = {
			@Queue(value = "${custom.mq.consumer.queue-name.sendSms}") })
	public void process(String jsonData) {
		System.out.println(jsonData);
		logger.debug("Receive sendSms mq msg :"+ jsonData);
		SendSmsModel sendSmsModel = JSONObject.parseObject(jsonData,SendSmsModel.class);
		if(SendSmsModel.VALCODE_TYPE.equalsIgnoreCase(sendSmsModel.getType())) {

			smsSendService.sendValCodeSms(sendSmsModel.getPhoneNo(), sendSmsModel.getContent(),sendSmsModel.getType());
		}
		logger.debug("process sendSms mag sucess");
		
	}

}
