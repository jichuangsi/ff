package cn.com.fintheircing.admin.usermanag.service.Impl;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IPayInfoRepository;
import cn.com.fintheircing.admin.usermanag.entity.pay.PayInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.*;
import cn.com.fintheircing.admin.usermanag.model.promise.CheckPromiseModel;
import cn.com.fintheircing.admin.usermanag.model.promise.PromiseModel;
import cn.com.fintheircing.admin.usermanag.model.result.AppResultModel;
import cn.com.fintheircing.admin.usermanag.uilts.GsonUtil;
import cn.com.fintheircing.admin.usermanag.uilts.HttpUtils;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.dao.mapper.IBillMapper;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IBillRepository;
import cn.com.fintheircing.admin.usermanag.model.result.*;
import cn.com.fintheircing.admin.usermanag.service.IPayService;
import cn.com.fintheircing.admin.usermanag.uilts.ModelToEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Value("${custom.pay.QRcode}")
    private String gatewayPayByQRcode;
    @Autowired
    private IBillRepository iBillRepository;
    @Autowired
    private IBillMapper iBillMapper;
    @Resource
    private IPayInfoRepository iPayInfoRepository;

    /**
     * 获取第三方支付信息并且返回支付地址
     *
     * @return
     */
    @Override
    public ResultModel getWayToPay(NetQueryModel model, PayConfigModel payConfig) throws UserServiceException {
        Map<String, String> formData = new HashMap<>();
        formData.put("orderId", model.getOrderId());
        formData.put("orderName", model.getOrderName());
        formData.put("payerNo", payConfig.getUserId());
        formData.put("payerName", payConfig.getUserName());
        formData.put("amount", model.getAmount());
        formData.put("tradeId", model.getTradeId());
        formData.put("noticeUrl", model.getNoticeUrl());
        formData.put("encryptionParams", model.getEncryptionParams());

        try {
            String s = HttpUtils.doPost(payConfig.getUrl() + reciveWay, formData);
            ResultModel resultModel = GsonUtil.jsonToObject(s, ResultModel.class);

            if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())) {
                throw new UserServiceException(resultModel.getFailReason());
            }

            return resultModel;
        } catch (Exception exp) {
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
        formData.put("orderId", model.getOrderId());
        formData.put("payerNo", model.getPayerNo());
        formData.put("payerName", model.getPayerName());
        formData.put("amount", model.getAmount());
        formData.put("tradeId", model.getTradeId());
        formData.put("encryptionParams", model.getEncryptionParams());
        formData.put("appId ", model.getAppId());
        try {
            String s = HttpUtils.doPost(url + reciveWay, formData);
            ResultModel resultModel = GsonUtil.jsonToObject(s, ResultModel.class);
            if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())) {
                throw new UserServiceException(resultModel.getFailReason());
            }
//                payResponseModel.setResult(ResultCode.PAY_INFO_EXIT);
            return resultModel;
        } catch (Exception exp) {
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
        formData.put("orderId", model.getOrderId());
        formData.put("encryptionParams", model.getEncryptionParams());
        formData.put("tradeId", model.getTradeId());
        try {
            String s = HttpUtils.doPost(url + queryPayResult, formData);
            BillResponseModel resultModel = GsonUtil.jsonToObject(s, BillResponseModel.class);
            if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())) {
                throw new UserServiceException(resultModel.getFailReason());
            }
            iBillRepository.save(ModelToEntity.COVERBILLTOMODEL(resultModel));
            return resultModel;
        } catch (Exception exp) {
            throw new UserServiceException(exp.getMessage());
        }

    }

    /**
     * 支付返回所有结果集
     *
     * @param model
     * @return
     * @throws UserServiceException
     */
    @Override
    public boolean queryReconTrans(BillQueryModel model, String orderId) throws UserServiceException {
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
            for (TransListModel m : resultModel.getTransList()) {
                if (m.getOrderId().equals(orderId)) {
                    iBillMapper.updateBill(m);
                    return true;
                }
            }
            return false;
        } catch (Exception exp) {
            throw new UserServiceException(exp.getMessage());
        }

    }

    /**
     * 展示支付二维码
     *
     * @param model
     * @param payConfig
     * @return
     * @throws UserServiceException
     */
    @Override
    public AppResultModel payForQRCode(AppQueryModel model, PayConfigModel payConfig) throws UserServiceException {
        Map<String, String> formData = new HashMap<>();
        formData.put("amount", model.getAmount());
        formData.put("orderId", model.getOrderId());
        formData.put("orderName", model.getOrderName());
        formData.put("payerNo", model.getPayerNo());
        formData.put("payerName", model.getPayerName());
        formData.put("tradeId", model.getTradeId());
        formData.put("noticeUrl", model.getNoticeUrl());
        formData.put("encryptionParams", model.getEncryptionParams());
        formData.put("payType", model.getPayType());
        formData.put("remark", model.getRemark());
        try {
            String s = HttpUtils.doPost(payConfig.getUrl() + gatewayPayByQRcode, formData);
            AppResultModel appResultModel = GsonUtil.jsonToObject(s, AppResultModel.class);
            if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(appResultModel.getResult())) {
                throw new UserServiceException(appResultModel.getFailReason());
            }
            if ("1".equalsIgnoreCase(model.getPayType())) {
                appResultModel.setPayType("微信");
            }
            if ("2".equalsIgnoreCase(model.getPayType())) {
                appResultModel.setPayType("支付宝");
            }
            return appResultModel;
        } catch (Exception exp) {
            throw new UserServiceException(exp.getMessage());
        }
    }

    @Override
    public CheckPromiseModel addPromiseMoney(PromiseModel data, UserTokenInfo userInfo) {
        Map<String, Object> parms = new HashMap<>();
        parms.put("userId", data.getUserId());
        parms.put("businessContractId", data.getBusinessContractId());

        CheckPromiseModel model = iBillMapper.findAllPromise(parms);
        model.setTaskType("追加保证金");
        return model;
    }

    @Override
    public List<RecodeInfoPayModel> findAllPayInfo() {
        return iBillMapper.findAllPayInfo();

    }

    @Override
    public int updatePayInfo(RecodeInfoPayModel model) {
        return iBillMapper.updatePayInfo(model);
    }

    /**
     * 查询所有申请追加保证金的个人的信息
     *
     * @return
     */
    @Override
    public List<PromiseModel> findAllApply() {
        List<PromiseModel> allApply = iBillMapper.findAllApply();
        return allApply;
    }

    /**
     * 同意追加保证金申请
     *
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean agreePromiseMoney(UserTokenInfo userInfo, PromiseModel model) {
        PayInfo p = new PayInfo();
        if (iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId()) > 0) {
            p.setWay(model.getWay());
            p.setRemark(model.getRemark());
            p.setCreateTime(model.getCreateTime());
            p.setBusinessContractId(model.getBusinessContractId());
            p.setCostCount(model.getCash());
            p.setCheckStatus("0");
            p.setUpdateTime(new Date());
            p.setUserId(model.getUserId());
            p.setOperaId(userInfo.getUuid());
            p.setOperaName(userInfo.getUserName());
            iPayInfoRepository.save(p);
            return true;
        }

        return false;
    }

    /**
     * 驳回追加保证金申请
     *
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean passPromiseMoney(UserTokenInfo userInfo, PromiseModel model) {
        PayInfo p = new PayInfo();
        if (iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId()) > 0) {
            p.setWay(model.getWay());
            p.setRemark(model.getRemark());
            p.setCreateTime(model.getCreateTime());
            p.setBusinessContractId(model.getBusinessContractId());
            p.setCostCount(model.getCash());
            p.setCheckStatus("1");
            p.setUpdateTime(new Date());
            p.setUserId(model.getUserId());
            p.setOperaId(userInfo.getUuid());
            p.setOperaName(userInfo.getUserName());
            iPayInfoRepository.save(p);
            return true;
        }
        return false;
    }

    /**
     * 提现申请
     *
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean agreewithdrawCash(UserTokenInfo userInfo, RecodeInfoPayModel model) {
        PayInfo p = new PayInfo();
        if (iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId()) > 0) {
            p.setWay(model.getWay());
            p.setRemark(model.getRemark());
            p.setCreateTime(model.getCreatTime());
            p.setCostCount(model.getCostCount());
            p.setCheckStatus(VerifyCode.getName(0));
            p.setUpdateTime(new Date());
            p.setUserId(model.getUserId());
            p.setOperaId(userInfo.getUuid());
            p.setOperaName(userInfo.getUserName());
            iPayInfoRepository.save(p);
            return true;
        }
        return false;
    }

    /**
     * 驳回提现申请
     *
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean passwithdrawCash(UserTokenInfo userInfo, RecodeInfoPayModel model) {

        PayInfo p = new PayInfo();
        if (iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId()) > 0) {
            p.setWay(model.getWay());
            p.setRemark(model.getRemark());
            p.setCreateTime(model.getCreatTime());
            p.setCostCount(model.getCostCount());
            p.setCheckStatus(VerifyCode.getName(1));
            p.setUpdateTime(new Date());
            p.setUserId(model.getUserId());
            p.setOperaId(userInfo.getUuid());
            p.setOperaName(userInfo.getUserName());
            iPayInfoRepository.save(p);
            return true;
        }
        return false;
    }
}
