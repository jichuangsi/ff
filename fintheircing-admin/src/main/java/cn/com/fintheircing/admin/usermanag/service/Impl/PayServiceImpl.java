package cn.com.fintheircing.admin.usermanag.service.Impl;

import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockEntrustRepository;
import cn.com.fintheircing.admin.business.entity.BusinessContract;
import cn.com.fintheircing.admin.business.entity.BusinessStockEntrust;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.VerifyCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.systemdetect.dao.repository.ProductRepository;
import cn.com.fintheircing.admin.systemdetect.entity.Product;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.IPayInfoRepository;
import cn.com.fintheircing.admin.usermanag.entity.pay.RecodeInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.*;
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
    @Resource
    private IBusinessContractRepository iBusinessContractRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private IBusinessStockEntrustRepository iBusinessStockEntrustRepository;

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

    /**
     * 查询所有未审核的
     * @return
     * @throws UserServiceException
     */
    @Override
    public List<RecodeInfoPayModel> findAllPayInfo() throws UserServiceException {
        List<RecodeInfoPayModel> allPayInfo = iBillMapper.findAllPayInfo();
        if (allPayInfo.isEmpty()&&allPayInfo==null){
            throw new UserServiceException(ResultCode.SELECT_NULL_MSG);
        }
        for (RecodeInfoPayModel m :allPayInfo
                ) {
            if (m.getCostCount()!=0){
                m.setiAccountAmount(m.getAccountAmount()-m.getCostCount());
            }
            if (m.getAddCount()!=0){
                m.setiAccountAmount(m.getAccountAmount()+m.getAddCount());
            }
        }
        return allPayInfo;

    }

    @Override
    public int updatePayInfo(RecodeInfoPayModel model) {
        return iBillMapper.updatePayInfo(model);
    }



    /**
     * 同意追加保证金申请
     *
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean agreePromiseMoney(UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        BusinessContract businessContract = iBusinessContractRepository.findByUuid(model.getBusinessContractId());
        if (StringUtils.isEmpty(businessContract)){
            throw new UserServiceException(ResultCode.CONTACT_NOT_EXITS);
        }
        double a=businessContract.getPromisedMoney()+model.getCostCount();
        businessContract.setPromisedMoney(a);
        iBusinessContractRepository.save(businessContract);
        Map<String,Object> parms =new HashMap<>();
        parms.put("userId", model.getUserId());
        parms.put("money", model.getCostCount());
        if (iBillMapper.updateCostUserAmount(parms)<0){
            throw new UserServiceException(ResultCode.FAILE_COST);
        }
        if (model.getTaskId().equalsIgnoreCase("0")) {
            RecodeInfo p = new RecodeInfo();
            if (iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId()) > 0) {
                p.setWay(model.getWay());
                p.setRemark(model.getRemark());
                p.setCreateTime(model.getCreatTime());
                p.setBusinessContractId(model.getBusinessContractId());
                p.setCostCount(model.getCostCount());
                p.setCheckStatus(VerifyCode.getName(0));
                p.setUpdateTime(new Date());
                p.setUserId(model.getUserId());
                p.setOperaId(userInfo.getUuid());
                p.setOperaName(userInfo.getUserName());
                p.setOperatWay("同意追加保证金");
                iPayInfoRepository.save(p);
                return true;
            }else {
                throw new UserServiceException(ResultCode.SAVE_OPERAT_FAILE);
            }

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
    public boolean passPromiseMoney(UserTokenInfo userInfo,RecodeInfoPayModel model) {
            RecodeInfo p = new RecodeInfo();
        if (iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId()) > 0) {
            p.setWay(model.getWay());
            p.setRemark(model.getRemark());
            p.setCreateTime(model.getCreatTime());
            p.setBusinessContractId(model.getBusinessContractId());
            p.setCostCount(model.getCostCount());
            p.setCheckStatus(VerifyCode.getName(1));
            p.setUpdateTime(new Date());
            p.setUserId(model.getUserId());
            p.setOperaId(userInfo.getUuid());
            p.setOperaName(userInfo.getUserName());
            p.setOperatWay("驳回保证金申请");
            iPayInfoRepository.save(p);
            return true;
        }
        return false;
    }


    /**
     * 同意提现申请
     *
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean agreewithdrawCash(UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        BusinessContract b = iBusinessContractRepository.findByUuid(model.getBusinessContractId());
        if (StringUtils.isEmpty(b)){
            //查询合约
            throw new UserServiceException(ResultCode.CONTACT_NOT_EXITS);
        }
        b.setAvailableMoney(b.getAvailableMoney()-model.getAddCount());
        BusinessContract businessContract = iBusinessContractRepository.save(b);
        if (StringUtils.isEmpty(businessContract)){
            //合约可用资金扣钱
            throw new UserServiceException(ResultCode.WITHDRAW_DEDUCT_FAILE);
        }
        Map<String,Object> parms =new HashMap<>();
        parms.put("userId", model.getUserId());
        parms.put("money", model.getAddCount());
        int i = iBillMapper.updateAddUserAmount(parms);
        if (i<=0){
            //个人提现
            throw new UserServiceException(ResultCode.PERSON_WITHDRAW_FAILE);
        }
        RecodeInfo p = new RecodeInfo();
        if (iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId()) > 0) {
            p.setWay(model.getWay());
            p.setRemark(model.getRemark());
            p.setCreateTime(model.getCreatTime());
            p.setCostCount(model.getAddCount());
            p.setCheckStatus(VerifyCode.getName(0));
            p.setUpdateTime(new Date());
            p.setUserId(model.getUserId());
            p.setOperaId(userInfo.getUuid());
            p.setOperaName(userInfo.getUserName());
            p.setOperatWay("同意提现");
            iPayInfoRepository.save(p);

            return true;
        }else {
            throw new UserServiceException(ResultCode.SAVE_OPERAT_FAILE);
        }

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

        RecodeInfo p = new RecodeInfo();
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
            p.setOperatWay("驳回提现");
            iPayInfoRepository.save(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean expendMoney(UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException {
        RecodeInfo p = new RecodeInfo();
        if (model.getTaskId().equalsIgnoreCase("1")) {
            List<BusinessStockEntrust> byDeleteFlagAndContractId = iBusinessStockEntrustRepository.findByDeleteFlagAndContractId("0", model.getBusinessContractId());
            if (byDeleteFlagAndContractId.size() > 0) {
                //持仓
                throw new UserServiceException(ResultCode.HAS_HODING);
            } else {
                //查询产品id
                String productId = iBusinessContractRepository.findByUuid(model.getBusinessContractId()).getProductId();
               //查询合约
                BusinessContract b = iBusinessContractRepository.findByUuid(model.getBusinessContractId());
                //查询产品
                Product oneById = productRepository.findOneById(productId);
                //扩大资金=产品的杠杆 x 金额
                b.setAvailableMoney(oneById.getLeverRate() * model.getCostCount()+b.getAvailableMoney());
                BusinessContract save = iBusinessContractRepository.save(b);
                if (StringUtils.isEmpty(save)){
                    throw new UserServiceException(ResultCode.CONTACT_SAVE_FAILE);
                }
                iBillMapper.updateRecodeInfo(model.getRecodeInfoPayId());
                p.setWay(model.getWay());
                p.setRemark(model.getRemark());
                p.setCreateTime(model.getCreatTime());
                p.setBusinessContractId(model.getBusinessContractId());
                p.setCostCount(model.getCostCount());
                p.setCheckStatus("1");
                p.setUpdateTime(new Date());
                p.setUserId(model.getUserId());
                p.setOperaId(userInfo.getUuid());
                p.setOperaName(userInfo.getUserName());
                p.setOperatWay("同意扩大融资");
                iPayInfoRepository.save(p);
                return true;
            }
        }
        return false;
    }
}