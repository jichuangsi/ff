package cn.com.fintheircing.admin.usermanag.service.Impl;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.usermanag.uilts.GsonUtil;
import cn.com.fintheircing.admin.usermanag.uilts.HttpUtils;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IBillMapper;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IBillRepository;
import cn.com.fintheircing.admin.usermanag.model.pay.AppQueryModel;
import cn.com.fintheircing.admin.usermanag.model.pay.NetQueryModel;
import cn.com.fintheircing.admin.usermanag.model.pay.ResultModel;
import cn.com.fintheircing.admin.usermanag.model.result.*;
import cn.com.fintheircing.admin.usermanag.service.IPayService;
import cn.com.fintheircing.admin.usermanag.uilts.ModelToEntity;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 支付实现层 class
 *
 * @author yaoxiong
 * @date 2019/1/9
 */
@Service
public class PayServiceImpl implements IPayService {
    @Value("${custom.pay.url}")
    private String url;
    @Value("${custom.pay.reciveWay}")
    private String reciveWay;
    @Value("${custom.pay.weChatOrAilpay}")
    private String weChatOrAilpay;
    @Value("${custom.pay.queryPayResult}")
    private String queryPayResult;
    @Value("${custom.pay.queryReconTrans}")
    private String queryReconTrans;
    @Autowired
    private IBillRepository iBillRepository;
    @Autowired
    private IBillMapper iBillMapper;
    /**
     * 获取第三方支付信息并且返回支付地址
     *
     * @return
     */
    @Override
    public ResultModel getWayToPay(NetQueryModel model) throws UserServiceException {
       Map<String, String> formData = new HashMap<>();
        formData.put("orderId",model.getOrderId());
        formData.put("orderName",model.getOrderName());
        formData.put("payerNo",model.getPayerNo());
        formData.put("payerName",model.getPayerName());
        formData.put("amount",model.getAmount());
        formData.put("tradeId",model.getTradeId());
        formData.put("noticeUrl",model.getNoticeUrl());
        formData.put("encryptionParams",model.getEncryptionParams());

        try {
            String s = HttpUtils.doPost(url + reciveWay, formData);
            ResultModel resultModel = GsonUtil.jsonToObject(s, ResultModel.class);
                if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())){
                    throw new UserServiceException(resultModel.getFailReason());
                }
//                payResponseModel.setResult(ResultCode.PAY_INFO_EXIT);
                return resultModel;
            }

         catch (Exception exp) {
            throw new UserServiceException(exp.getMessage());
        }

    }

    /**
     * 支付宝微信支付信息并且返回支付
     *
     * @param model
     */
    @Override
    public ResultModel getWechatOrAilpayInfo(AppQueryModel model) throws UserServiceException {
        Map<String, String> formData = new HashMap<>();
        formData.put("orderId",model.getOrderId());
        formData.put("payerNo",model.getPayerNo());
        formData.put("payerName",model.getPayerName());
        formData.put("amount",model.getAmount());
        formData.put("tradeId",model.getTradeId());
        formData.put("encryptionParams",model.getEncryptionParams());
        formData.put("openId",model.getOpenId());
        formData.put("appType",model.getAppType());
        formData.put("appId ",model.getAppId());
        try {
            String s = HttpUtils.doPost(url + reciveWay, formData);
            ResultModel resultModel = GsonUtil.jsonToObject(s, ResultModel.class);
                if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())){
                    throw new UserServiceException(resultModel.getFailReason());
                }
//                payResponseModel.setResult(ResultCode.PAY_INFO_EXIT);
                return resultModel;
            }

         catch (Exception exp) {
            throw new UserServiceException(exp.getMessage());
        }

    }



    /**
     * 支付结果查询
     *
     * @param model
     * @return
     * @throws UserServiceException
     */
    @Override
    public BillResponseModel queryPayResult(BillQueryModel model) throws UserServiceException {
        Map<String, String> formData = new HashMap<>();
        formData.put("orderId",model.getOrderId());
        formData.put("encryptionParams",model.getEncryptionParams());
        formData.put("tradeId",model.getTradeId());
        try {
            String s = HttpUtils.doPost(url + queryPayResult, formData);
            BillResponseModel resultModel = GsonUtil.jsonToObject(s, BillResponseModel.class);
                if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())){
                    throw new UserServiceException(resultModel.getFailReason());
                }
               iBillRepository.save(ModelToEntity.COVERBILLTOMODEL(resultModel));
                return resultModel;
            }

         catch (Exception exp) {
            throw new UserServiceException(exp.getMessage());
        }

    }
    /**
     * 支付返回所有结果集
     * @param model
     * @return
     * @throws UserServiceException
     */
    @Override
    public boolean queryReconTrans(BillQueryModel model , String orderId) throws UserServiceException {
        Map<String, String> formData = new HashMap<>();
        formData.put("transDate", model.getTransDate());
        formData.put("tradeId", model.getTradeId());
        formData.put("encryptionParams", model.getEncryptionParams());
        if (StringUtils.isEmpty(orderId)) {
            throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
        }
        try {
            String s = HttpUtils.doPost(url + queryReconTrans, formData);
            BillListResponseModel<TransListModel> resultModel = GsonUtil.jsonToObject(s, BillListResponseModel.class);
            if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())) {
                throw new UserServiceException(resultModel.getFailReason());
            }
            for (TransListModel m : resultModel.getTransList())
            {
                if (m.getOrderId().equals(orderId)) {
                    iBillMapper.updateBill(m);
                    return true;
                }
            }
            return false;
        }catch (Exception exp) {
            throw new UserServiceException(exp.getMessage());
        }

    }


}
