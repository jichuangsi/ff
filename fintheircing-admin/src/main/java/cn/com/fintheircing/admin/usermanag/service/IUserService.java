package cn.com.fintheircing.admin.usermanag.service;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.tranfer.TranferEntrustModel;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.BankCardModel;
import cn.com.fintheircing.admin.usermanag.model.UserStockHoldingModel;
import com.github.pagehelper.PageInfo;

import javax.transaction.Transactional;
import java.util.List;

public interface IUserService {
    @Transactional
    List<AdminClientInfoModel> findAllUserInfo(AdminClientInfoModel queryModel) throws UserServiceException;

    //AdminClientInfoModel findOneById(String id)throws UserServiceException;
    @Transactional
    boolean changeStatus(String id) throws UserServiceException;
    @Transactional
    List<AdminClientInfoModel> findByOption(AdminClientInfoModel queryModel)throws UserServiceException;

    @Transactional
    int changeProxyNum(String userId, String proxyId)throws UserServiceException;
    @Transactional
    boolean returnStatus(String id)throws UserServiceException;
//
//    List<AskMoneyInfoModel> insideMoney(AskMoneyInfoModel model)throws UserServiceException;
//
    List<BankCardModel> findAllBankCard(BankCardModel model) throws UserServiceException;

    boolean updatebankCard(String id) throws UserServiceException;

    List<AdminClientInfoModel> findAllDetails(String id);

    boolean changeAmount(String id, double amount);

//    AskMoneyInfoModel changeMoneyBySelf(AskMoneyInfoModel model);

//    AskMoneyInfoModel insideMoneyHandel(AskMoneyInfoModel model);

//    List<ContactInfoModel> contactRecode(String goodsType);

    PageInfo<TranferEntrustModel> getPageEntrusts(TranferEntrustModel model) throws BusinessException;

    PageInfo<UserStockHoldingModel> getPageHolding(String userId, int index, int size);
}
