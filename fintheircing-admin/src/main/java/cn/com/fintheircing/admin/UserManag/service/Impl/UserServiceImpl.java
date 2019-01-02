package cn.com.fintheircing.admin.usermanag.service.Impl;

import cn.com.fintheircing.admin.Repository.AdminClientInfoRepository;
import cn.com.fintheircing.admin.Repository.AskMoneyInfoRepository;
import cn.com.fintheircing.admin.Repository.UserClientInfoRepository;
import cn.com.fintheircing.admin.Repository.contactInfoRepository;
import cn.com.fintheircing.admin.UserManag.Excption.UserServiceException;
import cn.com.fintheircing.admin.UserManag.mapper.fundsAskMapper;
import cn.com.fintheircing.admin.UserManag.model.AskMoneyInfoModel;
import cn.com.fintheircing.admin.UserManag.model.ContactInfoModel;
import cn.com.fintheircing.admin.UserManag.service.UserService;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.entity.AskMoneyInfo;
import cn.com.fintheircing.admin.common.entity.Contact.contactInfo;
import cn.com.fintheircing.admin.common.entity.UserClientInfo;
import cn.com.fintheircing.admin.common.utils.EntityToModel;
import cn.com.fintheircing.admin.common.utils.ModelToEntity;
import cn.com.fintheircing.admin.usermanag.dao.mapper.userMapper;
import cn.com.fintheircing.admin.usermanag.model.AdminClientInfModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserClientInfoRepository userClientInfoRepository;
    @Autowired
    userMapper usermapper;
    @Autowired
    fundsAskMapper askMapper;
    @Autowired
    AdminClientInfoRepository adminClientInfoRepository;
    @Autowired
    AskMoneyInfoRepository askMoneyInfoRepository;
    @Autowired
    contactInfoRepository contactInfoRepository;
    @Override
    public List<AdminClientInfModel> findAllUserInfo(AdminClientInfModel Model
    ) throws UserServiceException {
        List<AdminClientInfo> users=null;
        //如果model==null 就返回全部
        try {
            List<AdminClientInfModel> all = usermapper.findAll(Model);
            if (all==null||all.size()==0){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            }else {

                return all;
            }
//            else {
//                for (AdminClientInfo a: all
//                     ) {
//                    if ("0".equals(a.getStatus())){
//                        users.add(a);
//                    }
//                }
//                return convertQuestionList(users);
//            }
        }catch (Exception e){
            throw  new UserServiceException(e.getMessage());
        }
    }


//    public AdminClientInfModel findOneById(String id)throws UserServiceException {
//        try {
//
//            UserInfo oneById = userInfoRepository.findOneByIdAndStatus(id,"A");
//            if (StringUtils.isEmpty(id)){
//                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
//            }else if (oneById==null){
//                throw new UserServiceException(ResultCode.USER_EXITS);
//            }else {
//                return EntityToModel.coverUserInfo(oneById);
//            }
//        }catch (Exception e){
//            throw new UserServiceException(e.getMessage());
//        }



    @Override
    public AdminClientInfModel changeStatus(String id) throws UserServiceException {
        try {
            AdminClientInfo oneByUuid = adminClientInfoRepository.findOneByUuid(id);

            if (StringUtils.isEmpty(id)){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            }else if (oneByUuid==null||"0".equals(oneByUuid.getStatus())){
                throw new UserServiceException(ResultCode.USER_EXITS);
            }else {
                oneByUuid.setStatus("1");
                return convertQuestionList(oneByUuid);
            }
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public List<AdminClientInfModel> findByOption(AdminClientInfModel model)throws UserServiceException {
        List<AdminClientInfModel> users=null;
        try {
            List<AdminClientInfModel> all = usermapper.findAll(model);
            if (all==null||all.size()==0){
                throw new UserServiceException(ResultCode.USER_EXITS);
            }else {
                for (AdminClientInfModel a: all
                        ) {
                    if ("1".equals(a.getStatus())){
                        users.add(a);
                    }
                }
                return users;
            }
        }catch (Exception e){
            throw  new UserServiceException(e.getMessage());
        }

    }

    @Override
    public AdminClientInfModel changeProxyNum(AdminClientInfModel model)throws UserServiceException {
        try{
            if (model==null){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            }else {
            return convertentity(model);
            }
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public AdminClientInfModel returnStatus(String id) throws UserServiceException{
        try {
            AdminClientInfo oneByUuid = adminClientInfoRepository.findOneByUuid(id);

            if (StringUtils.isEmpty(id)){
                throw new UserServiceException(ResultCode.PARAM_MISS_MSG);
            }else if (oneByUuid==null||"1".equals(oneByUuid.getStatus())){
                throw new UserServiceException(ResultCode.USER_EXITS);
            }else {
                oneByUuid.setStatus("0");
                return convertQuestionList(oneByUuid);
            }
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }

    }

    @Override
    public List<AskMoneyInfoModel> insideMoney(AskMoneyInfoModel model)throws UserServiceException {
        try {
                List<AskMoneyInfoModel> users=null;
                if (model==null){
                    List<AskMoneyInfo> all = askMoneyInfoRepository.findAll();
                    all.forEach(user1->{
                        UserClientInfo oneById = userClientInfoRepository.findOneByUserId(user1.getUserId());
                        users.add(EntityToModel.coverAskMoneyInfo(user1,oneById));
                    });
                }else {
                List<AskMoneyInfo> alluser = askMapper.findAlluser(model);
                if (alluser==null||alluser.size()==0){throw new UserServiceException(ResultCode.USER_EXITS);}else {
                    alluser.forEach(user2->{
                        UserClientInfo oneById = userClientInfoRepository.findOneByUserId(user2.getUserId());
                        users.add(EntityToModel.coverAskMoneyInfo(user2,oneById));
                    });
                }
            }
                return users;
        }catch (Exception e){
                throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public List<AskMoneyInfoModel> bankCard(AskMoneyInfoModel model) throws UserServiceException {
        try {
                List<AskMoneyInfoModel> users=null;
                List<AskMoneyInfo> alluser = askMapper.findAlluser(model);
                if (alluser==null||alluser.size()==0){throw new UserServiceException(ResultCode.USER_EXITS);}else {
                    alluser.forEach(user2 -> {
                        if (!"0".equals(user2.getDeleteFlag())) {
                            UserClientInfo oneById = userClientInfoRepository.findOneByUserId(user2.getUserId());
                            users.add(EntityToModel.coverAskMoneyInfo(user2, oneById));
                        }
                    });
             // }
            }
            return users;
        }catch (Exception e){
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public AskMoneyInfoModel updatebankCard(AskMoneyInfoModel model) {
        AskMoneyInfo askMoneyInfo = ModelToEntity.coverAskMoneyInfo2entity(model);
        askMoneyInfo.setDeleteFlag("1");
        AskMoneyInfo save = askMoneyInfoRepository.save(askMoneyInfo);
        UserClientInfo oneByPhone = userClientInfoRepository.findOneByUserId(save.getUserId());
//        EntityToModel.coverAskMoneyInfo(save,oneByPhone);
        return   EntityToModel.coverAskMoneyInfo(save,oneByPhone);
    }
    /*未实现*/
    @Override
    public AskMoneyInfoModel changeMoneyBySelf(AskMoneyInfoModel model) {
        return null;
    }

    @Override
    public AskMoneyInfoModel insideMoneyHandel(AskMoneyInfoModel model) {
        AskMoneyInfo askMoneyInfo = ModelToEntity.coverAskMoneyInfo2entity(model);
        AskMoneyInfo save = askMoneyInfoRepository.save(askMoneyInfo);
        UserClientInfo oneByPhone = userClientInfoRepository.findOneByUserId(save.getPhone());
        AskMoneyInfoModel askMoneyInfoModel = EntityToModel.coverAskMoneyInfo(save, oneByPhone);
        return askMoneyInfoModel;
    }

    @Override
    public List<ContactInfoModel> contactRecode(String goodsType) {
        if (StringUtils.isEmpty(goodsType))
        {
           return EntityToModel.coverContactInfo(contactInfoRepository.findAll());
        }
        List<contactInfo> all = contactInfoRepository.findAllByGoodsType(goodsType);
        return EntityToModel.coverContactInfo(all);
    }

//    private List<AdminClientInfModel> convertQuestionList(List<AdminClientInfo> userInfos){
//        List<AdminClientInfModel> models = new ArrayList<>();
//        List<UserClientInfo> clientInfos=null;
//        userInfos.forEach(userInfo -> {
//            //根据手机号查询UserClientInfo
//            UserClientInfo oneByUuid = userClientInfoRepository.findOneByUserId(userInfo.getUserId());
//            clientInfos.add(oneByUuid);
//            EntityToModel.coverUserClientInfo(oneByUuid);
//        });
////        models.add(EntityToModel.coverUserInfo(userInfos,oneByUuid));
//        return models;
//    }

    private AdminClientInfModel convertQuestionList(AdminClientInfo userInfo){
        AdminClientInfModel model =new AdminClientInfModel();
            //根据手机号查询UserClientInfo
        UserClientInfo oneByPhone = userClientInfoRepository.findOneByUserId(userInfo.getUuid());
         return EntityToModel.coverAdminClientInfo(userInfo,oneByPhone);

    }
    private AdminClientInfModel convertentity(AdminClientInfModel userInfo){
        AdminClientInfModel model =new AdminClientInfModel();
            //根据手机号查询UserClientInfo
        UserClientInfo oneByPhone = userClientInfoRepository.findOneByUserId(userInfo.getPhone());
        AdminClientInfo adminClientInfo = ModelToEntity.cover2entity(userInfo);
        AdminClientInfo save = adminClientInfoRepository.save(adminClientInfo);
       return EntityToModel.coverAdminClientInfo(save,oneByPhone);

    }
}