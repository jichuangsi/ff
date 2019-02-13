package cn.com.fintheircing.customer.user.service;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.user.dao.mapper.IPayMapper;
import cn.com.fintheircing.customer.user.dao.repository.IRecodInfoPayRepository;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.entity.RecodeInfoPay;
import cn.com.fintheircing.customer.user.entity.UserAccount;
import cn.com.fintheircing.customer.user.exception.AccountPayException;
import cn.com.fintheircing.customer.user.model.payresultmodel.RecodeInfoPayModel;
import cn.com.fintheircing.customer.user.utlis.Model2Entity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
            UserAccount account = iUserAccountRepository.findByUserId(model.getUserId());
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
}
