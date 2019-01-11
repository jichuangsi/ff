package cn.com.fintheircing.sms.service.impl;

import cn.com.fintheircing.sms.Commons.SmsSendUtil;
import cn.com.fintheircing.sms.Commons.Status;
import cn.com.fintheircing.sms.Repository.RecodingRepository;
import cn.com.fintheircing.sms.entity.Recoding;
import cn.com.fintheircing.sms.model.mesModel;
import cn.com.fintheircing.sms.service.ISmsSendService;
import cn.com.fintheircing.sms.utils.Entity2Model;
import cn.com.fintheircing.sms.utils.Model2Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class SmsSendServiceDefImpl implements ISmsSendService{

	@Value("${sms.signName}")
	private String signName;
	@Value("${sms.templateCode}")
	private String templateCode;
	// 签名KEY
	@Value("${sms.accessKeyId}")
	private String accessKeyId;
	// 签名密钥
	@Value("${sms.accessKeySecret}")
	private String accessKeySecret;

	@Autowired
	private SmsSendUtil smsSendUtil;
	@Autowired
	private RecodingRepository recodingRepository;

	@Override
	@Transactional
	public void sendValCodeSms(String phone,String code ,String taskType) {
		String message=smsSendUtil.send(phone, code,signName,templateCode,accessKeyId,accessKeySecret);
		mesModel model =new mesModel();
		if (StringUtils.isEmpty(message)){
			model.setIsSucess(Status.FAIL);
		}else {
			model.setIsSucess(Status.SUCCESS);
		}
		model.setUuid(UUID.randomUUID().toString().replaceAll("",""));
		model.setCreatedTime(new Date());
		model.setContent(message);
		model.setTaskType(taskType);
		model.setPhone(phone);
		recodingRepository.save(Model2Entity.CovermesModel(model));
	}

	@Override
	public List<mesModel> findAllMessage() {
		List<mesModel> models=null;
		List<Recoding> all = recodingRepository.findAll();
		all.forEach(one->{

			models.add(Entity2Model.coverRecoding(one));
		});
		return models;
	}

}
