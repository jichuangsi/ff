package cn.com.fintheircing.admin.usermanag.service;


import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfoModel;
import cn.com.fintheircing.admin.usermanag.model.ContactInfoModel;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    @Transactional
    List<AdminClientInfoModel> findAllUserInfo(AdminClientInfoModel queryModel) throws UserServiceException;

    //AdminClientInfoModel findOneById(String id)throws UserServiceException;
    @Transactional
    AdminClientInfoModel changeStatus(String id) throws UserServiceException;
    @Transactional
    List<AdminClientInfoModel> findByOption(AdminClientInfoModel queryModel)throws UserServiceException;

    @Transactional
    AdminClientInfoModel changeProxyNum(AdminClientInfoModel model)throws UserServiceException;
    @Transactional
    AdminClientInfoModel returnStatus(String id)throws UserServiceException;
//
//    List<AskMoneyInfoModel> insideMoney(AskMoneyInfoModel model)throws UserServiceException;
//
//    List<AskMoneyInfoModel> bankCard(AskMoneyInfoModel model) throws UserServiceException;

//    AskMoneyInfoModel updatebankCard(AskMoneyInfoModel model);
//
//    AskMoneyInfoModel changeMoneyBySelf(AskMoneyInfoModel model);
//
//    AskMoneyInfoModel insideMoneyHandel(AskMoneyInfoModel model);

//    List<ContactInfoModel> contactRecode(String goodsType);
}
