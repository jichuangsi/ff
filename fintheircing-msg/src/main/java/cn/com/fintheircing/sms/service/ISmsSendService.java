package cn.com.fintheircing.sms.service;

import cn.com.fintheircing.sms.model.mesModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ISmsSendService {
	@Transactional
	void sendValCodeSms(String phone, String valCode,String tasktype);

	List<mesModel> findAllMessage();
}
