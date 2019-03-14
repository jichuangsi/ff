package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.entity.UserAccount;
import cn.com.fintheircing.customer.user.exception.AccountPayException;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.model.payresultmodel.PayInfoModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.model.payresultmodel.ResultModel;
import cn.com.fintheircing.customer.user.model.queryModel.NetQueryModel;
import cn.com.fintheircing.customer.user.utlis.GsonUtil;
import cn.com.fintheircing.customer.user.utlis.HttpUtils;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserPayService {

    @Resource
    private IRecodInfoPayRepository iRecodInfoPayRepository;
    @Resource
    private IPayMapper iPayMapper;
    @Resource
    private IUserAccountRepository iUserAccountRepository;


    @Transactional(rollbackFor = Exception.class)
    public RecodeInfoPay costAccount(RecodeInfoPayModel model) throws AccountPayException {
        RecodeInfoPay payInfo = new RecodeInfoPay();
        if (model.getCostCount() > iUserAccountRepository.findAccountByUserId(model.getUserId())) {
            throw new AccountPayException(ResultCode.ACCOUNT_LESS_ERR);
        } else {
            UserAccount account = iUserAccountRepository.findOneByUserId(model.getUserId());
            if (null != account) {
                //扣款
                if (0 != model.getCostCount()) {
                    account.setAccount(account.getAccount() - model.getCostCount());
                    iUserAccountRepository.save(account);
                } else if (0 != model.getAddCount()) {  //加钱
                    account.setAccount(account.getAccount() + model.getAddCount());
                    iUserAccountRepository.save(account);
                }
                // 记录
                payInfo = iRecodInfoPayRepository.save(Model2Entity.UpdateRecodeInfoPayModel(model));
            } else {
                throw new AccountPayException(ResultCode.ACCOUNT_PAY_ERR);
            }
        }
        return payInfo;
    }


    public void saveRecodeInfoPay(RecodeInfoPay payInfo){
        iRecodInfoPayRepository.save(payInfo);
    }

    public ResultModel recodPayInfo(UserTokenInfo userInfo, NetQueryModel model, PayInfoModel payInfoModel) throws AccountPayException{
        Map<String, Object> formData = new HashMap<>();
        formData.put("orderId", model.getOrderId());
        formData.put("orderName", model.getOrderName());
        formData.put("payerNo", userInfo.getUuid());
        formData.put("payerName", userInfo.getUserName());
        formData.put("amount", model.getAmount());
        formData.put("tradeId", model.getTradeId());
        formData.put("noticeUrl", model.getNoticeUrl());
        formData.put("encryptionParams", model.getEncryptionParams());
        try {
            String s = HttpUtils.doPost(payInfoModel.getMethod() + payInfoModel.getPayWay(), formData);
            ResultModel resultModel = GsonUtil.jsonToObject(s, ResultModel.class);

            if (!ResultCode.PAY_INFO_EXIT.equalsIgnoreCase(resultModel.getResult())) {
                throw new AccountPayException(resultModel.getFailReason());
            }

            return resultModel;
        } catch (Exception exp) {
            throw new AccountPayException(exp.getMessage());
        }
    }
}
