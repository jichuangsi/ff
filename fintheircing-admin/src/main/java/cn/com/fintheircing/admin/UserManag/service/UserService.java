package cn.com.fintheircing.admin.UserManag.service;

import cn.com.fintheircing.admin.UserManag.Excption.UserServiceException;
import cn.com.fintheircing.admin.UserManag.model.AdminClientInfModel;
import cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel;
import cn.com.fintheircing.admin.UserManag.model.ContactInfoModel;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    @Transactional
    List<AdminClientInfModel> findAllUserInfo(AdminClientInfModel queryModel) throws UserServiceException;

    //AdminClientInfModel findOneById(String id)throws UserServiceException;
    @Transactional
    AdminClientInfModel changeStatus(String id) throws UserServiceException;
    @Transactional
    List<AdminClientInfModel> findByOption(AdminClientInfModel queryModel)throws UserServiceException;

    @Transactional
    AdminClientInfModel changeProxyNum(AdminClientInfModel model)throws UserServiceException;
    @Transactional
    AdminClientInfModel returnStatus(String id)throws UserServiceException;

    List<AskMoneyInfoModel> insideMoney(AskMoneyInfoModel model)throws UserServiceException;

    List<AskMoneyInfoModel> bankCard(AskMoneyInfoModel model) throws UserServiceException;

    AskMoneyInfoModel updatebankCard(AskMoneyInfoModel model);

    AskMoneyInfoModel changeMoneyBySelf(AskMoneyInfoModel model);

    AskMoneyInfoModel insideMoneyHandel(AskMoneyInfoModel model);

    List<ContactInfoModel> contactRecode(String goodsType);
}
