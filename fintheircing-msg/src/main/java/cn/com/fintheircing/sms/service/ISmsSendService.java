package cn.com.fintheircing.sms.service;

import cn.com.fintheircing.sms.model.MesModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ISmsSendService {
	@Transactional
	void sendValCodeSms(String phone, String valCode,String tasktype);

	List<MesModel> findAllMessage();
	/**
	 * 返回userid的所有信息
	 * @param id
	 * @return
	 */
	List<MesModel> findAllMesByUserId(String id);
}
