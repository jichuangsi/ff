package cn.com.fintheircing.sms.service.impl;

import cn.com.fintheircing.sms.Commons.SmsSendUtil;
import cn.com.fintheircing.sms.Commons.MesStatus;

import cn.com.fintheircing.sms.dao.RecodingRepository;
import cn.com.fintheircing.sms.dao.mapper.IRecodingMapper;
import cn.com.fintheircing.sms.model.MesModel;
import cn.com.fintheircing.sms.service.ISmsSendService;
import cn.com.fintheircing.sms.utils.Model2Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class SmsSendServiceDefImpl implements ISmsSendService{

	@Value("${custom.sms.signName}")
	private String signName;
	@Value("${custom.sms.templateCode}")
	private String templateCode;
	// 签名KEY
	@Value("${custom.sms.accessKeyId}")
	private String accessKeyId;
	// 签名密钥
	@Value("${custom.sms.accessKeySecret}")
	private String accessKeySecret;

	@Autowired
	private RecodingRepository recodingRepository;
	@Autowired
	private IRecodingMapper iRecodingMapper;
	@Override
	@Transactional
	public void sendValCodeSms(String phone,String code ,String taskType) {
		String message=SmsSendUtil.send(phone, code,signName,templateCode,accessKeyId,accessKeySecret);
		MesModel model =new MesModel();
		if (StringUtils.isEmpty(message)){
			model.setIsSucess(MesStatus.getName(1));
		}else {
			model.setIsSucess(MesStatus.getName(0));
		}
		model.setContent(message);
		model.setTaskType(taskType);
		model.setPhone(phone);
		recodingRepository.save(Model2Entity.CovermesModel(model));
	}

	@Override
	public List<MesModel> findAllMessage() {
		return iRecodingMapper.findAllMes();
	}

	/**
	 * 返回userid的所有信息
	 * @param id
	 * @return
	 */
	@Override
	public List<MesModel> findAllMesByUserId(String id) {
			return iRecodingMapper.findAllMesByUserId(id);
	}

}
